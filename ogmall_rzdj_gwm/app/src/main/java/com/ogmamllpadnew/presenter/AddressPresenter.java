package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.AddressContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 地址列表
 * Created by 陈俊山 on 2018-11-16.
 */

public class AddressPresenter extends HttpPresenter implements AddressContract.Presenter {
    private Context mContext;
    private AddressContract.View mView;

    public AddressPresenter(Context mContext, AddressContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}