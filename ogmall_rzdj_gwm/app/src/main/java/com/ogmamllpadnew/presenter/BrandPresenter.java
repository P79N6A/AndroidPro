package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.BrandContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 品牌
 * Created by 陈俊山 on 2018-11-02.
 */

public class BrandPresenter extends HttpPresenter implements BrandContract.Presenter {
    private Context mContext;
    private BrandContract.View mView;

    public BrandPresenter(Context mContext, BrandContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}