package com.ogmallpad.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.BaseGridView;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.R;
import com.ogmallpad.databinding.FgProductlistLayoutBinding;
import com.ogmallpad.databinding.RqCodeLayoutBinding;
import com.shan.netlibrary.bean.CentersavecollectsBean;
import com.shan.netlibrary.bean.DesigndetailsBean;
import com.shan.netlibrary.bean.ProductsshareBean;
import com.shan.netlibrary.net.AppParams;
import com.shan.netlibrary.net.CancelRequestListener;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by chenjunshan on 2017/02/05.
 * <p>
 * 图片轮播控件ConvenientBanner
 */
public class PlanDetailsBannerAdapter implements Holder<List<DesigndetailsBean.DataBean.ProductsBean>>, CancelRequestListener {
    private BaseGridView baseGridView;
    private Context context;
    private List<CommonAdapter> adapters;

    @Override
    public View createView(Context context) {
        this.context = context;
        baseGridView = (BaseGridView) LayoutInflater.from(context).inflate(R.layout.viewpager_item, null);
        return baseGridView;
    }

    private DesigndetailsBean.DataBean.ProductsBean productsBean;

    @Override
    public void UpdateUI(final Context context, int position, List<DesigndetailsBean.DataBean.ProductsBean> data) {
        adapters = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            final int j = i;
            CommonAdapter adapter = new CommonAdapter<FgProductlistLayoutBinding, DesigndetailsBean.DataBean.ProductsBean>(context, R.layout.fg_productlist_layout, data) {
                @Override
                protected void getItem(FgProductlistLayoutBinding binding, final DesigndetailsBean.DataBean.ProductsBean bean, int position) {
                    ImageCacheUtils.loadImage(context, bean.getImage(), binding.ivMenu);
                    binding.tvTitle.setText(bean.getName());
                    binding.tvNum.setText(context.getResources().getString(R.string.str_sl) + bean.getCount());
                    binding.tvMoney.setText("¥" + bean.getPrice());
                    binding.tvCollect.setChecked(bean.isCollect());
                    binding.tvCollect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            productsBean = bean;
                            Map<String, String> map = new HashMap<>();
                            map.put("id", String.valueOf(bean.getId()));
                            map.put("isCollect", String.valueOf(!bean.isCollect()));
                            centersavecollects(map, j);
                        }
                    });
                    binding.tvShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, String> map = new HashMap<>();
                            map.put("productId", String.valueOf(bean.getId()));
                            productsshare(map);
                        }
                    });
                }

                @Override
                protected void itemOnclick(FgProductlistLayoutBinding binding, DesigndetailsBean.DataBean.ProductsBean bean, int position) {

                }
            };
            baseGridView.setAdapter(adapter);
            adapters.add(adapter);
        }
    }

    /**
     * 批量收藏
     */
    public void centersavecollects(Map<String, String> map, final int position) {
        HttpCallback callback = new HttpCallback<CentersavecollectsBean>(context, this, false) {
            @Override
            protected void onSuccess(CentersavecollectsBean baseBean) {
                //收藏成功
                productsBean.setCollect(!productsBean.isCollect());
                if (productsBean.isCollect()) {
                    ToastUtils.toast(context.getResources().getString(R.string.str_sccg));
                } else {
                    ToastUtils.toast(context.getResources().getString(R.string.str_qxsccg));
                }
                adapters.get(position).notifyDataSetChanged();
            }

            @Override
            protected void onFailure(Throwable e) {
            }
        };
        HttpBuilder.getInstance().execute(HttpBuilder.httpService.centersavecollects(AppParams.getParams(map)), callback);
    }

    /**
     * 产品分享
     *
     * @param map
     */
    public void productsshare(Map<String, String> map) {
        HttpCallback callback = new HttpCallback<ProductsshareBean>(context, this, false) {
            @Override
            protected void onSuccess(ProductsshareBean baseBean) {
                showRqDialog(baseBean.getData().getUrl());
            }

            @Override
            protected void onFailure(Throwable e) {
            }
        };
        HttpBuilder.getInstance().execute(HttpBuilder.httpService.productsshare(AppParams.getParams(map)), callback);
    }

    @Override
    public void cancelRequest() {

    }

    @Override
    public void cancelAllRequest() {

    }

    /**
     * *********************************************************************************************
     * 分享成功弹窗
     */
    private CommonDialog dialog;
    private RqCodeLayoutBinding rqCodeBinding;

    private void showRqDialog(String image) {
        if (dialog == null) {
            dialog = new CommonDialog.Builder(context)
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