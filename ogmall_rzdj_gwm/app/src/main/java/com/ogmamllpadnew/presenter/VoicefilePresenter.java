package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.VoicefileContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 录音文件
 * Created by 陈俊山 on 2018-11-12.
 */

public class VoicefilePresenter extends HttpPresenter implements VoicefileContract.Presenter {
    private Context mContext;
    private VoicefileContract.View mView;

    public VoicefilePresenter(Context mContext, VoicefileContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}