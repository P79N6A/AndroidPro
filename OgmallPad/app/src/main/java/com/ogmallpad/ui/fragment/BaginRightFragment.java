package com.ogmallpad.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.PublicUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.bean.ColorSizeBean;
import com.ogmallpad.bean.ShopScreenBean;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.BaginRightContract;
import com.ogmallpad.databinding.FgBaginRightFooterLayoutBinding;
import com.ogmallpad.databinding.FgBaginRightHeaderLayoutBinding;
import com.ogmallpad.databinding.FgBaginRightLayoutBinding;
import com.ogmallpad.databinding.FgBaginRightTypeItemLayoutBinding;
import com.ogmallpad.presenter.BaginRightPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.ui.activity.LoginActivity;
import com.shan.netlibrary.bean.BagshareBean;
import com.shan.netlibrary.bean.BrandlistBean;
import com.shan.netlibrary.bean.ProductsearchBean;
import com.shan.netlibrary.bean.RoomTypespecsBean;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拎包入住
 * Created by chenjunshan on 2018-08-21.
 */

public class BaginRightFragment extends BaseFragment<FgBaginRightLayoutBinding, ProductsearchBean.DataBean.ListBean> implements BaginRightContract.View {
    private BaginRightPresenter presenter;
    private FgBaginRightHeaderLayoutBinding headerBinding;
    private FgBaginRightFooterLayoutBinding footerBinding;
    private String styleId;//风格id
    private String brandId;//品牌id
    private String searchText;//搜索内容
    private String minPrice;//最小价格
    private String maxPrice;//最大价格
    private String sortType = "1";//排序方式 1 最热，2 价格，3 最新
    private String order = "0";//排序方案 升序还是降序 0 降序，1升序
    private StylesBean stylesBean;
    private BrandlistBean brandlistBean;

    @Override
    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public void setStyleId(String styleId) {
        this.styleId = styleId;
        onRefresh();
    }

    @Override
    public void setBrandId(String brandId) {
        this.brandId = brandId;
        onRefresh();
    }

    @Override
    public void query() {
        onRefresh();
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_bagin_right_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BaginRightPresenter(getActivity(), this);
        lvBinding.xrecyclerview.setPadding(0, 0, 0, 0);
        addHeaderView();
        addFooterView();
        noDataTitle = getString(R.string.str_hint11);
    }

    /**
     * 增加头布局
     */
    private void addHeaderView() {
        headerBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_bagin_right_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(headerBinding.getRoot());


    }

