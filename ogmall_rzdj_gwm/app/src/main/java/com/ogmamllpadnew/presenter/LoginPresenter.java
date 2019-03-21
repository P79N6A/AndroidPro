package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.LoginContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 登录
 * Created by 陈俊山 on 2018-11-13.
 */

public class LoginPresenter extends HttpPresenter implements LoginContract.Presenter {
    private Context mContext;
    private LoginContract.View mView;

    public LoginPresenter(Context mContext, LoginContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}