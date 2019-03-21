package com.ogmallpad.presenter;

import android.content.Context;

import com.ogmallpad.contract.LiveContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 个性生活
 * Created by chenjunshan on 2018-07-03.
 */

public class LivePresenter extends HttpPresenter implements LiveContract.Presenter {
    private Context mContext;
    private LiveContract.View mView;

    public LivePresenter(Context mContext, LiveContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}