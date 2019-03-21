package com.ogmallpad.presenter;

import android.content.Context;

import com.ogmallpad.contract.CollectContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 收藏
 * Created by chenjunshan on 2018-09-27.
 */

public class CollectPresenter extends HttpPresenter implements CollectContract.Presenter {
    private Context mContext;
    private CollectContract.View mView;

    public CollectPresenter(Context mContext, CollectContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}