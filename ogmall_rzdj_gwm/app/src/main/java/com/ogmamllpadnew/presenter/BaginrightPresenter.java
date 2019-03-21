package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.AppUtils;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.JiaGeBean;
import com.ogmamllpadnew.bean.ShopScreenBean;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.BaginrightContract;
import com.ogmamllpadnew.databinding.DialogShopScreen2LayoutBinding;
import com.ogmamllpadnew.databinding.FgBrandLayoutBinding;
import com.ogmamllpadnew.databinding.ItemFgLayoutBinding;
import com.ogmamllpadnew.databinding.ItemShopScreenJgLayoutBinding;
import com.ogmamllpadnew.databinding.SelectBrandLayoutBinding;
import com.shan.netlibrary.bean.BrandselectBrandByCategoryAndOldHouseStyleBean;
import com.shan.netlibrary.bean.BrandselectOldHouseStyleByCategoryBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 拎包入住右边
 * Created by 陈俊山 on 2018-11-10.
 */

public class BaginrightPresenter extends HttpPresenter implements BaginrightContract.Presenter {
    private Context mContext;
    private BaginrightContract.View mView;
    private int minPrice;
    private int maxPrice;
    private SelectBrandLayoutBinding layoutBinding1;

    public BaginrightPresenter(Context mContext, BaginrightContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    public CommonDialog dialogSelect;
    private DialogShopScreen2LayoutBinding layoutBinding;
    /**R.layout.dialog_shop_screen2_layout*/

    /**
     * 初始化状态栏
     */
    private void intiStatusBar() {
        if (layoutBinding != null && AppUtils.getSystemVersion() < 19) {
            layoutBinding1.statusBar.setVisibility(View.GONE);
        } else {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) layoutBinding1.statusBar.getLayoutParams();
            linearParams.height = AppUtils.getStatusBarHeight();
            layoutBinding1.statusBar.setLayoutParams(linearParams);
        }
    }

