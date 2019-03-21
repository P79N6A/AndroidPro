package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.adapter.PubViewPagerAdapter;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.ProductlisttabContract;
import com.ogmamllpadnew.databinding.FgProductlisttabLayoutBinding;
import com.ogmamllpadnew.databinding.TitleRightSearchLayoutBinding;
import com.ogmamllpadnew.presenter.ProductlisttabPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.BrandselectBrandByCategoryAndOldHouseStyleBean;
import com.shan.netlibrary.bean.BrandselectOldHouseStyleByCategoryBean;
import com.shan.netlibrary.bean.ProductselectCategory2ByCategory1Bean;
import com.shan.netlibrary.bean.ProductselectCategory3ByCategory2Bean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品列表
 * Created by 陈俊山 on 2018-11-08.
 */

public class ProductlisttabFragment extends BaseFragment<FgProductlisttabLayoutBinding, Object> implements ProductlisttabContract.View {
    private ProductlisttabPresenter presenter;
    private String title;
    private int category1Id;
    private int category2Id;
    private int category3Id;
    private int category2;
    private List<BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean> ppData;
    private int mCurrentItem;
    private int houseId;

    @Override
    public int bindLayout() {
        return R.layout.fg_productlisttab_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(title);
        if (title.equals("爆款专区")) {
            mBinding.tabsProduct.setVisibility(View.GONE);
        }
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ProductlisttabPresenter(getActivity(), this);
        category1Id = getActivity().getIntent().getIntExtra(Constants.CATEGORY1ID, 0);
        category2Id = getActivity().getIntent().getIntExtra(Constants.CATEGORY2ID, 0);
        category3Id = getActivity().getIntent().getIntExtra(Constants.CATEGORY3ID, 0);
        title = getActivity().getIntent().getStringExtra(Constants.TITLE);
        selectCategory2ByCategory1();

        addHeaderSearchView();
    }


