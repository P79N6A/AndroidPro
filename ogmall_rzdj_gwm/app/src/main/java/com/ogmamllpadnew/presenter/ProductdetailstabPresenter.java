package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.ProductdetailstabContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 商品详情
 * Created by 陈俊山 on 2018-11-08.
 */

public class ProductdetailstabPresenter extends HttpPresenter implements ProductdetailstabContract.Presenter {
    private Context mContext;
    private ProductdetailstabContract.View mView;

    public ProductdetailstabPresenter(Context mContext, ProductdetailstabContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}