package com.ogmallpad.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;
import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.R;
import com.ogmallpad.bean.JiaGeBean;
import com.ogmallpad.bean.ShareBean;
import com.ogmallpad.bean.ShopCartBean;
import com.ogmallpad.bean.ShopScreenBean;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.BaginRightContract;
import com.ogmallpad.databinding.BrandItemLayoutBinding;
import com.ogmallpad.databinding.DialogImageLayoutBinding;
import com.ogmallpad.databinding.DialogShopScreenLayoutBinding;
import com.ogmallpad.databinding.EditItemLayoutBinding;
import com.ogmallpad.databinding.FgBrandLayoutBinding;
import com.ogmallpad.databinding.FiltrateLayoutBinding;
import com.ogmallpad.databinding.ItemFgLayoutBinding;
import com.ogmallpad.databinding.ItemShopScreenJgLayoutBinding;
import com.ogmallpad.databinding.RqCodeLayoutBinding;
import com.ogmallpad.databinding.ShopLayoutBinding;
import com.ogmallpad.ui.fragment.BaginLeftFragment;
import com.shan.netlibrary.bean.BrandlistBean;
import com.shan.netlibrary.bean.RoomTypespecsBean;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.net.HttpPresenter;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 拎包入住
 * Created by chenjunshan on 2018-08-21.
 */

public class BaginRightPresenter extends HttpPresenter implements BaginRightContract.Presenter, View.OnClickListener {
    private Context mContext;
    private BaginRightContract.View mView;
    private int minPrice;
    private int maxPrice;
    private CommonDialog dialogSx;
    private int ppSecltor;
    private List<JiaGeBean> jiaGeBeans;

