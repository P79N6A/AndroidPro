package com.ogmallpad.presenter;

import android.content.Context;

import com.ogmallpad.contract.VoicefileContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 录音文件
 * Created by chenjunshan on 2018-07-04.
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