    @Override
    public void showSelectDialog(final List<BrandselectOldHouseStyleByCategoryBean.DataBean> fgData, final List<BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean> ppData) {
        if (dialogSelect == null) {
            dialogSelect = new CommonDialog.Builder(mContext)
                    .setWidth(1f)
                    .setHeight(1f)
                    .setResId(R.layout.dialog_shop_screen2_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setAnimResId(R.style.anim_left_menu)
                    .build();

        }
        layoutBinding = (DialogShopScreen2LayoutBinding) dialogSelect.getBinding();
        layoutBinding.llPrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogSelect.dismiss();
            }
        });
        layoutBinding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(ppData, fgData);

            }
        });
        layoutBinding.tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refreshShop(fgSelectorPos, ppSecltor);
                dialogSelect.dismiss();
            }
        });
        bindJgData();
        bindPgData(ppData);
        bindfgData(fgData);
        dialogSelect.show();
    }

    private  CommonAdapter gvFgAdapter;
    private int fgSelectorPos = -1;
    public void bindfgData(final List<BrandselectOldHouseStyleByCategoryBean.DataBean> fgData) {
        gvFgAdapter = new CommonAdapter<ItemFgLayoutBinding,BrandselectOldHouseStyleByCategoryBean.DataBean>(mContext,R.layout.item_fg_layout,fgData) {
            @Override
            protected void getItem(ItemFgLayoutBinding binding, BrandselectOldHouseStyleByCategoryBean.DataBean bean, int position) {
                binding.tvText.setText(bean.getName());
                binding.tvText.setChecked(bean.isCheck());
            }

            @Override
            protected void itemOnclick(ItemFgLayoutBinding binding, BrandselectOldHouseStyleByCategoryBean.DataBean bean, int position) {
                fgSelectorPos = position;
                for (int i = 0; i < fgData.size(); i++) {
                    if (i == position) {
                        fgData.get(i).setCheck(true);
//                        ppSecltor = -1;
//                        refreshShop(fgSelectorPos, ppSecltor);
                    } else {
                        fgData.get(i).setCheck(false);
                    }
                }
                gvFgAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new MessageEvent(MsgConstant.SHOP_SCREEN_SHUAXIN_FG_BAGIN,fgData.get(position).getId()));
            }
        };
        layoutBinding.gvFgList.setAdapter(gvFgAdapter);
    }

    public void refreshShop(int fgSelectorPos, int ppSecltor) {

        BrandselectOldHouseStyleByCategoryBean.DataBean fgDataBean = null;
        BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean ppDataBean = null;
        if (fgSelectorPos >= 0){
            fgDataBean = (BrandselectOldHouseStyleByCategoryBean.DataBean) gvFgAdapter.getItem(fgSelectorPos);
        }
        if (ppSecltor >= 0){
            ppDataBean = (BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean) gvPgAdapter.getItem(ppSecltor);
        }
        String minStr = layoutBinding.etMin.getText().toString().trim();
        String maxStr = layoutBinding.etMax.getText().toString().trim();

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
        EventBus.getDefault().post(new MessageEvent(MsgConstant.SPSX_CONSTANT_S_BAGIN,bean));
    }

    private CommonAdapter gvPgAdapter;
    private int ppSecltor = -1;
    public void bindPgData(final List<BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean> ppData) {
        if (ppData != null) {
            gvPgAdapter = new CommonAdapter<FgBrandLayoutBinding, BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean>(mContext, R.layout.fg_brand_layout, ppData) {
                @Override
                protected void getItem(FgBrandLayoutBinding binding, BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean bean, int position) {
                    ImageCacheUtils.loadImage(mContext, bean.getHeadimage(), binding.imageview);
                    ImageCacheUtils.loadImage(mContext, bean.getImage(), binding.imageview2);
                    if (bean.isChick()){
                        binding.llParent.setBackgroundResource(R.drawable.shape_grap8);
                    }else {
                        binding.llParent.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }

                }

                @Override
                protected void itemOnclick(FgBrandLayoutBinding binding, BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean bean, int position) {
                    ppSecltor = position;
                    for (int i = 0; i < ppData.size(); i++) {
                        if (i == position) {
                            ppData.get(i).setChick(true);
                            refreshShop(fgSelectorPos,ppSecltor);
                        } else {
                            ppData.get(i).setChick(false);
                        }
                    }

                    gvPgAdapter.notifyDataSetChanged();

                }
            };
            layoutBinding.gvPpList.setAdapter(gvPgAdapter);
        }
    }


    private CommonAdapter gvJgAdapter;
    List<JiaGeBean> jiaGeBeans;
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
        gvJgAdapter = new CommonAdapter<ItemShopScreenJgLayoutBinding,JiaGeBean>(mContext,R.layout.item_shop_screen_jg_layout, jiaGeBeans) {
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
                layoutBinding.etMin.setText(String.valueOf(bean.minPrice));
                if (bean.maxPrice != 0)
                    layoutBinding.etMax.setText(String.valueOf(bean.maxPrice));
                else
                    layoutBinding.etMax.setText("");
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
        layoutBinding.gvBrand.setAdapter(gvJgAdapter);
    }
    private void reset(List<BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean> ppData, List<BrandselectOldHouseStyleByCategoryBean.DataBean> fgData) {
        layoutBinding.etMin.setText("");
        layoutBinding.etMax.setText("");
        bindJgData();
        for (BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean dataBean : ppData){
            dataBean.setChick(false);
        }
        for (BrandselectOldHouseStyleByCategoryBean.DataBean dataBean : fgData){
            dataBean.setCheck(false);
        }
        bindfgData(fgData);
        bindPgData(ppData);
    }
    @Override
    public void dismissDialog() {
        if (dialogSelect != null) {
            dialogSelect.dismiss();
        }
    }
}