    public BaginRightPresenter(Context mContext, BaginRightContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    /**
     * *********************************************************************************************
     * 筛选弹窗
     */
    private FiltrateLayoutBinding filtrateBinding = null;
    private CommonAdapter styleAdapter;
    private CommonAdapter brandAdapter;
    private CommonDialog filtrateDialog;
    private List<StylesBean.DataBean> stylesBeans;
    private List<BrandlistBean.DataBean.ListBean> brands;

    @Override
    public void showType(View view, StylesBean stylesBean, BrandlistBean brandlistBean) {
        if (stylesBean == null) {
            stylesBean = new StylesBean();
        }
        if (brandlistBean == null) {
            brandlistBean = new BrandlistBean();
        }
        stylesBeans = stylesBean.getData();
        brands = brandlistBean.getData().getList();

        if (filtrateDialog == null) {
            filtrateDialog = new CommonDialog.Builder(mContext)
                    .setWidth(0f)
                    .setHeight(0.96f)
                    .setResId(R.layout.filtrate_layout)
                    .setGravity(Gravity.RIGHT | Gravity.BOTTOM)
                    .setAnimResId(R.style.anim_left_menu)
                    .build();
            filtrateBinding = (FiltrateLayoutBinding) filtrateDialog.getBinding();
            filtrateBinding.llBrandTile.setOnClickListener(this);
            filtrateBinding.llStyleTile.setOnClickListener(this);
            filtrateBinding.llPriceTile.setOnClickListener(this);
            filtrateBinding.btnOk.setOnClickListener(this);
            //重置
            filtrateBinding.btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filtrateBinding.etMin.setText("");
                    filtrateBinding.etMax.setText("");
                    filtrateBinding.tvStyle.setText("");
                    filtrateBinding.tvBrand.setText("");
                    for (int i = 0; i < stylesBeans.size(); i++) {
                        stylesBeans.get(i).setCheck(false);
                    }
                    for (int i = 0; i < brands.size(); i++) {
                        brands.get(i).setCheck(false);
                    }
                    mView.setStyleId(null);
                    mView.setBrandId(null);
                    mView.setMinPrice(null);
                    mView.setMaxPrice(null);
                    mView.query();
                    brandAdapter.notifyDataSetChanged();
                    styleAdapter.notifyDataSetChanged();
                }
            });
        }
        styleAdapter = new CommonAdapter<EditItemLayoutBinding, StylesBean.DataBean>(mContext, R.layout.edit_item_layout, stylesBeans) {
            @Override
            protected void getItem(EditItemLayoutBinding binding, StylesBean.DataBean bean, int position) {
                binding.tvName.setText(bean.getName());
                if (bean.isCheck()) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_type_on);
                } else {
                    binding.tvName.setBackgroundResource(R.drawable.shape_type_off);
                }
            }

            @Override
            protected void itemOnclick(EditItemLayoutBinding binding, StylesBean.DataBean bean, int position) {
                if (bean.isCheck()) {
                    bean.setCheck(false);
                    filtrateBinding.tvStyle.setText("");
                    mView.setStyleId(null);
                } else {
                    filtrateBinding.tvStyle.setText(bean.getName());
                    mView.setStyleId(String.valueOf(bean.getId()));
                    for (int i = 0; i < stylesBeans.size(); i++) {
                        if (i == position) {
                            stylesBeans.get(i).setCheck(true);
                        } else {
                            stylesBeans.get(i).setCheck(false);
                        }
                    }
                }
                styleAdapter.notifyDataSetChanged();
            }
        };
        //绑定风格数据
        filtrateBinding.gvStyle.setAdapter(styleAdapter);
        //绑定品牌数据
        brandAdapter = new CommonAdapter<BrandItemLayoutBinding, BrandlistBean.DataBean.ListBean>(mContext, R.layout.brand_item_layout, brands) {
            @Override
            protected void getItem(BrandItemLayoutBinding binding, BrandlistBean.DataBean.ListBean bean, int position) {
                ImageCacheUtils.loadImage(mContext, bean.getImage(), binding.iv2);
                ImageCacheUtils.loadImage(mContext, bean.getHeadImage(), binding.iv);
                if (bean.isCheck()) {
                    binding.cb.setChecked(true);
                    binding.cb.setVisibility(View.VISIBLE);
                } else {
                    binding.cb.setChecked(false);
                    binding.cb.setVisibility(View.GONE);
                }
            }

            @Override
            protected void itemOnclick(BrandItemLayoutBinding binding, BrandlistBean.DataBean.ListBean bean, int position) {
                if (bean.isCheck()) {
                    bean.setCheck(false);
                    filtrateBinding.tvBrand.setText("");
                    mView.setBrandId(null);
                } else {
                    filtrateBinding.tvBrand.setText(bean.getName());
                    mView.setBrandId(String.valueOf(bean.getId()));
                    for (int i = 0; i < brands.size(); i++) {
                        if (i == position) {
                            brands.get(i).setCheck(true);
                        } else {
                            brands.get(i).setCheck(false);
                        }
                    }
                }
                brandAdapter.notifyDataSetChanged();
            }
        };
        filtrateBinding.gvBrand.setAdapter(brandAdapter);
        filtrateDialog.show();
    }

    /**
     * 刷新帅选数据
     */
    public void refreshFiltrate() {
        if (filtrateBinding != null) {
            filtrateBinding.tvBrand.setText("");
            filtrateBinding.tvStyle.setText("");
            filtrateBinding.etMax.setText("");
            filtrateBinding.etMin.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_style_tile:
                if (filtrateBinding.gvStyle.getVisibility() == View.VISIBLE) {
                    filtrateBinding.gvStyle.setVisibility(View.GONE);
                    filtrateBinding.cbStyle.setChecked(false);
                } else {
                    filtrateBinding.cbStyle.setChecked(true);
                    filtrateBinding.cbBrand.setChecked(false);
                    filtrateBinding.cbPrice.setChecked(false);
                    filtrateBinding.gvStyle.setVisibility(View.VISIBLE);
                    filtrateBinding.gvBrand.setVisibility(View.GONE);
                    filtrateBinding.llPrice.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_brand_tile:
                if (filtrateBinding.gvBrand.getVisibility() == View.VISIBLE) {
                    filtrateBinding.gvBrand.setVisibility(View.GONE);
                    filtrateBinding.cbBrand.setChecked(false);
                } else {
                    filtrateBinding.cbStyle.setChecked(false);
                    filtrateBinding.cbBrand.setChecked(true);
                    filtrateBinding.cbPrice.setChecked(false);
                    filtrateBinding.gvStyle.setVisibility(View.GONE);
                    filtrateBinding.gvBrand.setVisibility(View.VISIBLE);
                    filtrateBinding.llPrice.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_price_tile:
                if (filtrateBinding.llPrice.getVisibility() == View.VISIBLE) {
                    filtrateBinding.llPrice.setVisibility(View.GONE);
                    filtrateBinding.cbPrice.setChecked(false);
                } else {
                    filtrateBinding.cbStyle.setChecked(false);
                    filtrateBinding.cbBrand.setChecked(false);
                    filtrateBinding.cbPrice.setChecked(true);
                    filtrateBinding.gvStyle.setVisibility(View.GONE);
                    filtrateBinding.gvBrand.setVisibility(View.GONE);
                    filtrateBinding.llPrice.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_ok:
//                minPrice = filtrateBinding.etMin.getText().toString().trim();
//                maxPrice = filtrateBinding.etMax.getText().toString().trim();
//                mView.setMinPrice(minPrice);
//                mView.setMaxPrice(maxPrice);
//                mView.query();
//                filtrateDialog.dismiss();
                break;
            case R.id.btn_share:
                share(rqCodeBinding.etPlanName.getText().toString().trim(),
                        rqCodeBinding.etPlanArea.getText().toString().trim());
                break;
        }
    }


    /**
     * *********************************************************************************************
     * 分享成功弹窗
     */
    private CommonDialog dialog;
    private RqCodeLayoutBinding rqCodeBinding;

    @Override
    public void showRqDialog() {
        if (dialog == null) {
            dialog = new CommonDialog.Builder(mContext)
                    .setWidth(0.3f)
                    .setHeight(0f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setResId(R.layout.rq_code_layout)
                    .setGravity(Gravity.CENTER)
                    .build();
            rqCodeBinding = (RqCodeLayoutBinding) dialog.getBinding();
            rqCodeBinding.btnShare.setOnClickListener(this);
        }
        rqCodeBinding.ll1.setVisibility(View.GONE);
        rqCodeBinding.ll2.setVisibility(View.VISIBLE);
        dialog.show();
    }

    @Override
    public void setRqCode(String image) {
        rqCodeBinding.etPlanArea.setText("");
        rqCodeBinding.etPlanName.setText("");
        rqCodeBinding.ll1.setVisibility(View.VISIBLE);
        rqCodeBinding.ll2.setVisibility(View.GONE);
        Bitmap mBitmap = CodeUtils.createImage(image, 400, 400, null);
        rqCodeBinding.image.setImageBitmap(mBitmap);
    }

    /**
     * 分享
     */
    @Override
    public void share(String name, String area) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrfamc));
            return;
        }
        if (TextUtils.isEmpty(area)) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrxymj));
            return;
        }
        //获取所有数据
        List<RoomTypespecsBean.DataBean> datas = BaginLeftFragment.roomTypespecsBean.getData();
        List<ShareBean> shareBeanList = new ArrayList<>();
        for (int k = 0; k < datas.size(); k++) {
            RoomTypespecsBean.DataBean dataBean = datas.get(k);
            ShareBean shareBean = new ShareBean();
            shareBean.setName(dataBean.getSpecName());
            List<RoomTypespecsBean.DataBean.CategoryListBean> categoryListBeans = dataBean.getCategoryList();
            List<ShareBean.ProductsBean> productsBeans = new ArrayList<>();
            for (int i = 0; i < categoryListBeans.size(); i++) {
                List<RoomTypespecsBean.DataBean.CategoryListBean.ProductBean> products = categoryListBeans.get(i).getProduct();
                for (int j = 0; j < products.size(); j++) {
                    ShareBean.ProductsBean productsBean = new ShareBean.ProductsBean();
                    productsBean.setCount(products.get(j).getCount());
                    productsBean.setSpec_id(products.get(j).getSpec_id());
                    productsBeans.add(productsBean);
                }
            }
            //产品数大于0则添加
            if (productsBeans.size() > 0) {
                shareBean.setProducts(productsBeans);
                shareBeanList.add(shareBean);
            }
        }
        String data = new Gson().toJson(shareBeanList);
        LogUtils.d("data:"+data);
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("roomTypeId", BaginLeftFragment.roomTypeId);
        map.put("area", area);
        map.put("jsonData", data);
        //分享
        bagshare(map);
    }

    /**
     * 一键加入购物车
     */
    @Override
    public void addShop() {
        List<ShopCartBean> shopCartBeans = new ArrayList<>();
        //获取所有数据
        List<RoomTypespecsBean.DataBean> datas = BaginLeftFragment.roomTypespecsBean.getData();
        for (int k = 0; k < datas.size(); k++) {
            RoomTypespecsBean.DataBean dataBean = datas.get(k);
            List<RoomTypespecsBean.DataBean.CategoryListBean> categoryListBeans = dataBean.getCategoryList();
            for (int i = 0; i < categoryListBeans.size(); i++) {
                List<RoomTypespecsBean.DataBean.CategoryListBean.ProductBean> products = categoryListBeans.get(i).getProduct();
                for (int j = 0; j < products.size(); j++) {
                    ShopCartBean shopCartBean = new ShopCartBean();
                    shopCartBean.setCount(products.get(j).getCount());
                    shopCartBean.setProductId(products.get(j).getProductId());
                    shopCartBean.setSpecificationId(products.get(j).getSpec_id());
                    shopCartBeans.add(shopCartBean);
                }
            }
        }
        shopCartBeans = removeDuplicate(shopCartBeans);
        String data = new Gson().toJson(shopCartBeans);
        //加入购物车
//        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), data);
//        cartsave(body);

        //批量收藏
        for (int i = 0 ; i < shopCartBeans.size() ; i++){
            Map<String,String> params = new HashMap<>();
            params.put("id", String.valueOf(shopCartBeans.get(i).getProductId()));
            params.put("isCollect", String.valueOf(true));
            centersavecollects(params);
        }
    }

    /**
     * *********************************************************************************************
     * 分享成功弹窗
     */
    private CommonDialog shopDialog;
    private ShopLayoutBinding shopBinding;

    @Override
    public void showShop() {
        if (shopDialog == null) {
            shopDialog = new CommonDialog.Builder(mContext)
                    .setWidth(0.3f)
                    .setHeight(0f)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setResId(R.layout.shop_layout)
                    .setGravity(Gravity.CENTER)
                    .build();
            shopBinding = (ShopLayoutBinding) shopDialog.getBinding();
            shopBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shopDialog.dismiss();
                }
            });
        }
        shopDialog.show();
    }

    /**
     * 删除重复数据
     *
     * @param list
     * @return
     */
    private List removeDuplicate(List<ShopCartBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getSpecificationId() == list.get(i).getSpecificationId()) {
                    list.get(i).setCount(list.get(i).getCount() + list.get(j).getCount());
                    list.remove(j);
                }
            }
        }
        return list;
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
                imageDialog.dismiss();
            }
        });
        ImageCacheUtils.loadImage(mContext, img, imageBinding.imageView);
        imageDialog.show();
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
//        EventBus.getDefault().post(new MessageEvent(MsgConstant.SPSX_CONSTANT_S,bean));
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