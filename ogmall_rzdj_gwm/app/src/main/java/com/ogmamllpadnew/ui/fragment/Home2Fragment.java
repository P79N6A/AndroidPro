package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.PublicUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.databinding.HbdpLayoutBinding;
import com.ogmamllpadnew.databinding.Home2LayoutBinding;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.AppselectHomeDataBean;

import java.util.List;

/**
 * Created by root on 18-11-6.
 */

public class Home2Fragment extends BaseFragment<Home2LayoutBinding, Object> {
    @Override
    public int bindLayout() {
        return R.layout.home2_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
    }

    /**
     * 初始化火爆单品数据
     */
    private void initHbdpData() {
        try{
            final List<AppselectHomeDataBean.DataBean.HotProductBean> hotProduct = homeDataBean.getData().getHotProduct();
            mBinding.llHbdp.removeAllViews();
            for (int i = 0; i < hotProduct.size(); i++) {
                final int j = i;
                HbdpLayoutBinding hbdpLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.hbdp_layout, mBinding.llHbdp, false);
                mBinding.llHbdp.addView(hbdpLayoutBinding.getRoot());
                ImageCacheUtils.loadImage(getActivity(), hotProduct.get(i).getHeadImage(), hbdpLayoutBinding.imageview);
                hbdpLayoutBinding.tvName.setText(hotProduct.get(i).getName());
                hbdpLayoutBinding.tvSubName.setText(hotProduct.get(i).getSubTitle());
                hbdpLayoutBinding.tvPrice.setText(PublicUtils.getRenminbi() + hotProduct.get(i).getPrice());
                hbdpLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.ID, hotProduct.get(j).getId());
                        startFragment(ProductdetailsFragment.class, bundle);
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private AppselectHomeDataBean homeDataBean;

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.HOMEDATA) {
            homeDataBean = (AppselectHomeDataBean) msgEvent.getBean();
            if (homeDataBean != null) {
                initHbdpData();
            }
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }
}
