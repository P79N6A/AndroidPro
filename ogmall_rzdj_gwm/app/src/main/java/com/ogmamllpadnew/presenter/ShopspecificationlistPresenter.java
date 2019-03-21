package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.contract.ShopspecificationlistContract;
import com.ogmamllpadnew.databinding.DialogAddSizeLayoutBinding;
import com.shan.netlibrary.bean.GoodsselectGoodsSpecificationBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 店铺商-规格列表
 * Created by 陈俊山 on 2018-12-11.
 */

public class ShopspecificationlistPresenter extends HttpPresenter implements ShopspecificationlistContract.Presenter {
    private Context mContext;
    private ShopspecificationlistContract.View mView;
    private boolean isEdit = false;

    public ShopspecificationlistPresenter(Context mContext, ShopspecificationlistContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    //新增规格--------------------------------
    private CommonDialog dialogAddSize;
    private DialogAddSizeLayoutBinding addSizeLayoutBinding;

    public CommonDialog getDialogAddSize() {
        return dialogAddSize;
    }

    @Override
    public void showAddSize(final int goodsId, final boolean isEdit, final GoodsselectGoodsSpecificationBean.DataBean item) {
        dialogAddSize = new CommonDialog.Builder(mContext)
                .setWidth(0.65f)
                .setHeight(0.7f)
                .setResId(R.layout.dialog_add_size_layout)
                .setAnimResId(0)
                .build();
        addSizeLayoutBinding = (DialogAddSizeLayoutBinding) dialogAddSize.getBinding();
        addSizeLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddSize.dismiss();
            }
        });
        addSizeLayoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddSize.dismiss();
            }
        });
        addSizeLayoutBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGoodsSpecification(goodsId, isEdit, item);
            }
        });
        addSizeLayoutBinding.ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.uploadPictures();
            }
        });
        if (isEdit && item != null) {
            addSizeLayoutBinding.etName.setText(item.getName());
            addSizeLayoutBinding.etColor.setText(item.getColor());
            addSizeLayoutBinding.etOldPrice.setText(item.getOriginalPrice());
            addSizeLayoutBinding.etSize.setText(item.getSpec());
            addSizeLayoutBinding.etPrice.setText(item.getPrice());
            ImageCacheUtils.loadImage(mContext, item.getImagesList().size() > 0 ? item.getImagesList().get(0) : "", addSizeLayoutBinding.ivUpload);
        } else {
            addSizeLayoutBinding.etName.setText("");
            addSizeLayoutBinding.etColor.setText("");
            addSizeLayoutBinding.etOldPrice.setText("");
            addSizeLayoutBinding.etSize.setText("");
            addSizeLayoutBinding.etPrice.setText("");
            addSizeLayoutBinding.ivUpload.setImageResource(R.mipmap.ic_upload_image);
        }
        dialogAddSize.show();
    }

    private String localPath;

    /**
     * 绑定图片
     *
     * @param localPath
     */
    @Override
    public void bindImage(String localPath) {
        this.localPath = localPath;
        ImageCacheUtils.loadImage(mContext, localPath, addSizeLayoutBinding.ivUpload);
    }

    /**
     * 添加商品规格（仅工厂和店铺拥有该权限）
     */
    private void addGoodsSpecification(int goodsId, boolean isEdit, GoodsselectGoodsSpecificationBean.DataBean item) {
        String name = addSizeLayoutBinding.etName.getText().toString().trim();
        String color = addSizeLayoutBinding.etColor.getText().toString().trim();
        String originalPrice = addSizeLayoutBinding.etOldPrice.getText().toString().trim();
        String spec = addSizeLayoutBinding.etSize.getText().toString().trim();
        String price = addSizeLayoutBinding.etPrice.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrggmc));
            return;
        }
        if (TextUtils.isEmpty(spec)) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrgg));
            return;
        }
        if (TextUtils.isEmpty(color)) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrys));
            return;
        }
        if (TextUtils.isEmpty(originalPrice)) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsryj));
            return;
        }
        if (TextUtils.isEmpty(price)) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrxsj));
            return;
        }
        if (TextUtils.isEmpty(localPath) && !isEdit) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrggt));
            return;
        }

        Map<String, RequestBody> params = new HashMap<>();
        if (!TextUtils.isEmpty(localPath)) {
            File file = new File(localPath);
            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
            //一定要加("AttachmentKey\"; filename=\"" +，不然失败
            params.put("files\"; filename=\"" + file.getName(), requestFile);
        }
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("color", color);
        map.put("spec", spec);
        map.put("originalPrice", originalPrice);
        map.put("price", price);
        if (isEdit) {
            if (params.size() == 0) {
                map.put("id", String.valueOf(item.getId()));
                goodseditGoodsSpecificationNoFile(map);
            } else {
                map.put("id", String.valueOf(item.getId()));
                goodseditGoodsSpecification(params, map);
            }
        } else {
            map.put("goodsId", String.valueOf(goodsId));
            goodsaddGoodsSpecification(params, map);
        }
    }
}