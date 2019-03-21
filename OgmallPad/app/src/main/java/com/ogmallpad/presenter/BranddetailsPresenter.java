package com.ogmallpad.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.R;
import com.ogmallpad.contract.BranddetailsContract;
import com.ogmallpad.databinding.DialogImageLayoutBinding;
import com.ogmallpad.databinding.RqCodeLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 品牌详情
 * Created by chenjunshan on 2018-07-03.
 */

public class BranddetailsPresenter extends HttpPresenter implements BranddetailsContract.Presenter {
    private Context mContext;
    private BranddetailsContract.View mView;

    public BranddetailsPresenter(Context mContext, BranddetailsContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    private CommonDialog imageDialog;
    private DialogImageLayoutBinding imageBinding;

    /**
     * 查看图片
     *
     * @param img
     */
    public void lookImage(String img) {
        imageDialog = new CommonDialog.Builder(mContext)
                .setWidth(1f)
                .setHeight(1f)
                .setResId(R.layout.dialog_image_layout)
                .setShape(R.drawable.dialog_tra_shape)
                .setAnimResId(R.style.dialog_scale)
                .build();
        imageBinding = (DialogImageLayoutBinding) imageDialog.getBinding();
        imageBinding.imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                dialog.dismiss();
            }
        });
        ImageCacheUtils.loadImage(mContext, img, imageBinding.imageView);
        imageDialog.show();
    }

    /**
     * *********************************************************************************************
     * 分享成功弹窗
     */
    private CommonDialog dialog;
    private RqCodeLayoutBinding rqCodeBinding;

    @Override
    public void showRqDialog(String image) {
        if (dialog == null) {
            dialog = new CommonDialog.Builder(mContext)
                    .setWidth(0.3f)
                    .setHeight(0f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setResId(R.layout.rq_code_layout)
                    .setGravity(Gravity.CENTER)
                    .build();
            rqCodeBinding = (RqCodeLayoutBinding) dialog.getBinding();
            rqCodeBinding.ll1.setVisibility(View.VISIBLE);
            rqCodeBinding.ll2.setVisibility(View.GONE);
        }
        Bitmap mBitmap = CodeUtils.createImage(image, 400, 400, null);
        rqCodeBinding.image.setImageBitmap(mBitmap);
        dialog.show();
    }
}