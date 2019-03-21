package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.PlandetailsContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 方案详情
 * Created by 陈俊山 on 2018-11-09.
 */

public class PlandetailsPresenter extends HttpPresenter implements PlandetailsContract.Presenter {
    private Context mContext;
    private PlandetailsContract.View mView;

    public PlandetailsPresenter(Context mContext, PlandetailsContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}