    /**
     * 增加底部布局
     */
    private void addFooterView() {
        footerBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_bagin_right_footer_layout, lvBinding.llFooter, false);
        lvBinding.llFooter.addView(footerBinding.getRoot());
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        headerBinding.tvFiltrate.setOnClickListener(this);
        footerBinding.btnShare.setOnClickListener(this);
        footerBinding.btnAdd.setOnClickListener(this);
        headerBinding.etSearch.addTextChangedListener(textWatcher);
        headerBinding.tvHot.setOnClickListener(this);
        headerBinding.tvPrice.setOnClickListener(this);
        headerBinding.tvNewest.setOnClickListener(this);
        headerBinding.tvSearch.setOnClickListener(this);
        headerBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    SoftKeyBoardUtils.closeKeybord(v, getActivity());
                    search();
                }
                return false;
            }
        });

        headerBinding.etMaxPrice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    minPrice = headerBinding.etMinPrice.getText().toString();
                    maxPrice = headerBinding.etMaxPrice.getText().toString();
                    pageNum = 1;
                    clearData();
                    getData(false);
                    SoftKeyBoardUtils.closeKeybord(view, getActivity());
                    return true;
                }
                return false;
            }
        });
    }

    private boolean isFirst = true;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (isFirst) {
                isFirst = false;
            } else {
                if (TextUtils.isEmpty(editable.toString())) {
                    searchText = editable.toString();
                    onRefresh(true);
                }
            }
        }
    };

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_filtrate:
                List<String> list3 = new ArrayList<>();
                for (int i = 0; i < 14; i++) {
                    list3.add("");
                }
                presenter.showSxDialog(stylesBean, brandlistBean);
                break;
            case R.id.btn_share:
                presenter.showRqDialog();
                break;
            case R.id.btn_add:
                //加入购物车
                if (MyApp.getInstance().isLogin()) {
                    presenter.addShop();
                } else {
                    startActivity(LoginActivity.class, null);
                }
                break;
            case R.id.tv_hot:
                tab1();
                onRefresh();
                headerBinding.tvNewest.setChecked(false);
                headerBinding.tvPrice.setChecked(false);
                headerBinding.tvHot.setChecked(true);
                break;
            case R.id.tv_price:
                tab2();
                onRefresh();
                headerBinding.tvNewest.setChecked(false);
                headerBinding.tvPrice.setChecked(true);
                headerBinding.tvHot.setChecked(false);
                break;
            case R.id.tv_newest:
                tab3();
                onRefresh();
                headerBinding.tvNewest.setChecked(true);
                headerBinding.tvPrice.setChecked(false);
                headerBinding.tvHot.setChecked(false);
                break;
            case R.id.tv_search:
                search();
                break;
        }
    }
    private void tab1() {
        sortType = "1";//排序方式 1 最热，2 价格，3 最新
        order = "0";//排序方案 升序还是降序 0 降序，1升序
        headerBinding.tvHot.setTextColor(getResources().getColor(R.color.color_c73c3a));
        headerBinding.tvPrice.setTextColor(getResources().getColor(R.color.color_333333));
        headerBinding.tvNewest.setTextColor(getResources().getColor(R.color.color_333333));
        headerBinding.ivPrice.setImageResource(R.mipmap.ic_price_defualt);
    }

    private void tab2() {
        sortType = "2";//排序方式 1 最热，2 价格，3 最新
        if (order.equals("0")) {
            order = "1";
            headerBinding.ivPrice.setImageResource(R.mipmap.ic_price_fall);
        } else {
            order = "0";
            headerBinding.ivPrice.setImageResource(R.mipmap.ic_price_litre);
        }
        headerBinding.tvHot.setTextColor(getResources().getColor(R.color.color_333333));
        headerBinding.tvPrice.setTextColor(getResources().getColor(R.color.color_c73c3a));
        headerBinding.tvNewest.setTextColor(getResources().getColor(R.color.color_333333));
    }

    private void tab3() {
        sortType = "3";//排序方式 1 最热，2 价格，3 最新
        order = "0";//排序方案 升序还是降序 0 降序，1升序
        headerBinding.tvHot.setTextColor(getResources().getColor(R.color.color_333333));
        headerBinding.tvPrice.setTextColor(getResources().getColor(R.color.color_333333));
        headerBinding.tvNewest.setTextColor(getResources().getColor(R.color.color_c73c3a));
        headerBinding.ivPrice.setImageResource(R.mipmap.ic_price_defualt);
    }

    /**
     * 搜索
     */
    private void search() {
        searchText = headerBinding.etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(searchText)) {
            ToastUtils.toast(getString(R.string.str_hint7));
            return;
        }
        onRefresh(false);
    }

    @Override
    protected void initData() {
        super.initData();
        lvBinding.llHeader.setVisibility(View.GONE);
        lvBinding.llFooter.setVisibility(View.GONE);
        List<ProductsearchBean.DataBean.ListBean> data = new ArrayList<>();
        setData(data);
    }

    @Override
    protected void getListVewItem(FgBaginRightLayoutBinding binding, final ProductsearchBean.DataBean.ListBean item, final int position) {
        super.getListVewItem(binding, item, position);
        //标题
        binding.tvTitle.setText(item.getName());
        ImageCacheUtils.loadImage(getActivity(), item.getImage(), binding.ivHead);
        binding.tvPrice.setText(PublicUtils.getRenminbi() + item.getPrice());
        //获取颜色规格数据
        final List<ProductsearchBean.DataBean.ListBean.SpecArrBean> specArrs = item.getSpecArr();
        //大小
        if (position < sizeDatas.size())
        binding.gvGg.setAdapter(new CommonAdapter<FgBaginRightTypeItemLayoutBinding, ColorSizeBean>(getActivity(), R.layout.fg_bagin_right_type_item_layout, sizeDatas.get(position)) {
            @Override
            protected void getItem(FgBaginRightTypeItemLayoutBinding binding, ColorSizeBean bean, int position) {
                binding.tvName.setText(bean.getName());
                LogUtils.d("getItemGg:" + bean.getName());
                if (bean.getStatus() == 0) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_dash_grap_bgin);
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
                    item.setSpec_size(bean.getName());
                    bean.setStatus(1);
                    List<ColorSizeBean> sizeBeans = sizeDatas.get(position);
                    for (int i = 0; i < sizeBeans.size(); i++) {
                        if (i != childPosition) {
                            if (sizeBeans.get(i).getStatus() == 1) {
                                sizeBeans.get(i).setStatus(0);
                            }
                        }
                    }
                    //相同规格
                    List<ColorSizeBean> colorBeans = colorDatas.get(position);
                    for (int i = 0; i < specArrs.size(); i++) {
                        ProductsearchBean.DataBean.ListBean.SpecArrBean specArrBean = specArrs.get(i);
                        //判断规格是否相等
                        if (bean.getName().equals(specArrBean.getSpec_size())) {
                            for (int j = 0; j < colorBeans.size(); j++) {
                                //设置可点击状态
                                if (specArrBean.getSpec_color().equals(colorBeans.get(j).getName())) {
                                    if (colorBeans.get(j).getStatus() != 1 && colorBeans.get(j).getStatus() != 5) {
                                        colorBeans.get(j).setStatus(4);//临时可以选中的值4
                                    } else {
                                        colorBeans.get(j).setStatus(5);//临时已选中的值5
                                    }
                                }
                            }
                        }
                    }
                    //设置不能选择
                    for (int j = 0; j < colorBeans.size(); j++) {
                        if (colorBeans.get(j).getStatus() == 4) {
                            colorBeans.get(j).setStatus(0);
                        } else if (colorBeans.get(j).getStatus() == 5) {
                            colorBeans.get(j).setStatus(1);
                        } else {
                            colorBeans.get(j).setStatus(2);
                        }
                    }
                    //设置图片和价格
                    if (!TextUtils.isEmpty(item.getSpec_color())) {
                        for (int i = 0; i < specArrs.size(); i++) {
                            ProductsearchBean.DataBean.ListBean.SpecArrBean specArrBean = specArrs.get(i);
                            //判断颜色和规格是否都相等
                            if (item.getSpec_color().equals(specArrBean.getSpec_color()) && item.getSpec_size().equals(specArrBean.getSpec_size())) {
                                item.setImage(specArrBean.getHeadImage());
                                item.setPrice(specArrBean.getPrice());
                                item.setSpec_id(specArrBean.getSpec_id());
                                break;
                            }
                        }
                    }
                    LogUtils.d("sizeBeans:" + bean.getStatus());
                    adapter.notifyDataSetChanged();
                } else if (bean.getStatus() == 1) {
                    bean.setStatus(0);
                    //设置规格数据
                    item.setSpec_size("");
                    //设置都可选择
                    List<ColorSizeBean> colorBeans = colorDatas.get(position);
                    for (int j = 0; j < colorBeans.size(); j++) {
                        if (colorBeans.get(j).getStatus() != 1) {
                            colorBeans.get(j).setStatus(0);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

//        setColorData(binding,colorDatas.get(position),item);
        //颜色
        if (position < colorDatas.size())
        binding.gvColor.setAdapter(new CommonAdapter<FgBaginRightTypeItemLayoutBinding, ColorSizeBean>(getActivity(), R.layout.fg_bagin_right_type_item_layout, colorDatas.get(position)) {
            @Override
            protected void getItem(FgBaginRightTypeItemLayoutBinding binding, ColorSizeBean bean, int position) {
                binding.tvName.setText(bean.getName());
                LogUtils.d("getItemColor:" + bean.getName());
                if (bean.getStatus() == 0) {
                    binding.tvName.setBackgroundResource(R.drawable.shape_white_black_bgin);
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
                    item.setSpec_color(bean.getName());
                    bean.setStatus(1);
                    List<ColorSizeBean> colorBeans = colorDatas.get(position);
                    for (int i = 0; i < colorBeans.size(); i++) {
                        if (i != childPosition) {
                            if (colorBeans.get(i).getStatus() == 1) {
                                colorBeans.get(i).setStatus(0);
                            }
                        }
                    }
                    //相同颜色
                    List<ColorSizeBean> sizeBeans = sizeDatas.get(position);
                    for (int i = 0; i < specArrs.size(); i++) {
                        ProductsearchBean.DataBean.ListBean.SpecArrBean specArrBean = specArrs.get(i);
                        //判断颜色是否相等
                        if (bean.getName().equals(specArrBean.getSpec_color())) {
                            for (int j = 0; j < sizeBeans.size(); j++) {
                                //设置可点击状态
                                if (specArrBean.getSpec_size().equals(sizeBeans.get(j).getName())) {
                                    if (sizeBeans.get(j).getStatus() != 1) {
                                        sizeBeans.get(j).setStatus(4);//临时可以选中的值4
                                        LogUtils.d("sizeBeans4:" + sizeBeans.get(j).getName());
                                    } else {
                                        sizeBeans.get(j).setStatus(5);//临时已选中的值5
                                        LogUtils.d("sizeBeans5:" + sizeBeans.get(j).getName());
                                    }
                                }
                            }
                        }
                    }
                    //设置不能选择
                    for (int j = 0; j < sizeBeans.size(); j++) {
                        if (sizeBeans.get(j).getStatus() == 4) {
                            sizeBeans.get(j).setStatus(0);
                        } else if (sizeBeans.get(j).getStatus() == 5) {
                            sizeBeans.get(j).setStatus(1);
                        } else {
                            sizeBeans.get(j).setStatus(2);
                        }
                    }
                    //设置图片和价格
                    if (!TextUtils.isEmpty(item.getSpec_size())) {
                        for (int i = 0; i < specArrs.size(); i++) {
                            ProductsearchBean.DataBean.ListBean.SpecArrBean specArrBean = specArrs.get(i);
                            //判断颜色和规格是否都相等
                            if (item.getSpec_color().equals(specArrBean.getSpec_color()) && item.getSpec_size().equals(specArrBean.getSpec_size())) {
                                item.setImage(specArrBean.getHeadImage());
                                item.setPrice(specArrBean.getPrice());
                                item.setSpec_id(specArrBean.getSpec_id());
                                break;
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else if (bean.getStatus() == 1) {
                    bean.setStatus(0);
                    //设置规格数据
                    item.setSpec_color("");
                    //设置都可选择
                    List<ColorSizeBean> sizeBeans = sizeDatas.get(position);
                    for (int j = 0; j < sizeBeans.size(); j++) {
                        if (sizeBeans.get(j).getStatus() != 1) {
                            sizeBeans.get(j).setStatus(0);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAdd = false;
                for (int i = 0; i < specArrs.size(); i++) {
                    ProductsearchBean.DataBean.ListBean.SpecArrBean specArrBean = specArrs.get(i);
                    //判断颜色是否相等
                    if (item.getSpec_color().equals(specArrBean.getSpec_color()) && item.getSpec_size().equals(specArrBean.getSpec_size())) {
                        RoomTypespecsBean.DataBean.CategoryListBean.ProductBean productBean = new RoomTypespecsBean.DataBean.CategoryListBean.ProductBean();
                        productBean.setCount(1);
                        productBean.setImage(item.getImage());
                        productBean.setPrice(item.getPrice());
                        productBean.setSpec_color(item.getSpec_color());
                        productBean.setSpec_size(item.getSpec_size());
                        productBean.setSpec_id(item.getSpec_id());
                        productBean.setName(item.getName());
                        productBean.setProductId(item.getId());
                        //TODO 直接调用左边布局  不能采用EventBus 因为EventBus 在这种情况下会存在问题
                        if(BaginFragment.leftFragment != null)
                            BaginFragment.leftFragment.onMsgEvent(new MessageEvent(MsgConstant.LAYOUTDATA, productBean));
//                        EventBus.getDefault().post(new MessageEvent(MsgConstant.LAYOUTDATA, productBean));
                        isAdd = true;
                        break;
                    }
                }
                if (!isAdd) {
                    ToastUtils.toast(getString(R.string.str_hint10));
                }
            }
        });
        binding.ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.lookImage(item.getImage());
            }
        });
    }

//    private void setColorData(FgBaginRightLayoutBinding binding, List<ColorSizeBean> colorSizeBeans, final ProductsearchBean.DataBean.ListBean item) {
//        binding.gvColor.removeAllViews();
//        for (int i = 0 ; i < colorSizeBeans.size() ; i++){
//            final ColorSizeBean bean = colorSizeBeans.get(i);
//            FgBaginRightTypeItemLayoutBinding viewBind = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.fg_bagin_right_type_item_layout,binding.gvColor,false);
//            binding.gvColor.addView(viewBind.getRoot());
//            viewBind.tvName.setText(colorSizeBeans.get(i).getName());
//            if (bean.getStatus() == 0) {
//                viewBind.tvName.setBackgroundResource(R.drawable.shape_white_black_bgin);
//                viewBind.tvName.setTextColor(getResources().getColor(R.color.grgray));
//            } else if (bean.getStatus() == 1) {
//                viewBind.tvName.setBackgroundResource(R.drawable.shape_red4);
//                viewBind.tvName.setTextColor(getResources().getColor(R.color.black));
//            } else if (bean.getStatus() == 2) {
//                viewBind.tvName.setBackgroundResource(R.drawable.shape_dash_grap_hide);
//                viewBind.tvName.setTextColor(Color.parseColor("#dddddd"));
//            }
//            final int finalI = i;
//            viewBind.tvName.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (bean.getStatus() == 0) {
//                    //设置规格数据
//                    item.setSpec_color(bean.getName());
//                    bean.setStatus(1);
//                    List<ColorSizeBean> colorBeans = colorDatas.get(finalI);
//                    for (int j = 0; j < colorBeans.size(); j++) {
//                        if (j != finalI) {
//                            if (colorBeans.get(j).getStatus() == 1) {
//                                colorBeans.get(j).setStatus(0);
//                            }
//                        }
//                    }
//                    //相同颜色
//                    List<ColorSizeBean> sizeBeans = sizeDatas.get(finalI);
//                    for (int i = 0; i < specArrs.size(); i++) {
//                        ProductsearchBean.DataBean.ListBean.SpecArrBean specArrBean = specArrs.get(i);
//                        //判断颜色是否相等
//                        if (bean.getName().equals(specArrBean.getSpec_color())) {
//                            for (int j = 0; j < sizeBeans.size(); j++) {
//                                //设置可点击状态
//                                if (specArrBean.getSpec_size().equals(sizeBeans.get(j).getName())) {
//                                    if (sizeBeans.get(j).getStatus() != 1) {
//                                        sizeBeans.get(j).setStatus(4);//临时可以选中的值4
//                                        LogUtils.d("sizeBeans4:" + sizeBeans.get(j).getName());
//                                    } else {
//                                        sizeBeans.get(j).setStatus(5);//临时已选中的值5
//                                        LogUtils.d("sizeBeans5:" + sizeBeans.get(j).getName());
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    //设置不能选择
//                    for (int j = 0; j < sizeBeans.size(); j++) {
//                        if (sizeBeans.get(j).getStatus() == 4) {
//                            sizeBeans.get(j).setStatus(0);
//                        } else if (sizeBeans.get(j).getStatus() == 5) {
//                            sizeBeans.get(j).setStatus(1);
//                        } else {
//                            sizeBeans.get(j).setStatus(2);
//                        }
//                    }
//                    //设置图片和价格
//                    if (!TextUtils.isEmpty(item.getSpec_size())) {
//                        for (int i = 0; i < specArrs.size(); i++) {
//                            ProductsearchBean.DataBean.ListBean.SpecArrBean specArrBean = specArrs.get(i);
//                            //判断颜色和规格是否都相等
//                            if (item.getSpec_color().equals(specArrBean.getSpec_color()) && item.getSpec_size().equals(specArrBean.getSpec_size())) {
//                                item.setImage(specArrBean.getHeadImage());
//                                item.setPrice(specArrBean.getPrice());
//                                item.setSpec_id(specArrBean.getSpec_id());
//                                break;
//                            }
//                        }
//                    }
//                    adapter.notifyDataSetChanged();
//                } else if (bean.getStatus() == 1) {
//                    bean.setStatus(0);
//                    //设置规格数据
//                    item.setSpec_color("");
//                    //设置都可选择
//                    List<ColorSizeBean> sizeBeans = sizeDatas.get(finalI);
//                    for (int j = 0; j < sizeBeans.size(); j++) {
//                        if (sizeBeans.get(j).getStatus() != 1) {
//                            sizeBeans.get(j).setStatus(0);
//                        }
//                    }
//                    adapter.notifyDataSetChanged();
//                }
//
//                }
//            });
//
//        }
//    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.CATEGORYID) {
            //恢复初始值
            tab1();
            isFirst = true;
            searchText = "";
            headerBinding.etSearch.setText("");
            //产品搜索id
            categoryId = msgEvent.getMsg();
            if (!TextUtils.isEmpty(categoryId)) {
                onRefresh();
                styleId = null;
                brandId = null;
                minPrice = null;
                maxPrice = null;
                //获取风格
                Map<String, String> map = new HashMap<>();
                map.put("categotyId", categoryId);
                presenter.styles(map);
                //获取品牌
                Map<String, String> map2 = new HashMap<>();
                map2.put("pageNo", "1");
                map2.put("pageSize", "5000");
                map2.put("categoryId", categoryId);
                presenter.brandlist(map2, false);
                presenter.refreshFiltrate();
            } else {
                categoryId = "";
                lvBinding.xrecyclerview.setLoadingMoreEnabled(true);
                lvBinding.xrecyclerview.scrollToPosition(0);
                pageNum = 1;
                searchBean = new ProductsearchBean();
                handleData();
                setData(searchBean.getData().getList());
            }
        } else if (msgEvent.getType() == MsgConstant.REFRESHALLNUMDATA) {
            //计算商品总价
            refreshData();
        } else if (msgEvent.getType() == MsgConstant.REFRESHALLDATA) {
            initData();
            //计算商品总价
            refreshData();
        }
    }

    private boolean isSetEmptyMargin = true;

    /**
     * 设置空页面的边距
     */
    private void setEmptyMargin() {
        if (isSetEmptyMargin) {
            isSetEmptyMargin = false;
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) lvBinding.llEmpty.getLayoutParams();
            params.leftMargin = 60;
            params.rightMargin = 40;
            lvBinding.llEmpty.setLayoutParams(params);
        }
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        double allMoney = 0;
        double allCount = 0;
        List<RoomTypespecsBean.DataBean> datas = BaginLeftFragment.roomTypespecsBean.getData();
        for (int k = 0; k < datas.size(); k++) {
            RoomTypespecsBean.DataBean dataBean = datas.get(k);
            List<RoomTypespecsBean.DataBean.CategoryListBean> categoryListBeans = dataBean.getCategoryList();
            for (int i = 0; i < categoryListBeans.size(); i++) {
                List<RoomTypespecsBean.DataBean.CategoryListBean.ProductBean> products = categoryListBeans.get(i).getProduct();
                for (int j = 0; j < products.size(); j++) {
                    allMoney += products.get(j).getCount() * Double.parseDouble(products.get(j).getPrice());
                    allCount += products.get(j).getCount();
                }
            }
        }
        if (allCount == 0) {
//            footerBinding.tvNumber.setVisibility(View.GONE);
//            footerBinding.btnAdd.setVisibility(View.GONE);
//            footerBinding.btnShare.setVisibility(View.GONE);

            footerBinding.tvNumber.setText("全部房间共   ￥0.00");
        } else {
//            footerBinding.tvNumber.setVisibility(View.VISIBLE);
            footerBinding.btnAdd.setVisibility(View.VISIBLE);
            footerBinding.btnShare.setVisibility(View.VISIBLE);
            String money = new DecimalFormat("######0.00").format(allMoney);
            footerBinding.tvNumber.setText("全部房间共   ￥" + money);
        }
    }

    private List<List<ColorSizeBean>> sizeDatas = null;
    private List<List<ColorSizeBean>> colorDatas = null;

    /**
     * 处理数据
     */
    private void handleData() {
        if (pageNum == 1) {
            colorDatas = new ArrayList<>();
            sizeDatas = new ArrayList<>();
        }
        if (searchBean != null) {
            List<ProductsearchBean.DataBean.ListBean> listBeans = searchBean.getData().getList();
            for (int k = 0; k < listBeans.size(); k++) {
                List<ProductsearchBean.DataBean.ListBean.SpecArrBean> specArrs = listBeans.get(k).getSpecArr();
                List<ColorSizeBean> sizeData = new ArrayList<>();
                List<ColorSizeBean> colorData = new ArrayList<>();
                //绑定颜色规格数据
                for (int i = 0; i < specArrs.size(); i++) {
                    String size = specArrs.get(i).getSpec_size();
                    String color = specArrs.get(i).getSpec_color();
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
                sizeDatas.add(sizeData);
                colorDatas.add(colorData);
            }
        }
    }

    private String categoryId;

    /**
     * 获取数据
     */
    private void getData(boolean isShow) {
        Map<String, String> map = new HashMap<>();
        map.put("categoryId", categoryId);//所属分类id
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        if (styleId != null && Integer.parseInt(styleId) != 0)
            map.put("styleId", styleId);//风格id
        if (brandId != null && Integer.parseInt(brandId) != 0)
            map.put("brandId", brandId);//品牌id
        if (minPrice != null)
            map.put("minPrice", minPrice);//最小价格
        if (maxPrice != null && Integer.parseInt(maxPrice) != 0)
            map.put("maxPrice", maxPrice);//最大价格
        map.put("sortType", sortType);//排序方式 1 最热，2 价格，3 最新
        map.put("order", order);//排序方案 升序还是降序 0 降序，1升序
        map.put("searchWord", searchText);//搜索关键字
        presenter.productsearch(map, isShow);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getData(false);
    }

    @Override
    public void onRefresh(boolean isShow) {
        super.onRefresh(isShow);
        getData(isShow);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            getData(true);
        }
    }

    private ProductsearchBean searchBean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSEARCH) {
            //产品搜索
            if (lvBinding.llHeader.getVisibility() == View.GONE) {
                lvBinding.llFooter.setVisibility(View.VISIBLE);
                lvBinding.llHeader.setVisibility(View.VISIBLE);
            }
            searchBean = (ProductsearchBean) baseBean;
            handleData();
            isLoadingMore(searchBean.getData().getPageResult().getTotalPage());
            if (pageNum == 1)
                clearData();
            addData(searchBean.getData().getList());
            setEmptyMargin();
        } else if (mTag == HttpPresenter.CARTSAVE) {
            //ToastUtils.toast("加入购物车成功！");
            presenter.showShop();
        } else if (mTag == HttpPresenter.BAGSHARE) {
            BagshareBean bean = (BagshareBean) baseBean;
            presenter.setRqCode(bean.getData().getUrl());
        } else if (mTag == HttpPresenter.STYLES) {
            stylesBean = (StylesBean) baseBean;
        } else if (mTag == HttpPresenter.BRANDLIST) {
            brandlistBean = (BrandlistBean) baseBean;
        }else if (mTag == HttpPresenter.CENTERSAVECOLLECTS){
            ToastUtils.toast("收藏成功");
        }
    }
    @Override
    public void query(ShopScreenBean bean){
        pageNum = 1;
        clearData();
        Map<String, String> map = new HashMap<>();
        map.put("categoryId", categoryId);//所属分类id
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        styleId = String.valueOf(bean.getFgData());
        if (bean.getFgData() != 0) {
            map.put("styleId", String.valueOf(bean.getFgData()));//风格id
        }
        brandId = String.valueOf(bean.getPpData());
        if (bean.getPpData() != 0){
            map.put("brandId", String.valueOf(bean.getPpData()));//品牌id
        }

        minPrice = String.valueOf(bean.getMinPrice());
        map.put("minPrice", String.valueOf(bean.getMinPrice()));//最小价格
        maxPrice = String.valueOf(bean.getMaxPrice());
        if (bean.getMaxPrice() != 0) {
            map.put("maxPrice", String.valueOf(bean.getMaxPrice()));//最大价格
        }
        map.put("sortType", sortType);//排序方式 1 最热，2 价格，3 最新
        map.put("order", order);//排序方案 升序还是降序 0 降序，1升序
        presenter.productsearch(map, false);
    }

}