    private TitleRightSearchLayoutBinding searchLayoutBinding;
    private void addHeaderSearchView() {
        //添加搜索View
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        searchLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.title_right_search_layout, titleBinding.rl, false);
        titleBinding.rl.addView(searchLayoutBinding.getRoot(), params);
        searchLayoutBinding.etSearch.setHint(getString(R.string.str_spmc));
        searchLayoutBinding.tvAdd.setVisibility(View.GONE);
        searchLayoutBinding.tvScreen.setVisibility(View.VISIBLE);
    }

    private ProductselectCategory2ByCategory1Bean category1Bean;
    private ProductselectCategory3ByCategory2Bean category2Bean;
    private List<BrandselectOldHouseStyleByCategoryBean.DataBean> fgData;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSELECTCATEGORY2BYCATEGORY1) {
            category1Bean = (ProductselectCategory2ByCategory1Bean) baseBean;
            /*ProductselectCategory2ByCategory1Bean.DataBean dataBean = new ProductselectCategory2ByCategory1Bean.DataBean();
            dataBean.setId(Constants.AllBean);
            dataBean.setName(getString(R.string.str_qb));
            dataBean.setCheck(true);
            category1Bean.getData().add(0, dataBean);
            category2 = Constants.AllBean;*/
            List<ProductselectCategory2ByCategory1Bean.DataBean> dataBeans = category1Bean.getData();
            if (dataBeans.size() > 0) {
                int checkPos = 0;
                for (int i = 0; i < dataBeans.size(); i++) {
                    if (dataBeans.get(i).getId() == category2Id) {
                        checkPos = i;
                        break;
                    }
                }
                category1Bean.getData().get(checkPos).setCheck(true);
                category2 = category1Bean.getData().get(checkPos).getId();
                //初始化二级分类数据
                initTowTitle();
                //获取三级分类数据
                selectCategory3ByCategory2();
                mBinding.llContent.setVisibility(View.VISIBLE);
                mBinding.llEmpty.setVisibility(View.GONE);
            } else {
                mBinding.llContent.setVisibility(View.GONE);
                mBinding.llEmpty.setVisibility(View.VISIBLE);
            }
        } else if (mTag == HttpPresenter.PRODUCTSELECTCATEGORY3BYCATEGORY2) {
            category2Bean = (ProductselectCategory3ByCategory2Bean) baseBean;
            ProductselectCategory3ByCategory2Bean.DataBean dataBean = new ProductselectCategory3ByCategory2Bean.DataBean();
            dataBean.setId(Constants.AllBean);
            dataBean.setName(getString(R.string.str_qb));
            category2Bean.getData().add(0, dataBean);
            //初始化三级分类数据
            initThreeTitle();
        }else if (mTag == HttpPresenter.BRANDSELECTOLDHOUSESTYLEBYCATEGORY){   //风格
            BrandselectOldHouseStyleByCategoryBean bean = (BrandselectOldHouseStyleByCategoryBean) baseBean;
            if (fgData == null){
                fgData = new ArrayList<>();
            }else {
                fgData.clear();
            }
            fgData.addAll(bean.getData());
            if (presenter != null && presenter.dialog != null && presenter.dialog.isShowing()) {
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
            if (presenter != null && presenter.dialog != null && presenter.dialog.isShowing()) {
                presenter.bindPgData(ppData);
            }
        }
    }
    private List<ProductlistFragment> fragments;
    /**
     * 初始化三级分类数据
     */
    private void initThreeTitle() {
        //滑动tab

        fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < category2Bean.getData().size(); i++) {
            ProductlistFragment productlistFragment = new ProductlistFragment();
            fragments.add(productlistFragment);
            titles.add(category2Bean.getData().get(i).getName());
            productlistFragment.setCategory2(category2);
            productlistFragment.setCategory3(category2Bean.getData().get(i).getId());
            //0=平台商品，1=自营商品；缺省值=0
            if (category1Id == -1) {
                //自营商品
                productlistFragment.setDataType("1");
            } else {
                //平台商品
                productlistFragment.setDataType("0");
            }
        }
        mBinding.tabsProduct.setPadding(1);
        mBinding.vpProduct.setAdapter(new PubViewPagerAdapter<>(getActivity().getSupportFragmentManager(), fragments, titles));
        mBinding.tabsProduct.setViewPager(mBinding.vpProduct);
        mBinding.tabsProduct.setTextSize(12);
        for (int i = 0; i < category2Bean.getData().size(); i++) {
            if (category2Bean.getData().get(i).getId() == category3Id) {
                mBinding.vpProduct.setCurrentItem(i);
                break;
            }
        }
        mBinding.vpProduct.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("mTag","onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("mTag","onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("mTag","onPageScrollStateChanged");
                if (ppData != null){
                    ppData.clear();
                }


                int currentItem = mBinding.vpProduct.getCurrentItem();
                ProductlisttabFragment.this.mCurrentItem = currentItem;
                int category1Id = ProductlisttabFragment.this.category1Id;
                int category2Id = category2;
                int category3Id = fragments.get(currentItem).getCategory3();
                //书房家具  409    书桌 410
                Log.e("mTag",category1Id + "-----"+category2Id+"----"+category3Id);
                selectAllBrand(category3Id,category2Id,category1Id,0);
                selectAllHouse(category3Id,category2Id,category1Id);

            }
        });
        Log.e("mTag",category1Id + "-----"+category2+"----"+category3Id);
        //初始化品牌
        selectAllBrand(category3Id,category2,category1Id,0);
        selectAllHouse(category3Id,category2,category1Id);
    }

    private List<CheckBox> checkBoxes;
    private List<ProductselectCategory2ByCategory1Bean.DataBean> dataBeans;

    /**
     * 初始化二级分类数据
     */
    private void initTowTitle() {
        dataBeans = category1Bean.getData();
        checkBoxes = new ArrayList<>();
        for (int i = 0; i < dataBeans.size(); i++) {
            final int j = i;
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.brand_title_item_layout, mBinding.llTitle, false);
            CheckBox cb = (CheckBox) view.findViewById(R.id.rb);
            cb.setText(dataBeans.get(i).getName());
            checkBoxes.add(cb);
            if (dataBeans.get(i).isCheck()) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refreshOneTileStatus(j);
                }
            });
            mBinding.llTitle.addView(view);
        }
    }

    /**
     * 刷新一级title的状态
     *
     * @param i
     */
    private void refreshOneTileStatus(int i) {
        for (int j = 0; j < checkBoxes.size(); j++) {
            if (j == i) {
                checkBoxes.get(j).setChecked(true);
            } else {
                checkBoxes.get(j).setChecked(false);
            }
        }
        category2 = dataBeans.get(i).getId();
        selectCategory3ByCategory2();
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
        searchLayoutBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    search();
                }
                return false;
            }
        });

        searchLayoutBinding.tvScreen.setOnClickListener(this);
    }

    private void search() {
        String keywords = searchLayoutBinding.etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keywords)){
            ToastUtils.toast("请输入商品名称");
            return;
        }
        int current = mBinding.vpProduct.getCurrentItem();
        fragments.get(current).setKeyWords(keywords);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_screen:  //商品筛选
                presenter.showShopScreenDialog(fgData,ppData);
                break;
        }
    }

    /**
     * 根据一级分类查询二级分类（白名单接口）
     */
    private void selectCategory2ByCategory1() {
        Map<String, String> map = new HashMap<>();
        if (category1Id == -1) {
            //自营商品
            map.put("dataType", "1");//0=平台商品，1=自营商品；缺省值=0
            map.put("category1", String.valueOf(category1Id));//一级分类ID
        } else {
            //平台商品
            map.put("dataType", "0");//0=平台商品，1=自营商品；缺省值=0
            map.put("category1", String.valueOf(category1Id));//一级分类ID
        }
        presenter.productselectCategory2ByCategory1(map);
    }

    /**
     * 根据二级分类查询三级分类（白名单接口）
     */
    private void selectCategory3ByCategory2() {
        Map<String, String> map = new HashMap<>();

        if (category1Id == -1) {
            //自营商品
            map.put("dataType", "1");//0=平台商品，1=自营商品；缺省值=0
        } else {
            //平台商品
            map.put("dataType", "0");//0=平台商品，1=自营商品；缺省值=0
        }

        if (category2 != Constants.AllBean) {
            map.put("category2", String.valueOf(category2));//二级分类ID
        }
        presenter.productselectCategory3ByCategory2(map);
    }

    /**
     * 查询风格
     */
    private void selectAllHouse(int category3Id,int category2Id,int category1Id) {
        Log.e("mTag366",category1Id + "-----"+category2Id+"----"+category3Id);
        Map<String, String> map = new HashMap<>();
        if (category3Id != 0 && category3Id != Constants.AllBean){
            map.put("category", String.valueOf(category3Id));
        }else if (category2Id != 0 && category2Id != Constants.AllBean){
            map.put("category", String.valueOf(category2Id));
        }else if (category1Id != Constants.AllBean){
            map.put("category", String.valueOf(category1Id));
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

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.SHOP_SCREEN_SHUAXIN_FG && isVisible()){
            //刷新品牌
            int category1Id = this.category1Id;
            int category2Id = fragments.get(mCurrentItem).getCategory2();
            int category3Id = fragments.get(mCurrentItem).getCategory3();
            int brandId = (int) msgEvent.getBean();
            selectAllBrand(category3Id,category2Id,category1Id,brandId);
        }
    }
}
