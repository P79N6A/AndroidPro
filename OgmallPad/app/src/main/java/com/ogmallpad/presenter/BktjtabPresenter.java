package com.ogmallpad.presenter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.R;
import com.ogmallpad.bean.JiaGeBean;
import com.ogmallpad.bean.ShopScreenBean;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.BktjtabContract;
import com.ogmallpad.databinding.DialogShopScreenLayoutBinding;
import com.ogmallpad.databinding.FgBrandLayoutBinding;
import com.ogmallpad.databinding.ItemFgLayoutBinding;
import com.ogmallpad.databinding.ItemShopScreenJgLayoutBinding;
import com.shan.netlibrary.bean.BrandlistBean;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * 爆款推荐
 * Created by chenjunshan on 2018-07-06.
 */

public class BktjtabPresenter extends HttpPresenter implements BktjtabContract.Presenter {
    private Context mContext;
    private BktjtabContract.View mView;
    private ArrayList<JiaGeBean> jiaGeBeans;
    private CommonDialog dialogSx;
    private int ppSecltor;
    private int maxPrice;
    private int minPrice;

    public BktjtabPresenter(Context mContext, BktjtabContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    /***********************************************筛选对话框**********************************/
    private CommonAdapter gvJgAdapter,gvFgAdapter,gvPgAdapter;
    private DialogShopScreenLayoutBinding ssbinding;
    private int fgSelectorPos;
    public void showSxDialog(final StylesBean styleBean, final BrandlistBean brandlistBean) {
        if (dialogSx == null) {
            dialogSx = new CommonDialog.Builder(mContext)
                    .setWidth(1f)
                    .setHeight(1f)
                    .setResId(R.layout.dialog_shop_screen_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setAnimResId(R.style.anim_left_menu)
                    .build();
        }
        dialogSx.show();

        ssbinding = (DialogShopScreenLayoutBinding) dialogSx.getBinding();
        ssbinding.llPrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSx.dismiss();
            }
        });
        ssbinding.tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshShop(fgSelectorPos,ppSecltor);
                dialogSx.dismiss();
            }
        });
        ssbinding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset(brandlistBean,styleBean);
            }
        });
        bindJgData();
        bingFgData(styleBean);
        bindPPData(brandlistBean);
    }

    public void bindPPData(final BrandlistBean brandlistBean) {
        ppSecltor = -1;
        if (brandlistBean != null) {
            gvPgAdapter = new CommonAdapter<FgBrandLayoutBinding, BrandlistBean.DataBean.ListBean>(mContext, R.layout.fg_brand_layout, brandlistBean.getData().getList()) {
                @Override
                protected void getItem(FgBrandLayoutBinding binding, BrandlistBean.DataBean.ListBean bean, int position) {
                    ImageCacheUtils.loadImage(mContext, bean.getHeadImage(), binding.imageview);
                    ImageCacheUtils.loadImage(mContext, bean.getImage(), binding.imageview2);
                    if (bean.isCheck()){
                        binding.llParent.setBackgroundResource(R.drawable.shape_grap8);
                    }else {
                        binding.llParent.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }
                @Override
                protected void itemOnclick(FgBrandLayoutBinding binding, BrandlistBean.DataBean.ListBean bean, int position) {
                    ppSecltor = position;
                    for (int i = 0; i < brandlistBean.getData().getList().size(); i++) {
                        if (i == position) {
                            brandlistBean.getData().getList().get(i).setCheck(true);
                            refreshShop(fgSelectorPos,ppSecltor);
                        } else {
                            brandlistBean.getData().getList().get(i).setCheck(false);
                        }
                    }

                    gvPgAdapter.notifyDataSetChanged();

                }
            };
            ssbinding.gvPpList.setAdapter(gvPgAdapter);
        }
    }

    private void bingFgData(final StylesBean styleBean) {
        fgSelectorPos = -1;
        gvFgAdapter = new CommonAdapter<ItemFgLayoutBinding,StylesBean.DataBean>(mContext,R.layout.item_fg_layout,styleBean.getData()) {
            @Override
            protected void getItem(ItemFgLayoutBinding binding, StylesBean.DataBean bean, int position) {
                binding.tvText.setText(bean.getName());
                binding.tvText.setChecked(bean.isCheck());
            }

            @Override
            protected void itemOnclick(ItemFgLayoutBinding binding, StylesBean.DataBean bean, int position) {
                fgSelectorPos = position;
                for (int i = 0; i < styleBean.getData().size(); i++) {
                    if (i == position) {
                        styleBean.getData().get(i).setCheck(true);
//                        ppSecltor = -1;
//                        refreshShop(fgSelectorPos, ppSecltor);
                    } else {
                        styleBean.getData().get(i).setCheck(false);
                    }
                }
                gvFgAdapter.notifyDataSetChanged();
//                EventBus.getDefault().post(new MessageEvent(MsgConstant.SHOP_SCREEN_SHUAXIN_FG,styleBean.getData().get(position).getId()));
            }
        };
        ssbinding.gvFgList.setAdapter(gvFgAdapter);
    }

    private void bindJgData() {
        jiaGeBeans = new ArrayList<>();
        JiaGeBean bean = new JiaGeBean(0,0);
        JiaGeBean bean1 = new JiaGeBean(0,2500);
        JiaGeBean bean2 = new JiaGeBean(2501,5000);
        JiaGeBean bean3 = new JiaGeBean(5001,7500);
        JiaGeBean bean4 = new JiaGeBean(7500,10000);
        JiaGeBean bean5 = new JiaGeBean(10000,0);

        jiaGeBeans.add(bean);
        jiaGeBeans.add(bean1);
        jiaGeBeans.add(bean2);
        jiaGeBeans.add(bean3);
        jiaGeBeans.add(bean4);
        jiaGeBeans.add(bean5);

        gvJgAdapter = new CommonAdapter<ItemShopScreenJgLayoutBinding,JiaGeBean>(mContext,R.layout.item_shop_screen_jg_layout, jiaGeBeans){
            @Override
            protected void getItem(ItemShopScreenJgLayoutBinding binding, JiaGeBean bean, int position) {
                if (bean.minPrice == 0 && bean.maxPrice == 0){
                    binding.tvText.setText("全部");
                }else if (bean.maxPrice == 0 && bean.minPrice != 0){
                    binding.tvText.setText(bean.minPrice+"以上");
                }else {
                    binding.tvText.setText(bean.minPrice+"-"+bean.maxPrice);
                }
                binding.tvText.setChecked(bean.isCheck);
            }

            @Override
            protected void itemOnclick(ItemShopScreenJgLayoutBinding binding, JiaGeBean bean, int position) {
                ssbinding.etMin.setText(String.valueOf(bean.minPrice));
                if (bean.maxPrice != 0)
                    ssbinding.etMax.setText(String.valueOf(bean.maxPrice));
                else
                    ssbinding.etMax.setText("");
                for (int i = 0; i < jiaGeBeans.size(); i++) {
                    if (i == position) {
                        jiaGeBeans.get(i).isCheck = true;
                    } else {
                        jiaGeBeans.get(i).isCheck = false;
                    }
                }
                gvJgAdapter.notifyDataSetChanged();
            }
        };
        ssbinding.gvBrand.setAdapter(gvJgAdapter);
    }

    public void refreshShop(int fgSelectorPos, int ppSecltor) {
        StylesBean.DataBean fgDataBean = null;
        BrandlistBean.DataBean.ListBean ppDataBean = null;
        if (fgSelectorPos >= 0){
            fgDataBean = (StylesBean.DataBean) gvFgAdapter.getItem(fgSelectorPos);
        }
        if (ppSecltor >= 0){
            ppDataBean = (BrandlistBean.DataBean.ListBean) gvPgAdapter.getItem(ppSecltor);
        }
        String minStr = ssbinding.etMin.getText().toString().trim();
        String maxStr = ssbinding.etMax.getText().toString().trim();

        if (!TextUtils.isEmpty(maxStr) && !TextUtils.isEmpty(minStr)) {   //两有
            if ((Integer.parseInt(maxStr) <= Integer.parseInt(minStr)) && Integer.parseInt(maxStr) != 0) {
                ToastUtils.toast("最高价不能低于最低价");
                return;
            }
        }
        ShopScreenBean bean = new ShopScreenBean();
        if (!TextUtils.isEmpty(maxStr)){   //有最低价
            maxPrice = Integer.parseInt(maxStr);
            bean.setMaxPrice(maxPrice);

        }
        if (!TextUtils.isEmpty(minStr)){  //有最高价
            minPrice = Integer.parseInt(minStr);
            bean.setMinPrice(minPrice);
        }
        if (fgDataBean != null) {
            bean.setFgData(fgDataBean.getId());
        }else {
            bean.setFgData(0);
        }
        if (ppDataBean != null) {
            bean.setPpData(ppDataBean.getId());
        }else {
            bean.setPpData(0);
        }
        Log.e("mTag","postpostpostpostpostpostpost");
        EventBus.getDefault().post(new MessageEvent(MsgConstant.SPSX_CONSTANT_S,bean));
        if (mView != null){
            mView.query(bean);
        }
    }
    private void reset(BrandlistBean ppData, StylesBean fgData) {
        ssbinding.etMin.setText("");
        ssbinding.etMax.setText("");
        bindJgData();
        for (BrandlistBean.DataBean.ListBean dataBean : ppData.getData().getList()){
            dataBean.setCheck(false);
        }
        for (StylesBean.DataBean dataBean : fgData.getData()){
            dataBean.setCheck(false);
        }
        bingFgData(fgData);
        bindPPData(ppData);
    }

}