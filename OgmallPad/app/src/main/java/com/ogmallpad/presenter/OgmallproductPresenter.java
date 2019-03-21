package com.ogmallpad.presenter;

import android.content.Context;

import com.ogmallpad.contract.OgmallproductContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 软装家具
 * Created by chenjunshan on 2018-07-03.
 */

public class OgmallproductPresenter extends HttpPresenter implements OgmallproductContract.Presenter {
    private Context mContext;
    private OgmallproductContract.View mView;

    public OgmallproductPresenter(Context mContext, OgmallproductContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}