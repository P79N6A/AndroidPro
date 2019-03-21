package com.ogmallpad.presenter;

import android.content.Context;

import com.ogmallpad.contract.LoginContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 登录
 * Created by chenjunshan on 2018-07-03.
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