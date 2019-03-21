package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.AddplanContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 添加方案
 * Created by 陈俊山 on 2018-11-12.
 */

public class AddplanPresenter extends HttpPresenter implements AddplanContract.Presenter {
    private Context mContext;
    private AddplanContract.View mView;

    public AddplanPresenter(Context mContext, AddplanContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}