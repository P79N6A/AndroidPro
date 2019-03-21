package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.junshan.pub.adapter.ViewPagerAdapter;
import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmallpad.R;
import com.ogmallpad.adapter.PubViewPagerAdapter;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.contract.PlanContract;
import com.ogmallpad.databinding.MyPlanTabLayoutBinding;
import com.ogmallpad.presenter.PlanPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.DesignerofUserIdBean;
import com.shan.netlibrary.bean.DesignstylesOfdesignerBean;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.net.AppParams;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的方案
 * Created by chenjunshan on 18-7-5.
 */

public class MyPlanTabFragment extends BaseFragment<MyPlanTabLayoutBinding, Object> implements PlanContract.View {
    private PlanPresenter presenter;

    @Override
    public int bindLayout() {
        return R.layout.my_plan_tab_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new PlanPresenter(getActivity(), this);
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(loginBean.getData().getUserId()));
        presenter.designerofUserId(AppParams.getParams(map));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ofdesignerBean == null) {
            Map<String, String> map = new HashMap<>();
            map.put("userId", String.valueOf(loginBean.getData().getUserId()));
            presenter.designstylesOfdesigner(map);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    private StylesBean bean;
    private DesignstylesOfdesignerBean ofdesignerBean;
    private List<Fragment> list;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.STYLES) {
            bean = (StylesBean) baseBean;
            if (bean.getData().size() > 0) {
                list = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                for (int i = 0; i < bean.getData().size(); i++) {
                    PlanFragment planFragment = new PlanFragment();
                    planFragment.setId(String.valueOf(bean.getData().get(i).getId()));
                    list.add(planFragment);
                    titles.add(bean.getData().get(i).getName());
                }
                mBinding.planViewPager.setAdapter(new PubViewPagerAdapter<>(getActivity().getSupportFragmentManager(), list, titles));
                mBinding.planTabs.setViewPager(mBinding.planViewPager);
                mBinding.planTabs.setTextSize(13);
            }
        } else if (mTag == HttpPresenter.DESIGNSTYLESOFDESIGNER) {
            //获取设计师方案风格和方案列表
            ofdesignerBean = (DesignstylesOfdesignerBean) baseBean;
            //增加全部
            DesignstylesOfdesignerBean.DataBean allBean = new DesignstylesOfdesignerBean.DataBean();
            allBean.setStyleName("全部");
            allBean.setStyleId(SpConstant.STYLEID);
            ofdesignerBean.getData().add(0, allBean);

            if (ofdesignerBean.getData().size() > 0) {
                list = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                for (int i = 0; i < ofdesignerBean.getData().size(); i++) {
                    PlanFragment planFragment = new PlanFragment();
                    planFragment.setId(String.valueOf(ofdesignerBean.getData().get(i).getStyleId()));
                    list.add(planFragment);
                    titles.add(ofdesignerBean.getData().get(i).getStyleName());
                }
                mBinding.planViewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(), list, titles));
                mBinding.planTabs.setViewPager(mBinding.planViewPager);
                mBinding.planTabs.setTextSize(13);
            }
        } else if (mTag == HttpPresenter.DESIGNEROFUSERID) {
            //根据用户id 获取获取设计师用户数据！
            DesignerofUserIdBean bean = (DesignerofUserIdBean) baseBean;
            //绑定用户信息
            ImageCacheUtils.loadImage(getActivity(), bean.getData().getHeadImage(), mBinding.ivHead);
            mBinding.tvDesigner.setText(getString(R.string.str_sjs) + bean.getData().getUserName());
            mBinding.tvStyle.setText(getString(R.string.str_scfg) + bean.getData().getGoodAtStyle());
            mBinding.tvLocation.setText(getString(R.string.str_wz) + bean.getData().getProvinceName() + bean.getData().getCityName());
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.ivToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list != null && list.size() > 0) {
                    mBinding.planViewPager.setCurrentItem(0);
                }
            }
        });
    }
}
