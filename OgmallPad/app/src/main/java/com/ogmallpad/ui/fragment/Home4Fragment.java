package com.ogmallpad.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.contract.HomeContract;
import com.ogmallpad.databinding.Home3LayoutBinding;
import com.ogmallpad.databinding.PlanLayoutBinding;
import com.ogmallpad.presenter.HomePresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.ui.activity.HomeActivity;
import com.shan.netlibrary.bean.DesigndetailsBean;
import com.shan.netlibrary.bean.DesignlistBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/15.
 */

public class Home4Fragment extends BaseFragment<Home3LayoutBinding,Object>  implements HomeContract.View{
    private HomePresenter presenter;
    @Override
    public int bindLayout() {
        return R.layout.home3_layout;
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new HomePresenter(getContext(),this);
        mBinding.tvPtsps.setText("方案展示");
        designlist(false);

        mBinding.tvMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).jumpFragment(3);
        }
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.DESIGNLIST){
            DesignlistBean bean = (DesignlistBean) baseBean;
            initData(bean.getData().getList());
        }else if (mTag == HttpPresenter.DESIGNDETAILS){
            DesigndetailsBean bean = (DesigndetailsBean) baseBean;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BEAN, bean);
            startFragment(PlandetailsFragment.class, bundle);
        }
    }

    private void initData(final List<DesignlistBean.DataBean.ListBean> list) {
        try{
            mBinding.llHbdp.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                final int j = i;
                PlanLayoutBinding hbdpLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.plan_layout, mBinding.llHbdp, false);
                mBinding.llHbdp.addView(hbdpLayoutBinding.getRoot());
                ImageCacheUtils.loadImage(getActivity(), list.get(i).getHeadImage(), hbdpLayoutBinding.imageview);
                hbdpLayoutBinding.tvName.setText(list.get(i).getTitle());
                hbdpLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        designDetails(list.get(j).getId());
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    private void designlist(boolean isShow) {
        //根据风格Id所有审核通过的方案
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
//        if (loginBean.getData().getUserType().equals("Designer")) {
//            map.put("userId", String.valueOf(loginBean.getData().getUserId()));
//        }
        presenter.designlist(map, isShow);
    }

    private void designDetails(int id) {
        Map<String, String> map = new HashMap<>();
        map.put("designId", String.valueOf(id));
        presenter.designdetails(map);
    }
}
