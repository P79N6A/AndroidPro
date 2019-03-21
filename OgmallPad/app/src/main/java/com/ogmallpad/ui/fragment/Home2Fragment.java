package com.ogmallpad.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.PublicUtils;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.contract.HomeContract;
import com.ogmallpad.databinding.HbdpLayoutBinding;
import com.ogmallpad.databinding.Home2LayoutBinding;
import com.ogmallpad.presenter.HomePresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.ProductofCategotyBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/14.
 */

public class Home2Fragment extends BaseFragment<Home2LayoutBinding,Object> implements HomeContract.View{

    private HomePresenter presenter;
    @Override
    public int bindLayout() {
        return R.layout.home2_layout;
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new HomePresenter(getActivity(),this);
        loadHbdpData();

        mBinding.tvMore.setVisibility(View.VISIBLE);
        mBinding.tvMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_more){
            startFragment(BktjtabFragment.class, null);
        }

    }

    private void loadHbdpData() {
        Map<String,String> params = new HashMap<>();
        params.put("pageSize","10");
        params.put("categotyId","488");
        params.put("pageNo","1");
        presenter.productofCategoty(params,false);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTOFCATEGOTY){
            ProductofCategotyBean bean = (ProductofCategotyBean) baseBean;
            initHbdpData(bean.getData().getList());
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    private void initHbdpData(final List<ProductofCategotyBean.DataBean.ListBean> data) {
        try{
            mBinding.llHbdp.removeAllViews();
            for (int i = 0; i < data.size(); i++) {
                final int j = i;
                HbdpLayoutBinding hbdpLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.hbdp_layout, mBinding.llHbdp, false);
                mBinding.llHbdp.addView(hbdpLayoutBinding.getRoot());
                ImageCacheUtils.loadImage(getActivity(), data.get(i).getImage(), hbdpLayoutBinding.imageview);
                hbdpLayoutBinding.tvName.setText(data.get(i).getName());
                hbdpLayoutBinding.tvSubName.setText(data.get(i).getName());
                hbdpLayoutBinding.tvPrice.setText(PublicUtils.getRenminbi() + data.get(i).getPrice());
                hbdpLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.ID, String.valueOf(data.get(j).getId()));
                        startFragment(ProductdetailsFragment.class, bundle);
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
