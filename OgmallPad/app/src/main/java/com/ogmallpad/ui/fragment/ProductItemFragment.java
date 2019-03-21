package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmallpad.R;
import com.ogmallpad.bean.ProductsBean;
import com.ogmallpad.databinding.FgProductItemLayoutBinding;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.CentersavecollectsBean;
import com.shan.netlibrary.net.AppParams;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 18-9-25.
 */

public class ProductItemFragment extends BaseFragment<FgProductItemLayoutBinding, Object> {
    private ProductsBean.DataBean dataBean;

    @Override
    public int bindLayout() {
        return R.layout.fg_product_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    public static ProductItemFragment newInstance(ProductsBean.DataBean dataBean) {
        ProductItemFragment fragment = new ProductItemFragment();
        fragment.dataBean = dataBean;
        return fragment;
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        if (dataBean == null)
            return;
        ImageCacheUtils.loadImage(getActivity(), dataBean.getImage(), mBinding.iv);
        mBinding.tvTitle.setText(dataBean.getTitle());
        mBinding.tvSize.setText(getString(R.string.str_gg) + dataBean.getSpecification());
        mBinding.tvPrice.setText("¥" + dataBean.getPrice());
        mBinding.tvCollect.setChecked(dataBean.isCollect());
        mBinding.tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(dataBean.getId()));
                map.put("isCollect", String.valueOf(!dataBean.isCollect()));
                centersavecollects(map);
            }
        });
    }

    /**
     * 批量收藏
     */
    public void centersavecollects(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<CentersavecollectsBean>(getActivity(), this, false) {
            @Override
            protected void onSuccess(CentersavecollectsBean baseBean) {
                //收藏成功
                dataBean.setCollect(!dataBean.isCollect());
                mBinding.tvCollect.setChecked(dataBean.isCollect());
            }

            @Override
            protected void onFailure(Throwable e) {
            }
        };
        HttpBuilder.getInstance().execute(HttpBuilder.httpService.centersavecollects(AppParams.getParams(map)), callback);
    }
}
