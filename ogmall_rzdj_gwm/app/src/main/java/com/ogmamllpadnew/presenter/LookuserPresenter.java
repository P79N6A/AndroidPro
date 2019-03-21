package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.LookuserContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 查看会员
 * Created by 陈俊山 on 2018-11-21.
 */

public class LookuserPresenter extends HttpPresenter implements LookuserContract.Presenter {
    private Context mContext;
    private LookuserContract.View mView;

    public LookuserPresenter(Context mContext, LookuserContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}