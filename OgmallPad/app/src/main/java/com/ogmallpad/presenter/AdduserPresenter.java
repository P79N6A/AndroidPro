package com.ogmallpad.presenter;

import android.content.Context;

import com.ogmallpad.contract.AdduserContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 添加客户
 * Created by chenjunshan on 2018-07-03.
 */

public class AdduserPresenter extends HttpPresenter implements AdduserContract.Presenter {
    private Context mContext;
    private AdduserContract.View mView;

    public AdduserPresenter(Context mContext, AdduserContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}