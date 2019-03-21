package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.BaginleftContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 拎包入住左边
 * Created by 陈俊山 on 2018-11-10.
 */

public class BaginleftPresenter extends HttpPresenter implements BaginleftContract.Presenter {
    private Context mContext;
    private BaginleftContract.View mView;

    public BaginleftPresenter(Context mContext, BaginleftContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}