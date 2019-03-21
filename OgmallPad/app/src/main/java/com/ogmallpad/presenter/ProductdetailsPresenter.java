package com.ogmallpad.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.R;
import com.ogmallpad.contract.ProductdetailsContract;
import com.ogmallpad.databinding.DialogImageLayoutBinding;
import com.ogmallpad.databinding.RqCodeLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 商品详情
 * Created by chenjunshan on 2018-10-10.
 */

public class ProductdetailsPresenter extends HttpPresenter implements ProductdetailsContract.Presenter {
    private Context mContext;
    private ProductdetailsContract.View mView;

    public ProductdetailsPresenter(Context mContext, ProductdetailsContract.View mView) {
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

    private CommonDialog dialog;
    private DialogImageLayoutBinding imageBinding;

    /**
     * 查看图片
     *
     * @param img
     */
    public void lookImage(String img) {
        dialog = new CommonDialog.Builder(mContext)
                .setWidth(1f)
                .setHeight(1f)
                .setResId(R.layout.dialog_image_layout)
                .setShape(R.drawable.dialog_tra_shape)
                .setAnimResId(R.style.dialog_scale)
                .build();
        imageBinding = (DialogImageLayoutBinding) dialog.getBinding();
        imageBinding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        imageBinding.imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                dialog.dismiss();
            }
        });
        ImageCacheUtils.loadImage(mContext, img, imageBinding.imageView);
        dialog.show();
    }
}