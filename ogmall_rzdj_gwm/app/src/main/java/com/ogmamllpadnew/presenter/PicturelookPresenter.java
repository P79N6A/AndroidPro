package com.ogmamllpadnew.presenter;

import android.content.Context;

import com.ogmamllpadnew.contract.PicturelookContract;
import com.shan.netlibrary.net.HttpPresenter;

/**
 * 图片预览
 * Created by 陈俊山 on 2018-11-09.
 */

public class PicturelookPresenter extends HttpPresenter implements PicturelookContract.Presenter {
    private Context mContext;
    private PicturelookContract.View mView;

    public PicturelookPresenter(Context mContext, PicturelookContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }
}