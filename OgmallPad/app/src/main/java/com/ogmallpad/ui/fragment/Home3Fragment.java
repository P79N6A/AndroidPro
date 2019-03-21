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
import com.ogmallpad.databinding.ItemBrandBinding;
import com.ogmallpad.presenter.HomePresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.ui.activity.HomeActivity;
import com.shan.netlibrary.bean.BranddetailsBean;
import com.shan.netlibrary.bean.BrandlistBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/14.
 */

public class Home3Fragment extends BaseFragment<Home3LayoutBinding,Object> implements HomeContract.View {
    private HomePresenter presenter;
    @Override
    public int bindLayout() {
        return R.layout.home3_layout;
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new HomePresenter(getContext(),this);
        loadBrandList();
        mBinding.tvPtsps.setText("品牌展示");
        mBinding.tvMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (getActivity() instanceof HomeActivity){
            ((HomeActivity) getActivity()).jumpFragment(1);
        }
    }

    private void loadBrandList() {
        Map<String,String> params = new HashMap<>();
        params.put("pageSize","4");
        params.put("pageNo","1");
        presenter.brandlist(params,false);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.BRANDLIST){
            BrandlistBean bean = (BrandlistBean) baseBean;
            initBandList(bean.getData().getList());
        }else if (mTag == HttpPresenter.BRANDDETAILS){
            BranddetailsBean bean = (BranddetailsBean) baseBean;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BEAN, bean);
            startFragment(BranddetailsFragment.class, bundle);
        }
    }

    private void initBandList(final List<BrandlistBean.DataBean.ListBean> bean) {
        try{
            mBinding.llHbdp.removeAllViews();
            for (int i = 0; i < bean.size(); i++) {
                final int j = i;
                ItemBrandBinding hbdpLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.item_brand, mBinding.llHbdp, false);
                mBinding.llHbdp.addView(hbdpLayoutBinding.getRoot());
                ImageCacheUtils.loadImage(getActivity(), bean.get(i).getHeadImage(), hbdpLayoutBinding.iv);
                ImageCacheUtils.loadImage(getActivity(), bean.get(i).getImage(), hbdpLayoutBinding.iv2);

                final int finalI = i;
                hbdpLayoutBinding.llPrent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        branddetails(bean.get(finalI).getId());
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取品牌的详细信息
     *
     * @param bean
     */
    private void branddetails(int id) {
        Map<String, String> map = new HashMap<>();
        map.put("brandId", String.valueOf(id));
        presenter.branddetails(map);
    }


    @Override
    public void onFailure(Throwable e, long mTag) {

    }
}
