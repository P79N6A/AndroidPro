package com.ogmamllpadnew.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.ui.activity.CommonActivity;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.PublicUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.ColorSizeBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.databinding.FgBaginRightTypeItemLayoutBinding;
import com.ogmamllpadnew.databinding.ProductDetailsHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.RqCodeLayoutBinding;
import com.ogmamllpadnew.databinding.SpxxLayoutBinding;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.ProductcollectProductBean;
import com.shan.netlibrary.bean.ProductsdetailsBean;
import com.shan.netlibrary.bean.ProductsdetailsBean.DataBean.SpecArrBean.PropertiesBean;
import com.shan.netlibrary.net.AppParams;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 18-11-8.
 */

public class ProductDetailsHeaderFragment extends BaseFragment<ProductDetailsHeaderLayoutBinding, Object> {
    @Override
    public int bindLayout() {
        return R.layout.product_details_header_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        mBinding.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中间横线
        mBinding.tvOriginalPrice.getPaint().setAntiAlias(true);//抗锯齿
    }

    private ProductsdetailsBean detailsBean;//商品详情数据
    private ProductsdetailsBean.DataBean.SpecArrBean mSpecArrBean;//商品规格数据

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.PRODUCTDETAILS) {
            detailsBean = (ProductsdetailsBean) msgEvent.getBean();
            //获取第一个规格数据
            if (detailsBean.getData().getSpecArr().size() > 0) {
                mSpecArrBean = detailsBean.getData().getSpecArr().get(0);
            } else {
                mSpecArrBean = new ProductsdetailsBean.DataBean.SpecArrBean();
            }
            //处理数据
            handleData();
            //绑定数据
            bindData();
        }
    }

    /**
     * #############################处理数据START###################################
     */
    private List<ColorSizeBean> sizeData;
    private List<ColorSizeBean> colorData;

    private void handleData() {
        sizeData = new ArrayList<>();
        colorData = new ArrayList<>();

        if (detailsBean != null) {
            List<ProductsdetailsBean.DataBean.SpecArrBean> listBeans = detailsBean.getData().getSpecArr();
            //绑定颜色规格数据
            for (int i = 0; i < listBeans.size(); i++) {
                String size = listBeans.get(i).getSpecSize();
                String color = listBeans.get(i).getSpecColor();
                boolean isAddSize = true;//是否添加尺寸
                boolean isAddColor = true;//是否添加大小
                for (int j = 0; j < sizeData.size(); j++) {
                    if (size.equals(sizeData.get(j).getName())) {
                        isAddSize = false;
                        break;
                    }
                }
                for (int j = 0; j < colorData.size(); j++) {
                    if (color.equals(colorData.get(j).getName())) {
                        isAddColor = false;
                        break;
                    }
                }
                if (isAddSize) {
                    sizeData.add(new ColorSizeBean(size, 0));
                }
                if (isAddColor) {
                    colorData.add(new ColorSizeBean(color, 0));
                }
            }
        }
    }

    /**
     * #############################处理数据END###################################
     */

    private CommonAdapter sizeAdapter;
    private CommonAdapter colorAdapter;

    private void bindData() {
        bindCommonData();
        sizeAdapter = new CommonAdapter<FgBaginRightTypeItemLayoutBinding, ColorSizeBean>(getActivity(), R.layout.fg_bagin_right_type_item_layout, sizeData) {
            @Override
            protected void getItem(FgBaginRightTypeItemLayoutBinding binding, ColorSizeBean bean, int position) {
                binding.tvName.setText(bean.getName());
                LogUtils.d("getItemGg:" + bean.getName());
                if (bean.getStatus() == 0) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_dash_grap);
                    binding.tvName.setTextColor(Color.parseColor("#777777"));
                } else if (bean.getStatus() == 1) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_dash_red);
                    binding.tvName.setTextColor(Color.parseColor("#c73c3a"));
                } else if (bean.getStatus() == 2) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_dash_grap_hide);
                    binding.tvName.setTextColor(Color.parseColor("#dddddd"));
                }
            }

            @Override
            protected void itemOnclick(FgBaginRightTypeItemLayoutBinding binding, ColorSizeBean bean, int childPosition) {
                if (bean.getStatus() == 0) {
                    //设置规格数据
                    detailsBean.getData().setSpec_size(bean.getName());
                    bean.setStatus(1);
                    for (int i = 0; i < sizeData.size(); i++) {
                        if (i != childPosition) {
                            if (sizeData.get(i).getStatus() == 1) {
                                sizeData.get(i).setStatus(0);
                            }
                        }
                    }
                    //相同规格
                    List<ProductsdetailsBean.DataBean.SpecArrBean> listBeans = detailsBean.getData().getSpecArr();
                    for (int i = 0; i < listBeans.size(); i++) {
                        ProductsdetailsBean.DataBean.SpecArrBean specArrBean = listBeans.get(i);
                        //判断规格是否相等
                        if (bean.getName().equals(specArrBean.getSpecSize())) {
                            for (int j = 0; j < colorData.size(); j++) {
                                //设置可点击状态
                                if (specArrBean.getSpecColor().equals(colorData.get(j).getName())) {
                                    if (colorData.get(j).getStatus() != 1) {
                                        colorData.get(j).setStatus(4);//临时可以选中的值4
                                    } else {
                                        colorData.get(j).setStatus(5);//临时已选中的值5
                                    }
                                }
                            }
                        }
                    }
                    //设置不能选择
                    for (int j = 0; j < colorData.size(); j++) {
                        if (colorData.get(j).getStatus() == 4) {
                            colorData.get(j).setStatus(0);
                        } else if (colorData.get(j).getStatus() == 5) {
                            colorData.get(j).setStatus(1);
                        } else {
                            colorData.get(j).setStatus(2);
                        }
                    }
                    //设置图片和价格
                    if (!TextUtils.isEmpty(detailsBean.getData().getSpec_color()) && !TextUtils.isEmpty(detailsBean.getData().getSpec_size())) {
                        for (int i = 0; i < detailsBean.getData().getSpecArr().size(); i++) {
                            ProductsdetailsBean.DataBean.SpecArrBean specArrBean = detailsBean.getData().getSpecArr().get(i);
                            //判断颜色和规格是否都相等
                            if (detailsBean.getData().getSpec_color().equals(specArrBean.getSpecColor()) && detailsBean.getData().getSpec_size().equals(specArrBean.getSpecSize())) {
                                mSpecArrBean = specArrBean;
                                bindCommonData();
                                break;
                            }
                        }
                    }
                    colorAdapter.notifyDataSetChanged();
                    sizeAdapter.notifyDataSetChanged();
                } else if (bean.getStatus() == 1) {
                    bean.setStatus(0);
                    //设置规格数据
                    detailsBean.getData().setSpec_size("");
                    //设置都可选择
                    for (int j = 0; j < colorData.size(); j++) {
                        if (colorData.get(j).getStatus() != 1) {
                            colorData.get(j).setStatus(0);
                        }
                    }
                    colorAdapter.notifyDataSetChanged();
                    sizeAdapter.notifyDataSetChanged();
                }
            }
        };
        //大小
        mBinding.gvSize.setAdapter(sizeAdapter);

        //颜色
        colorAdapter = new CommonAdapter<FgBaginRightTypeItemLayoutBinding, ColorSizeBean>(getActivity(), R.layout.fg_bagin_right_type_item_layout, colorData) {
            @Override
            protected void getItem(FgBaginRightTypeItemLayoutBinding binding, ColorSizeBean bean, int position) {
                binding.tvName.setText(bean.getName());
                LogUtils.d("getItemColor:" + bean.getName());
                if (bean.getStatus() == 0) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_dash_grap);
                    binding.tvName.setTextColor(Color.parseColor("#777777"));
                } else if (bean.getStatus() == 1) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_dash_red);
                    binding.tvName.setTextColor(Color.parseColor("#c73c3a"));
                } else if (bean.getStatus() == 2) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_dash_grap_hide);
                    binding.tvName.setTextColor(Color.parseColor("#dddddd"));
                }
            }

            @Override
            protected void itemOnclick(FgBaginRightTypeItemLayoutBinding binding, ColorSizeBean bean, int childPosition) {
                if (bean.getStatus() == 0) {
                    //设置规格数据
                    detailsBean.getData().setSpec_color(bean.getName());
                    bean.setStatus(1);
                    for (int i = 0; i < colorData.size(); i++) {
                        if (i != childPosition) {
                            if (colorData.get(i).getStatus() == 1) {
                                colorData.get(i).setStatus(0);
                            }
                        }
                    }
                    //相同颜色
                    List<ProductsdetailsBean.DataBean.SpecArrBean> listBeans = detailsBean.getData().getSpecArr();
                    for (int i = 0; i < listBeans.size(); i++) {
                        ProductsdetailsBean.DataBean.SpecArrBean specArrBean = listBeans.get(i);
                        //判断颜色是否相等
                        if (bean.getName().equals(specArrBean.getSpecColor())) {
                            for (int j = 0; j < sizeData.size(); j++) {
                                //设置可点击状态
                                if (specArrBean.getSpecSize().equals(sizeData.get(j).getName())) {
                                    if (sizeData.get(j).getStatus() != 1) {
                                        sizeData.get(j).setStatus(4);//临时可以选中的值4
                                        LogUtils.d("sizeBeans4:" + sizeData.get(j).getName());
                                    } else {
                                        sizeData.get(j).setStatus(5);//临时已选中的值5
                                        LogUtils.d("sizeBeans5:" + sizeData.get(j).getName());
                                    }
                                }
                            }
                        }
                    }
                    //设置不能选择
                    for (int j = 0; j < sizeData.size(); j++) {
                        if (sizeData.get(j).getStatus() == 4) {
                            sizeData.get(j).setStatus(0);
                        } else if (sizeData.get(j).getStatus() == 5) {
                            sizeData.get(j).setStatus(1);
                        } else {
                            sizeData.get(j).setStatus(2);
                        }
                    }
                    //设置图片和价格
                    if (!TextUtils.isEmpty(detailsBean.getData().getSpec_size()) && !TextUtils.isEmpty(detailsBean.getData().getSpec_color())) {
                        for (int i = 0; i < detailsBean.getData().getSpecArr().size(); i++) {
                            ProductsdetailsBean.DataBean.SpecArrBean specArrBean = detailsBean.getData().getSpecArr().get(i);
                            //判断颜色和规格是否都相等
                            if (detailsBean.getData().getSpec_color().equals(specArrBean.getSpecColor()) && detailsBean.getData().getSpec_size().equals(specArrBean.getSpecSize())) {
                                mSpecArrBean = specArrBean;
                                bindCommonData();
                                break;
                            }
                        }
                    }
                    colorAdapter.notifyDataSetChanged();
                    sizeAdapter.notifyDataSetChanged();
                } else if (bean.getStatus() == 1) {
                    bean.setStatus(0);
                    //设置规格数据
                    detailsBean.getData().setSpec_color("");
                    //设置都可选择
                    for (int j = 0; j < sizeData.size(); j++) {
                        if (sizeData.get(j).getStatus() != 1) {
                            sizeData.get(j).setStatus(0);
                        }
                    }
                    colorAdapter.notifyDataSetChanged();
                    sizeAdapter.notifyDataSetChanged();
                }
            }
        };
        mBinding.gvColor.setAdapter(colorAdapter);
    }

    /**
     * 绑定数据
     */
    private void bindCommonData() {
        //头部数据
        mBinding.tvName.setText(detailsBean.getData().getName());
        mBinding.tvSubName.setText(detailsBean.getData().getSubTitle());
        mBinding.cbCollect.setChecked(detailsBean.getData().isCollect());
        ImageCacheUtils.loadImage(getActivity(), mSpecArrBean.getHeadImage(), mBinding.iv);
        mBinding.tvPrice.setText(PublicUtils.getRenminbi() + mSpecArrBean.getPrice());
        mBinding.tvOriginalPrice.setText(PublicUtils.getRenminbi() + mSpecArrBean.getPriceRiginal());
        //商品信息
        mBinding.gvSpxx.setAdapter(new CommonAdapter<SpxxLayoutBinding, PropertiesBean>(getActivity(), R.layout.spxx_layout, mSpecArrBean.getProperties()) {
            @Override
            protected void getItem(SpxxLayoutBinding binding, PropertiesBean bean, int position) {
                binding.tv.setText(bean.getName() + "︰" + bean.getValue());
            }

            @Override
            protected void itemOnclick(SpxxLayoutBinding binding, PropertiesBean bean, int position) {

            }
        });
    }

    /**
     * POST /app/product/collectProduct
     * 帐号本身或者客户收藏或取消收藏商品商品
     */
    private void collectProduct() {
        if (detailsBean == null)
            return;
        Map<String, String> map = new HashMap<>();
        map.put("productId", String.valueOf(detailsBean.getData().getId()));
        map.put("isCollect", String.valueOf(!detailsBean.getData().isCollect()));
        if (MyApp.getInstance().getCurrentUser() == null){
            map.put("id", String.valueOf(0));
        }else {
            map.put("id", String.valueOf(MyApp.getInstance().getCurrentUser().getId()));
        }
        map.put("dataType", String.valueOf(detailsBean.getData().getDataType()));
        HttpCallback callback = new HttpCallback<ProductcollectProductBean>(getActivity(), this, false) {
            @Override
            protected void onSuccess(ProductcollectProductBean baseBean) {
                if (detailsBean.getData().isCollect()) {
                    ToastUtils.toast(getString(R.string.str_yqxsc));
                } else {
                    ToastUtils.toast(getString(R.string.str_sccg));
                }
                detailsBean.getData().setCollect(!detailsBean.getData().isCollect());
                mBinding.cbCollect.setChecked(detailsBean.getData().isCollect());

                EventBus.getDefault().post(new MessageEvent(MsgConstant.SHOP_SC_REFRESH,detailsBean.getData()));
            }

            @Override
            protected void onFailure(Throwable e) {
            }
        };
        startRequest(HttpBuilder.httpService.productcollectProduct(AppParams.getParams(map)), callback);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.cbCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectProduct();
            }
        });
        mBinding.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRqDialog(HttpBuilder.BASE_URL+HttpBuilder.SHARE_URL+"id="+detailsBean.getData().getId()+"&dataType="+detailsBean.getData().getDataType());
            }
        });

        mBinding.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = mSpecArrBean.getHeadImage();
                List<String> paths = new ArrayList<>();
                paths.add(path);
                imgEnlarge(paths);
            }
        });
    }

    /**
     * *********************************************************************************************
     * 分享成功弹窗
     */
    private CommonDialog dialogShare;
    private RqCodeLayoutBinding rqCodeBinding;

    public void showRqDialog(String image) {
        if (dialogShare == null) {
            dialogShare = new CommonDialog.Builder(getActivity())
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

    public void imgEnlarge(List<String> iamges){
        Bundle bundle = new Bundle();
        bundle.putBoolean(CommonActivity.ISFULLSCREEN, true);
        bundle.putBoolean(CommonActivity.ISSLIDINGCLOSE, false);
        bundle.putStringArrayList(Constants.LSIT, (ArrayList<String>) iamges);
        startFragment(PicturelookFragment.class, bundle);
    }
}
