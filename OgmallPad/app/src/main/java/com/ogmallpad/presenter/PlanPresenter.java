package com.ogmallpad.presenter;

import android.content.Context;

import com.ogmallpad.contract.PlanContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 方案
 * Created by chenjunshan on 2018-07-02.
 */

public class PlanPresenter extends HttpPresenter implements PlanContract.Presenter {
    private Context mContext;
    private PlanContract.View mView;

    public PlanPresenter(Context mContext, PlanContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}