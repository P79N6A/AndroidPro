package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.ProductContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 商品
 * Created by 陈俊山 on 2018-11-02.
 */

public class ProductPresenter extends HttpPresenter implements ProductContract.Presenter {
    private Context mContext;
    private ProductContract.View mView;

    public ProductPresenter(Context mContext, ProductContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}