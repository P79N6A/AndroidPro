package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.adapter.PubViewPagerAdapter;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.contract.BaginlefttabContract;
import com.ogmamllpadnew.databinding.FgBaginlefttabLayoutBinding;
import com.ogmamllpadnew.presenter.BaginlefttabPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.HandbagselectAllHouseTypeBean;
import com.shan.netlibrary.bean.HandbagselectLayoutBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 拎包入住左边
 * Created by 陈俊山 on 2018-11-10.
 */

public class BaginlefttabFragment extends BaseFragment<FgBaginlefttabLayoutBinding, Object> implements BaginlefttabContract.View {
    private BaginlefttabPresenter presenter;
    public static String houseTypeId;//户型id

    /**
     * 设置房间名称
     * @param name
     */
    @Override
    public void setHouseName(String name){
        mBinding.tvHouseName.setText(name);
    }

    @Override
    public int bindLayout() {
        return R.layout.fg_baginlefttab_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BaginlefttabPresenter(getActivity(), this);
        presenter.handbagselectAllHouseType(null);
    }

    //户型数据
    private HandbagselectAllHouseTypeBean houseTypeBean;
    //布局数据
    private HandbagselectLayoutBean layoutBean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.HANDBAGSELECTALLHOUSETYPE) {
            //获取户型
            houseTypeBean = (HandbagselectAllHouseTypeBean) baseBean;
            presenter.showHxDialog(houseTypeBean);
        } else if (mTag == HttpPresenter.HANDBAGSELECTLAYOUT) {
            presenter.dismissDialog();
            //查询布局
            layoutBean = (HandbagselectLayoutBean) baseBean;

            for (int i = 0; i < layoutBean.getData().size(); i++) {
                layoutBean.getData().get(i).setCheck(true);
                layoutBean.getData().get(i).setDefult(true);
            }

            //保存拎包入住数据
            String data = new Gson().toJson(layoutBean);
            SPUtils.put(SpConstant.BAGIN_LAYOUT, data);

            MyApp.getInstance().setLayoutBean(layoutBean);
            initTitleData();

            EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_RIGHT_EMPTY));
            EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_BAGIN_PRICE));
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.llHx.setOnClickListener(this);
        mBinding.tvEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll_hx:
                if (houseTypeBean == null) {
                    presenter.handbagselectAllHouseType(null);
                } else {
                    presenter.showHxDialog(houseTypeBean);
                }
                break;
            case R.id.tv_edit:
                if (layoutBean == null) {
                    ToastUtils.toast(getString(R.string.str_qxzhx));
                } else {
                    presenter.showEditDialog();
                }
                break;
        }
    }

    /**
     * 初始化title数据
     */
    @Override
    public void initTitleData() {
        layoutBean = MyApp.getInstance().getLayoutBean();
        if (layoutBean == null)
            return;
        //滑动tab
        List<BaginleftFragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < layoutBean.getData().size(); i++) {
            if (layoutBean.getData().get(i).isCheck()) {
                BaginleftFragment productlistFragment = new BaginleftFragment();
                productlistFragment.setTabPos(i);
                fragments.add(productlistFragment);
                titles.add(layoutBean.getData().get(i).getName());
            }
        }
        mBinding.vpBaginLeft.setAdapter(new PubViewPagerAdapter<>(getActivity().getSupportFragmentManager(), fragments, titles));
        mBinding.tabsBaginLeft.setViewPager(mBinding.vpBaginLeft);
        mBinding.tabsBaginLeft.setTextSize(11);
        mBinding.tabsBaginLeft.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MyApp.getInstance().setTabPos(position);
                EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_RIGHT_EMPTY));
                EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_BAGIN_PRICE));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.REFRESH_REFRESH_LAYOUT_TITLE) {
            initTitleData();
        }
    }
}
