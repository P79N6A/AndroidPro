package com.ogmallpad.presenter;

import android.content.Context;

import com.ogmallpad.contract.BrandlookContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 品牌展示
 * Created by chenjunshan on 2018-07-03.
 */

public class BrandlookPresenter extends HttpPresenter implements BrandlookContract.Presenter {
    private Context mContext;
    private BrandlookContract.View mView;

    public BrandlookPresenter(Context mContext, BrandlookContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}