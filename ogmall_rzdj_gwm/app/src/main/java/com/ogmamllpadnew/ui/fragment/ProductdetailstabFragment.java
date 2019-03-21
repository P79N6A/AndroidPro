package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ogmamllpadnew.R;
import com.ogmamllpadnew.contract.ProductdetailstabContract;
import com.ogmamllpadnew.databinding.FgProductdetailstabLayoutBinding;
import com.ogmamllpadnew.presenter.ProductdetailstabPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.net.BaseBean;

/**
 * 商品详情
 * Created by 陈俊山 on 2018-11-08.
 */

public class ProductdetailstabFragment extends BaseFragment<FgProductdetailstabLayoutBinding, Object> implements ProductdetailstabContract.View {
    private ProductdetailstabPresenter presenter;

    @Override
    public int bindLayout() {
        return R.layout.fg_productdetailstab_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ProductdetailstabPresenter(getActivity(), this);

    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {

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

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }
}
