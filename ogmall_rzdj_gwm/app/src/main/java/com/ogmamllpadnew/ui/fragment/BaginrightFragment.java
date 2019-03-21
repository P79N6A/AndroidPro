package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.config.BaseMsgConstant;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.PublicUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.ColorSizeBean;
import com.ogmamllpadnew.bean.ShopScreenBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.BaginrightContract;
import com.ogmamllpadnew.databinding.FgBaginRightTypeItemLayoutBinding;
import com.ogmamllpadnew.databinding.FgBaginrightHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.FgBaginrightItemLayoutBinding;
import com.ogmamllpadnew.presenter.BaginrightPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.BrandselectBrandByCategory1Bean;
import com.shan.netlibrary.bean.BrandselectBrandByCategoryAndOldHouseStyleBean;
import com.shan.netlibrary.bean.BrandselectOldHouseStyleByCategoryBean;
import com.shan.netlibrary.bean.HandbagselectLayoutBean;
import com.shan.netlibrary.bean.ProductselectProductByCategoryBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拎包入住右边
 * Created by 陈俊山 on 2018-11-10.
 */

public class BaginrightFragment extends BaseFragment<FgBaginrightItemLayoutBinding, ProductselectProductByCategoryBean.DataBean> implements BaginrightContract.View {
    private BaginrightPresenter presenter;
    private int category23;
    private BrandselectBrandByCategory1Bean brandListBean;//屏牌列表
    private BrandselectOldHouseStyleByCategoryBean hourse;  //风格列表
    private ProductselectProductByCategoryBean productListbBean;//商品列表
    private String sortType = "0";//排序方式（0=最火排序，1=最新排序，2=价格升序，3=价格降序）
    private String minPrice;//最低价格
    private String maxPrice;//最高价格
    private String brandId;//品牌ID
    private String hourseStyleId;
    private List<BrandselectOldHouseStyleByCategoryBean.DataBean> fgData;
    private List<BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean> ppData;

    @Override
    public void setBrandId(String brandId) {
        this.brandId = brandId;
        onRefresh();
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_baginright_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BaginrightPresenter(getActivity(), this);
        addHeadView();
        lvBinding.llEmpty.setVisibility(View.VISIBLE);
        lvBinding.llHeader.setVisibility(View.GONE);
    }

