package com.ogmallpad.presenter;

import android.content.Context;

import com.ogmallpad.contract.ProductlookContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 商品展示
 * Created by chenjunshan on 2018-07-03.
 */

public class ProductlookPresenter extends HttpPresenter implements ProductlookContract.Presenter {
    private Context mContext;
    private ProductlookContract.View mView;

    public ProductlookPresenter(Context mContext, ProductlookContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}