package com.ogmamllpadnew.widget;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;

import com.junshan.pub.utils.PermissionUtis;
import com.junshan.pub.utils.PhotoUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.databinding.DgPhotoSelectLayoutBinding;


/**
 * 选择照片弹出框
 * Created by chenjunshan on 17-6-7.
 */

public class PhotoSelectDialog {
    private static DgPhotoSelectLayoutBinding selectLayoutBinding;
    private static CommonDialog dialog;

    /**
     * 打开图片选择器
     *
     * @param fragment
     */
    public static void open(final Fragment fragment) {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (PermissionUtis.checkPermissions(fragment.getActivity(), permissions)) {
            fragment.requestPermissions(permissions, PermissionUtis.REQUESTCODE);
        } else {
            dialog = new CommonDialog.Builder(fragment.getActivity())
                    .setWidth(1f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setGravity(Gravity.BOTTOM)
                    .setResId(R.layout.dg_photo_select_layout)
                    .build();
            selectLayoutBinding = (DgPhotoSelectLayoutBinding) dialog.getBinding();
            selectLayoutBinding.tvCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoUtils.toCamera(fragment);
                    dissmiss();
                }
            });
            selectLayoutBinding.tvHhotoALbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoUtils.toHhotoAlbum(fragment);
                    dissmiss();
                }
            });
            PhotoUtils.imageUri = null;//每次打开弹框都要设置为空，防止错误删除照片
            dialog.show();
        }
    }

    /**
     * 打开图片选择器
     *
     * @param activity
     */
    public static void open(final Activity activity) {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (PermissionUtis.checkPermissions(activity, permissions)) {
            ActivityCompat.requestPermissions(activity, permissions, PermissionUtis.REQUESTCODE);
        } else {
            dialog = new CommonDialog.Builder(activity)
                    .setWidth(1f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setGravity(Gravity.BOTTOM)
                    .setResId(R.layout.dg_photo_select_layout)
                    .build();
            selectLayoutBinding = (DgPhotoSelectLayoutBinding) dialog.getBinding();
            selectLayoutBinding.tvCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoUtils.toCamera(activity);
                    dissmiss();
                }
            });
            selectLayoutBinding.tvHhotoALbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhotoUtils.toHhotoAlbum(activity);
                    dissmiss();
                }
            });
            PhotoUtils.imageUri = null;//每次打开弹框都要设置为空，防止错误删除照片
            dialog.show();
        }
    }

    public static void dissmiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
