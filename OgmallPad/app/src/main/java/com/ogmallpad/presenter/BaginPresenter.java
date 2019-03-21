package com.ogmallpad.presenter;

import android.content.Context;

import com.ogmallpad.contract.BaginContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 拎包入住
 * Created by chenjunshan on 2018-08-21.
 */

public class BaginPresenter extends HttpPresenter implements BaginContract.Presenter {
    private Context mContext;
    private BaginContract.View mView;

    public BaginPresenter(Context mContext, BaginContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}