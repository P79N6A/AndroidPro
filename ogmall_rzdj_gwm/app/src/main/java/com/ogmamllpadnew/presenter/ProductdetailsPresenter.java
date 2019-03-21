package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.ProductdetailsContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 商品详情
 * Created by 陈俊山 on 2018-11-08.
 */

public class ProductdetailsPresenter extends HttpPresenter implements ProductdetailsContract.Presenter {
    private Context mContext;
    private ProductdetailsContract.View mView;

    public ProductdetailsPresenter(Context mContext, ProductdetailsContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}