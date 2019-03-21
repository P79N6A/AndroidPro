package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.MycollectContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 我的收藏
 * Created by 陈俊山 on 2018-11-12.
 */

public class MycollectPresenter extends HttpPresenter implements MycollectContract.Presenter {
    private Context mContext;
    private MycollectContract.View mView;

    public MycollectPresenter(Context mContext, MycollectContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}