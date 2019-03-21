package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ogmallpad.R;
import com.ogmallpad.adapter.ProductAdapter;
import com.ogmallpad.bean.ProductsBean;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.databinding.FgProductLayoutBinding;
import com.ogmallpad.ui.BaseFragment;


/**
 * 商品展示
 */
public class ProductsFragment extends BaseFragment<FgProductLayoutBinding, Object> {

    @Override
    public int bindLayout() {
        return R.layout.fg_product_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    private ProductAdapter productAdapter;

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        ProductsBean productsBean = (ProductsBean) getActivity().getIntent().getSerializableExtra(Constants.BEAN);
        int position = getActivity().getIntent().getIntExtra(Constants.POSITION, 0);
        if (productsBean != null && productsBean.getData().size() > 0) {
            if (productAdapter == null) {
                productAdapter = new ProductAdapter(getActivity().getSupportFragmentManager(), productsBean.getData());
                mBinding.pagerProduct.setAdapter(productAdapter);
                mBinding.indicatorProduct.setViewPager(mBinding.pagerProduct);
            }
            mBinding.pagerProduct.setCurrentItem(position);
        }
        mBinding.llClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }
}
