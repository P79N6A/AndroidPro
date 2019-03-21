package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;

import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.contract.BranddetailsContract;
import com.ogmamllpadnew.databinding.RqCodeLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 品牌详情
 * Created by 陈俊山 on 2018-11-07.
 */

public class BranddetailsPresenter extends HttpPresenter implements BranddetailsContract.Presenter {
    private Context mContext;
    private BranddetailsContract.View mView;

    public BranddetailsPresenter(Context mContext, BranddetailsContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    /**
     * *********************************************************************************************
     * 分享成功弹窗
     */
    private CommonDialog dialogShare;
    private RqCodeLayoutBinding rqCodeBinding;

    @Override
    public void showRqDialog(String image) {
        if (dialogShare == null) {
            dialogShare = new CommonDialog.Builder(mContext)
                    .setWidth(0.3f)
                    .setHeight(0f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setResId(R.layout.rq_code_layout)
                    .setGravity(Gravity.CENTER)
                    .build();
            rqCodeBinding = (RqCodeLayoutBinding) dialogShare.getBinding();
            rqCodeBinding.ll1.setVisibility(View.VISIBLE);
            rqCodeBinding.ll2.setVisibility(View.GONE);
        }
        Bitmap mBitmap = CodeUtils.createImage(image, 400, 400, null);
        rqCodeBinding.image.setImageBitmap(mBitmap);
        dialogShare.show();
    }
}