    private FgBaginrightHeaderLayoutBinding headerLayoutBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        headerLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_baginright_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(headerLayoutBinding.getRoot());
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSELECTPRODUCTBYCATEGORY) {
            lvBinding.llEmpty.setVisibility(View.GONE);
            lvBinding.llHeader.setVisibility(View.VISIBLE);
            //商品列表
            productListbBean = (ProductselectProductByCategoryBean) baseBean;
            handleData();
            addData(productListbBean.getData());
            isLoadingMore(productListbBean.getCount());
            presenter.dismissDialog();
        } else if (mTag == HttpPresenter.BRANDSELECTBRANDBYCATEGORY1) {
            //品牌列表
            brandListBean = (BrandselectBrandByCategory1Bean) baseBean;
        }else if (mTag == HttpPresenter.BRANDSELECTOLDHOUSESTYLEBYCATEGORY){   //风格
            BrandselectOldHouseStyleByCategoryBean bean = (BrandselectOldHouseStyleByCategoryBean) baseBean;
            if (fgData == null){
                fgData = new ArrayList<>();
            }else {
                fgData.clear();
            }
            fgData.addAll(bean.getData());
            if (presenter != null && presenter.dialogSelect != null && presenter.dialogSelect.isShowing()) {
                presenter.bindfgData(fgData);
            }


        }else if (mTag == HttpPresenter.BRANDSELECTBRANDBYCATEGORYANDOLDHOUSESTYLE){ //品牌
            BrandselectBrandByCategoryAndOldHouseStyleBean bean = (BrandselectBrandByCategoryAndOldHouseStyleBean) baseBean;
            Log.e("mTag",bean.getData().toString());
            if(ppData == null){
                ppData = new ArrayList<>();
            }else {
                ppData.clear();
            }
            ppData.addAll(bean.getData());
            if (presenter != null && presenter.dialogSelect != null && presenter.dialogSelect.isShowing()) {
                presenter.bindPgData(ppData);
            }
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgBaginrightItemLayoutBinding binding, final ProductselectProductByCategoryBean.DataBean item, final int position) {
        super.getListVewItem(binding, item, position);
        binding.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中间横线
        binding.tvOriginalPrice.getPaint().setAntiAlias(true);//抗锯齿

        ImageCacheUtils.loadImage(getActivity(), item.getHeadImage(), binding.ivHead);
        binding.tvTitle.setText(item.getName());
        binding.tvPrice.setText(PublicUtils.getRenminbi() + item.getPrice());
        binding.tvOriginalPrice.setText(PublicUtils.getRenminbi() + item.getPriceRiginal());
        if (TextUtils.isEmpty(item.getPriceRiginal())) {
            binding.tvOriginalPrice.setVisibility(View.GONE);
        } else {
            binding.tvOriginalPrice.setVisibility(View.VISIBLE);
        }

        final List<ProductselectProductByCategoryBean.DataBean.SpecArrBean> specArrs = item.getSpecArr();
        //大小
        ////////////////////////////////
        binding.gvGg.removeAllViews();
        for (int i = 0; i < sizeDatas.get(position).size(); i++) {
            final ColorSizeBean bean = sizeDatas.get(position).get(i);
            final int childPosition = i;
            FgBaginRightTypeItemLayoutBinding gvbinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_bagin_right_type_item_layout, binding.gvGg, false);
            gvbinding.tvName.setText(bean.getName().length() > 30 ? bean.getName().substring(0, 29) + "..." : bean.getName());
            LogUtils.d("getItemGg:" + bean.getName());
            if (bean.getStatus() == 0) {
                gvbinding.tvName.setBackgroundResource(R.drawable.shape_type_defult);
                gvbinding.tvName.setTextColor(Color.parseColor("#777777"));
            } else if (bean.getStatus() == 1) {
                gvbinding.tvName.setBackgroundResource(R.drawable.shape_type_on);
                gvbinding.tvName.setTextColor(Color.parseColor("#c73c3a"));
            } else if (bean.getStatus() == 2) {
                gvbinding.tvName.setBackgroundResource(R.drawable.shape_type_no);
                gvbinding.tvName.setTextColor(Color.parseColor("#dddddd"));
            }
            gvbinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                            ProductselectProductByCategoryBean.DataBean.SpecArrBean specArrBean = specArrs.get(i);
                            //判断规格是否相等
                            if (bean.getName().equals(specArrBean.getSpecSize())) {
                                for (int j = 0; j < colorBeans.size(); j++) {
                                    //设置可点击状态
                                    if (specArrBean.getSpecColor().equals(colorBeans.get(j).getName())) {
                                        if (colorBeans.get(j).getStatus() != 1) {
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
                                ProductselectProductByCategoryBean.DataBean.SpecArrBean specArrBean = specArrs.get(i);
                                //判断颜色和规格是否都相等
                                if (item.getSpec_color().equals(specArrBean.getSpecColor()) && item.getSpec_size().equals(specArrBean.getSpecSize())) {
                                    item.setHeadImage(specArrBean.getHeadImage());
                                    item.setPrice(specArrBean.getPrice());
                                    item.setSpec_id(specArrBean.getSpecId());
                                    item.setPriceRiginal(specArrBean.getPriceRiginal());
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
            binding.gvGg.addView(gvbinding.getRoot());
        }
        //颜色
        ////////////////////////////////
        binding.gvColor.removeAllViews();
        for (int i = 0; i < colorDatas.get(position).size(); i++) {
            final ColorSizeBean bean = colorDatas.get(position).get(i);
            final int childPosition = i;
            FgBaginRightTypeItemLayoutBinding gvbinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_bagin_right_type_item_layout, binding.gvGg, false);
            gvbinding.tvName.setText(bean.getName());
            LogUtils.d("getItemColor:" + bean.getName());
            if (bean.getStatus() == 0) {
                gvbinding.tvName.setBackgroundResource(R.drawable.shape_type_defult);
                gvbinding.tvName.setTextColor(Color.parseColor("#777777"));
            } else if (bean.getStatus() == 1) {
                gvbinding.tvName.setBackgroundResource(R.drawable.shape_type_on);
                gvbinding.tvName.setTextColor(Color.parseColor("#c73c3a"));
            } else if (bean.getStatus() == 2) {
                gvbinding.tvName.setBackgroundResource(R.drawable.shape_type_no);
                gvbinding.tvName.setTextColor(Color.parseColor("#dddddd"));
            }
            gvbinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
                            ProductselectProductByCategoryBean.DataBean.SpecArrBean specArrBean = specArrs.get(i);
                            //判断颜色是否相等
                            if (bean.getName().equals(specArrBean.getSpecColor())) {
                                for (int j = 0; j < sizeBeans.size(); j++) {
                                    //设置可点击状态
                                    if (specArrBean.getSpecSize().equals(sizeBeans.get(j).getName())) {
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
                                ProductselectProductByCategoryBean.DataBean.SpecArrBean specArrBean = specArrs.get(i);
                                //判断颜色和规格是否都相等
                                if (item.getSpec_color().equals(specArrBean.getSpecColor()) && item.getSpec_size().equals(specArrBean.getSpecSize())) {
                                    item.setHeadImage(specArrBean.getHeadImage());
                                    item.setPrice(specArrBean.getPrice());
                                    item.setSpec_id(specArrBean.getSpecId());
                                    item.setPriceRiginal(specArrBean.getPriceRiginal());
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
            binding.gvColor.addView(gvbinding.getRoot());
        }
        //加入配置单
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAdd = false;
                for (int i = 0; i < specArrs.size(); i++) {
                    ProductselectProductByCategoryBean.DataBean.SpecArrBean specArrBean = specArrs.get(i);
                    //判断颜色是否相等
                    if (item != null && item.getSpec_color().equals(specArrBean.getSpecColor()) && item.getSpec_size().equals(specArrBean.getSpecSize())) {
                        HandbagselectLayoutBean.DataBean.ValueBean.ProductBean productBean = new HandbagselectLayoutBean.DataBean.ValueBean.ProductBean();
                        productBean.setNum(1);
                        productBean.setImage(item.getHeadImage());
                        productBean.setPrice(item.getPrice());
                        productBean.setColor(item.getSpec_color());
                        productBean.setSize(item.getSpec_size());
                        productBean.setSpecId(item.getSpec_id());
                        productBean.setName(item.getName());
                        productBean.setProductId(item.getId());
                        //判断数据是否加载过
                        List<HandbagselectLayoutBean.DataBean.ValueBean.ProductBean> datas;
                        int tabPos = MyApp.getInstance().getTabPos();
                        int childPos = MyApp.getInstance().getChildPos();
                        datas = MyApp.getInstance().getLayoutBean().getData().get(tabPos).getValue().get(childPos).getProduct();
                        boolean isHas = false;
                        for (int j = 0; j < datas.size(); j++) {
                            if (datas.get(j).getSpecId() == productBean.getSpecId()) {
                                isHas = true;
                                datas.get(j).setNum(datas.get(j).getNum() + 1);
                                break;
                            }
                        }
                        if (!isHas) {
                            datas.add(productBean);
                        }

                        EventBus.getDefault().post(new MessageEvent(MsgConstant.BAGIN_LEFT_TYPE_REFRESH));
                        EventBus.getDefault().post(new MessageEvent(MsgConstant.REFRESH_BAGIN_PRICE));
                        isAdd = true;
                        break;
                    }
                }
                if (!isAdd) {
                    ToastUtils.toast(getString(R.string.str_hint10));
                } else {
                    ToastUtils.toast(getString(R.string.str_tjcg));
                }
            }
        });
        ////////////////////////////////
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        selectProductByCategory(false);
    }

    @Override
    public void onRefresh(boolean isShow) {
        super.onRefresh(isShow);

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            selectProductByCategory(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        if (msgEvent.getType() == BaseMsgConstant.NONET) {
            return;
        }
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.BAGIN_GETPRODUCT) {
            category23 = (int) msgEvent.getBean();
            sortType = "0";//排序方式（0=最火排序，1=最新排序，2=价格升序，3=价格降序）
            minPrice = null;//最低价格
            maxPrice = null;//最高价格
            brandId = null;//品牌ID
            hourseStyleId = null;
            headerLayoutBinding.cbHot.setChecked(true);
            headerLayoutBinding.cbNewest.setChecked(false);
            headerLayoutBinding.ivPrice.setImageResource(R.mipmap.ic_price_defualt);
            onRefresh();
            brandselectBrandByCategory1();
            selectAllHouse(category23);
            selectAllBrand(category23,0,0,0);
        } else if (msgEvent.getType() == MsgConstant.REFRESH_RIGHT_EMPTY) {
            lvBinding.llEmpty.setVisibility(View.VISIBLE);
            lvBinding.llHeader.setVisibility(View.GONE);
        }else if (msgEvent.getType() == MsgConstant.LB_SPSX_CONSTANT){
            ShopScreenBean bean = (ShopScreenBean) msgEvent.getBean();
            sortType = "0";//排序方式（0=最火排序，1=最新排序，2=价格升序，3=价格降序）
            if (bean.getMinPrice() != 0)
                minPrice = String.valueOf(bean.getMinPrice());//最低价格
            if (bean.getMaxPrice() != 0)
                maxPrice = String.valueOf(bean.getMaxPrice());//最高价格
            if (bean.getPpData() != -1)
                brandId = String.valueOf(bean.getPpData());//品牌ID
            if (bean.getFgData() != -1)
                hourseStyleId = String.valueOf(bean.getFgData());
            headerLayoutBinding.cbHot.setChecked(true);
            headerLayoutBinding.cbNewest.setChecked(false);
            headerLayoutBinding.ivPrice.setImageResource(R.mipmap.ic_price_defualt);
            onRefresh();
            selectProductByCategory(true);

        }else if (msgEvent.getType() == MsgConstant.SHOP_SCREEN_SHUAXIN_FG_BAGIN && isVisible()){
            //刷新品牌
            int hourse = (int) msgEvent.getBean();
            selectAllBrand(category23,0,0,hourse);
        }else if (msgEvent.getType() == MsgConstant.SPSX_CONSTANT_S_BAGIN){  //刷新商品
            Log.e("mTag","postpostpostpostpostpostpost------------------------");
            ShopScreenBean bean = (ShopScreenBean) msgEvent.getBean();
            pageNum = 1;
            clearData();
            hourseStyleId = String.valueOf(bean.getFgData());
            setBrandId(String.valueOf(bean.getPpData()));
            minPrice = String.valueOf(bean.getMinPrice());
            maxPrice = String.valueOf(bean.getMaxPrice());
            selectProductByCategory(true);
        }
    }

    /**
     * 根据分类查询商品或联合关键字查询（白名单接口）
     */
    private void selectProductByCategory(boolean isShow) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("keywords", "");//关键词搜索
        map.put("category23", String.valueOf(category23));//二级或三级分类ID（拎包入住的分类ID）
        map.put("brandId", "");//品牌ID
        map.put("isGetSpec", "true");//是否获取规格数据
        map.put("sortType", sortType);//排序方式
        map.put("minPrice", minPrice);//最低价格
        map.put("maxPrice", maxPrice);//最高价格
        map.put("brandId", brandId);//品牌ID
        map.put("housestyleId",hourseStyleId); //风格ID
        presenter.productselectProductByCategory(map, isShow);
    }

    /**
     * POST /app/brand/selectBrandByCategory1
     * 根据(一/二/三)分类查询品牌列表
     */
    private void brandselectBrandByCategory1() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(1));//第几页
        map.put("limit", "2000");//每页显示多少行
        map.put("category", String.valueOf(category23));//根据(一/二/三)分类查询品牌列表，空或者0则查询所有品牌
        presenter.brandselectBrandByCategory1(map);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        headerLayoutBinding.btnSelectBrand.setOnClickListener(this);
        headerLayoutBinding.cbHot.setOnClickListener(this);
        headerLayoutBinding.cbNewest.setOnClickListener(this);
        headerLayoutBinding.tvPrice.setOnClickListener(this);
        headerLayoutBinding.ivPrice.setOnClickListener(this);
        //监听搜索
        headerLayoutBinding.etMinPrice.addTextChangedListener(minPriceTextWatcher);
        headerLayoutBinding.etMinPrice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    minPrice = headerLayoutBinding.etMinPrice.getText().toString().trim();
                    maxPrice = headerLayoutBinding.etMaxPrice.getText().toString().trim();
                    onRefresh();
                }
                return false;
            }
        });
        headerLayoutBinding.etMaxPrice.addTextChangedListener(maxPriceTextWatcher);
        headerLayoutBinding.etMaxPrice.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    maxPrice = headerLayoutBinding.etMaxPrice.getText().toString().trim();
                    minPrice = headerLayoutBinding.etMinPrice.getText().toString().trim();
                    onRefresh();
                }
                return false;
            }
        });
    }

    //监听搜索
    private TextWatcher minPriceTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (TextUtils.isEmpty(text)) {
                minPrice = null;
                onRefresh(true);
            }
        }
    };
    private TextWatcher maxPriceTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (TextUtils.isEmpty(text)) {
                maxPrice = null;
                onRefresh(true);
            }
        }
    };

    private boolean isAsc = false;//是否升序

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_select_brand:
//                if (productListbBean == null || productListbBean.getData() == null || productListbBean.getData().isEmpty()){
//                    ToastUtils.toast(getString(R.string.str_wppkx));
//                }else if (hourse == null || hourse.getData() == null || hourse.getData().isEmpty()){
//                    ToastUtils.toast(getString(R.string.str_wfpkx));
//                }else {
//                    presenter.showSelectDialog(brandListBean, hourse);
//                }
//                if (brandListBean == null) {
//                    ToastUtils.toast(getString(R.string.str_wppkx));
//                } else {
//                    presenter.showSelectDialog(brandListBean);
//                }
                presenter.showSelectDialog(fgData,ppData);
                break;
            case R.id.cb_hot:
                sortType = "0";//排序方式（0=最火排序，1=最新排序，2=价格升序，3=价格降序）
                isAsc = false;
                headerLayoutBinding.cbHot.setChecked(true);
                headerLayoutBinding.cbNewest.setChecked(false);
                headerLayoutBinding.ivPrice.setImageResource(R.mipmap.ic_price_defualt);
                onRefresh();
                break;
            case R.id.cb_newest:
                sortType = "1";
                isAsc = false;
                headerLayoutBinding.cbHot.setChecked(false);
                headerLayoutBinding.cbNewest.setChecked(true);
                headerLayoutBinding.ivPrice.setImageResource(R.mipmap.ic_price_defualt);
                onRefresh();
                break;
            case R.id.iv_price:
            case R.id.tv_price:
                isAsc = !isAsc;
                if (isAsc) {
                    sortType = "2";
                    headerLayoutBinding.ivPrice.setImageResource(R.mipmap.ic_price_fall);
                } else {
                    sortType = "3";
                    headerLayoutBinding.ivPrice.setImageResource(R.mipmap.ic_price_litre);
                }
                headerLayoutBinding.cbHot.setChecked(false);
                headerLayoutBinding.cbNewest.setChecked(false);
                onRefresh();
                break;
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
        if (productListbBean != null) {
            List<ProductselectProductByCategoryBean.DataBean> listBeans = productListbBean.getData();
            for (int k = 0; k < listBeans.size(); k++) {
                List<ProductselectProductByCategoryBean.DataBean.SpecArrBean> specArrs = listBeans.get(k).getSpecArr();
                List<ColorSizeBean> sizeData = new ArrayList<>();
                List<ColorSizeBean> colorData = new ArrayList<>();
                //绑定颜色规格数据
                for (int i = 0; i < specArrs.size(); i++) {
                    String size = specArrs.get(i).getSpecSize();
                    String color = specArrs.get(i).getSpecColor();
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



    /**
     * 查询所有风格
     */
    private void selectAllHouse(int category3Id) {
        Map<String, String> map = new HashMap<>();
        if (category3Id != 0 && category3Id != Constants.AllBean){
            map.put("category", String.valueOf(category3Id));
        }
        presenter.brandselectOldHouseStyleByCategory(map);
    }

    /**
     * 查询品牌
     */
    private void selectAllBrand(int category3Id,int category2Id,int category1Id,int oldHouseStyleId) {

        Log.e("mTag383",category1Id + "-----"+category2Id+"----"+category3Id+"-----"+oldHouseStyleId);
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(1));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        if (category3Id != 0 && category3Id != Constants.AllBean){
            map.put("category", String.valueOf(category3Id));
        }else if (category2Id != 0 && category2Id != Constants.AllBean){
            map.put("category", String.valueOf(category2Id));
        }else if (category1Id != Constants.AllBean){
            map.put("category", String.valueOf(category1Id));
        }
        if (oldHouseStyleId != 0 && oldHouseStyleId != Constants.AllBean){
            map.put("oldHouseStyleId",String.valueOf(oldHouseStyleId));
        }
        presenter.brandselectBrandByCategoryAndOldHouseStyle(map);

    }


}