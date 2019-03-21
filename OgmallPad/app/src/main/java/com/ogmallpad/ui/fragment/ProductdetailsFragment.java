package com.ogmallpad.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.PublicUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.R;
import com.ogmallpad.bean.ColorSizeBean;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.ProductdetailsContract;
import com.ogmallpad.databinding.FgBaginRightTypeItemLayoutBinding;
import com.ogmallpad.databinding.FgProductdetailsLayoutBinding;
import com.ogmallpad.databinding.ProductImageItemLayoutBinding;
import com.ogmallpad.databinding.ProductTextItemLayoutBinding;
import com.ogmallpad.presenter.ProductdetailsPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.ProductsdetailsBean;
import com.shan.netlibrary.bean.ProductsdetailsBean.DataBean.SpecArrBean.PropertiesBean;
import com.shan.netlibrary.bean.ProductsshareBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情
 * Created by chenjunshan on 2018-10-10.
 */

public class ProductdetailsFragment extends BaseFragment<FgProductdetailsLayoutBinding, Object> implements ProductdetailsContract.View {
    private ProductdetailsPresenter presenter;
    private String productId;

    @Override
    public int bindLayout() {
        return R.layout.fg_productdetails_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.VISIBLE);
        titleBinding.tvLeft.setText(getString(R.string.str_spxq));
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ProductdetailsPresenter(getActivity(), this);
        mBinding.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中间横线
        mBinding.tvOldPrice.getPaint().setAntiAlias(true);//抗锯齿
        productId = getActivity().getIntent().getStringExtra(Constants.ID);
        getData();
    }

    /**
     * 获取商品详情数据
     */
    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);
        presenter.productsdetails(map);
    }

    private ProductsdetailsBean detailsBean;
    private ProductsdetailsBean.DataBean.SpecArrBean mSpecArrBean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSDETAILS) {
            //商品详情
            detailsBean = (ProductsdetailsBean) baseBean;
            if (detailsBean == null)
                detailsBean = new ProductsdetailsBean();
            if (detailsBean.getData().getSpecArr().size() > 0) {
                mSpecArrBean = detailsBean.getData().getSpecArr().get(0);
            } else {
                mSpecArrBean = new ProductsdetailsBean.DataBean.SpecArrBean();
            }
            handleData();
            bindData();
        } else if (mTag == HttpPresenter.PRODUCTSSHARE) {
            //分享成功
            ProductsshareBean productsshareBean = (ProductsshareBean) baseBean;
            presenter.showRqDialog(productsshareBean.getData().getUrl());
        } else if (mTag == HttpPresenter.CENTERSAVECOLLECTS) {
            //收藏成功
            detailsBean.getData().setCollect(!detailsBean.getData().isCollect());
            if (detailsBean.getData().isCollect()) {
                ToastUtils.toast(getString(R.string.str_sccg));
                mBinding.rbCollect.setImageResource(R.mipmap.ic_collect2_off);
//                mBinding.rbCollect.setText(getString(R.string.str_qxsc2));
            } else {
                ToastUtils.toast(getString(R.string.str_qxsccg));
                mBinding.rbCollect.setImageResource(R.mipmap.ic_collect2_on);
//                mBinding.rbCollect.setText(getString(R.string.str_sc2));
            }
//            mBinding.rbCollect.setChecked(detailsBean.getData().isCollect());
            EventBus.getDefault().post(new MessageEvent(MsgConstant.COLLECT_SHOP,detailsBean.getData()));
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {
        if (mTag == HttpPresenter.CENTERSAVECOLLECTS) {
            //收藏失败
//            mBinding.rbCollect.setChecked(!mBinding.rbCollect.isChecked());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.rbShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<>();
                map.put("productId", productId);
                presenter.productsshare(map);
            }
        });
        mBinding.rbCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<>();
                map.put("id", productId);
                map.put("isCollect", String.valueOf(!detailsBean.getData().isCollect()));
                presenter.centersavecollects(map);
            }
        });
        mBinding.ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSpecArrBean == null)
                    return;
                presenter.lookImage(mSpecArrBean.getHeadImage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

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
                    binding.tvName.setTextColor(getResources().getColor(R.color.black));
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
                        if (bean.getName().equals(specArrBean.getSpec_size())) {
                            for (int j = 0; j < colorData.size(); j++) {
                                //设置可点击状态
                                if (specArrBean.getSpec_color().equals(colorData.get(j).getName())) {
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
                            if (detailsBean.getData().getSpec_color().equals(specArrBean.getSpec_color()) && detailsBean.getData().getSpec_size().equals(specArrBean.getSpec_size())) {
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
        mBinding.gvGg.setAdapter(sizeAdapter);

        //颜色
        colorAdapter = new CommonAdapter<FgBaginRightTypeItemLayoutBinding, ColorSizeBean>(getActivity(), R.layout.fg_bagin_right_type_item_layout, colorData) {
            @Override
            protected void getItem(FgBaginRightTypeItemLayoutBinding binding, ColorSizeBean bean, int position) {
                binding.tvName.setText(bean.getName());
                LogUtils.d("getItemColor:" + bean.getName());
                if (bean.getStatus() == 0) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_white_black);
                    binding.tvName.setTextColor(getResources().getColor(R.color.grgray));
                } else if (bean.getStatus() == 1) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_red4);
                    binding.tvName.setTextColor(getResources().getColor(R.color.black));
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
                        if (bean.getName().equals(specArrBean.getSpec_color())) {
                            for (int j = 0; j < sizeData.size(); j++) {
                                //设置可点击状态
                                if (specArrBean.getSpec_size().equals(sizeData.get(j).getName())) {
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
                            if (detailsBean.getData().getSpec_color().equals(specArrBean.getSpec_color()) && detailsBean.getData().getSpec_size().equals(specArrBean.getSpec_size())) {
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

        //收藏
        if (detailsBean.getData().isCollect()){
            mBinding.rbCollect.setImageResource(R.mipmap.ic_collect2_off);
        }else {
            mBinding.rbCollect.setImageResource(R.mipmap.ic_collect2_on);
        }
    }

    private List<ColorSizeBean> sizeData;
    private List<ColorSizeBean> colorData;

    /**
     * 处理数据
     */
    private void handleData() {
        sizeData = new ArrayList<>();
        colorData = new ArrayList<>();

        if (detailsBean != null) {
            List<ProductsdetailsBean.DataBean.SpecArrBean> listBeans = detailsBean.getData().getSpecArr();
            //绑定颜色规格数据
            for (int i = 0; i < listBeans.size(); i++) {
                String size = listBeans.get(i).getSpec_size();
                String color = listBeans.get(i).getSpec_color();
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

    private void bindCommonData() {
        //头部数据
        mBinding.tvName.setText(detailsBean.getData().getName());
        mBinding.tvName2.setText(detailsBean.getData().getSubTitle());
//        mBinding.rbCollect.setChecked(detailsBean.getData().isCollect());
        ImageCacheUtils.loadImage(getActivity(), mSpecArrBean.getHeadImage(), mBinding.ivHead);
        mBinding.tvPrice.setText(PublicUtils.getRenminbi() + mSpecArrBean.getPrice());
        mBinding.tvOldPrice.setText(PublicUtils.getRenminbi() + mSpecArrBean.getPriceRiginal());
        //商品信息
        mBinding.gvProductInfo.setAdapter(new CommonAdapter<ProductTextItemLayoutBinding, PropertiesBean>(getActivity(), R.layout.product_text_item_layout, mSpecArrBean.getProperties()) {
            @Override
            protected void getItem(ProductTextItemLayoutBinding binding, PropertiesBean bean, int position) {
                binding.text.setText(bean.getName() + "︰" + bean.getValue());
            }

            @Override
            protected void itemOnclick(ProductTextItemLayoutBinding binding, PropertiesBean bean, int position) {

            }
        });

        //商品详情
        String images = detailsBean.getData().getImages();
        if (TextUtils.isEmpty(images)) {
            mBinding.llSpxq.setVisibility(View.GONE);
            mBinding.ssl.setVisibility(View.GONE);
            mBinding.lvProductInfo.setVisibility(View.GONE);
        } else {
            mBinding.llSpxq.setVisibility(View.VISIBLE);
            mBinding.ssl.setVisibility(View.VISIBLE);
            mBinding.lvProductInfo.setVisibility(View.VISIBLE);
            if (images.contains(",")) {
                String[] datas = images.split(",");
                for (int i = 0; i < datas.length; i++) {
                    ProductImageItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.product_image_item_layout, mBinding.lvProductInfo, false);
                    mBinding.lvProductInfo.addView(binding.getRoot());
                    ImageCacheUtils.loadImage(getActivity(), datas[i], binding.image);
                }
            } else {
                ProductImageItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.product_image_item_layout, mBinding.lvProductInfo, false);
                mBinding.lvProductInfo.addView(binding.getRoot());
                ImageCacheUtils.loadImage(getActivity(), images, binding.image);
            }
        }
    }

}
