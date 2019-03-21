package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.adapter.PubViewPagerAdapter;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.ProductlisttabContract;
import com.ogmamllpadnew.databinding.FgProductlisttabLayoutBinding;
import com.ogmamllpadnew.databinding.ShopProductHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.TitleRightSearchLayoutBinding;
import com.ogmamllpadnew.presenter.ProductlisttabPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.BrandselectBrandCategory1Bean;
import com.shan.netlibrary.bean.BrandselectOldHouseStyleByCategoryBean;
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

public class BrandlisttabFragment extends BaseFragment<FgProductlisttabLayoutBinding, Object> implements ProductlisttabContract.View {
    private ProductlisttabPresenter presenter;
    private int category;//一级分类ID（category1=null||category1=0查询全部，category1=-1查主推品牌，category1=381查品牌家具...）
    private int category1Id;
    private int category2Id;
    private int category3Id;
    private boolean isSearch = false;
    private String word;


    @Override
    public int bindLayout() {
        return R.layout.fg_productlisttab_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.btnLeft.setImageResource(R.mipmap.ic_brand_on);
        setLeftText(R.string.str_ppzs);

        addHeaderView();
    }

    @Override
    public void onLeftClick() {
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ProductlisttabPresenter(getActivity(), this);
        category1Id = getActivity().getIntent().getIntExtra(Constants.CATEGORY1ID, 0);
        category2Id = getActivity().getIntent().getIntExtra(Constants.CATEGORY2ID, 0);
        category3Id = getActivity().getIntent().getIntExtra(Constants.CATEGORY3ID, 0);
        brandselectBrandCategory1();
    }

    private TitleRightSearchLayoutBinding searchLayoutBinding;

    private void addHeaderView() {
        //添加搜索View
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.rightMargin = 180;
        searchLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.title_right_search_layout, titleBinding.rl, false);
        titleBinding.rl.addView(searchLayoutBinding.getRoot(), params);
        searchLayoutBinding.etSearch.setHint(getString(R.string.str_ppmc));
        searchLayoutBinding.tvAdd.setVisibility(View.GONE);
        searchLayoutBinding.tvScreen.setVisibility(View.GONE);
    }

    private BrandselectBrandCategory1Bean category1Bean;
    private BrandselectOldHouseStyleByCategoryBean category2Bean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.BRANDSELECTBRANDCATEGORY1) {
            category1Bean = (BrandselectBrandCategory1Bean) baseBean;
            List<BrandselectBrandCategory1Bean.DataBean> dataBeans = category1Bean.getData();
            BrandselectBrandCategory1Bean.DataBean dataBean = new BrandselectBrandCategory1Bean.DataBean();
            dataBean.setCategory1(Constants.AllBean);
            dataBean.setName(getString(R.string.str_qb));
            dataBean.setCheck(true);
            dataBeans.add(0, dataBean);
            if (dataBeans.size() > 0) {
                category1Bean.getData().get(0).setCheck(true);
                category = category1Bean.getData().get(0).getCategory1();
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
        } else if (mTag == HttpPresenter.BRANDSELECTOLDHOUSESTYLEBYCATEGORY) {
            category2Bean = (BrandselectOldHouseStyleByCategoryBean) baseBean;
            BrandselectOldHouseStyleByCategoryBean.DataBean dataBean = new BrandselectOldHouseStyleByCategoryBean.DataBean();
            dataBean.setId(Constants.AllBean);
            dataBean.setName(getString(R.string.str_qb));
            category2Bean.getData().add(0, dataBean);
            //初始化三级分类数据
            initThreeTitle();
        }
    }

    private List<BrandFragment> fragments;
    /**
     * 初始化三级分类数据
     */
    private void initThreeTitle() {
        //滑动tab
        fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < category2Bean.getData().size(); i++) {
            BrandFragment brandFragment = new BrandFragment();
            fragments.add(brandFragment);
            titles.add(category2Bean.getData().get(i).getName());
            brandFragment.setCategory(category);
            brandFragment.setOldHouseStyleId(category2Bean.getData().get(i).getId());
            if (i == 0 && isSearch){
                if (!TextUtils.isEmpty(word)){
                    brandFragment.setWord(word);
                    brandFragment.setIsSearch(true);
                    isSearch = false;
                    word = null;
                }
            }
        }
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

            }

            @Override
            public void onPageSelected(int position) {
                searchLayoutBinding.etSearch.setText("");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<CheckBox> checkBoxes;
    private List<BrandselectBrandCategory1Bean.DataBean> dataBeans;

    /**
     * 初始化二级分类数据
     */
    private void initTowTitle() {
        dataBeans = category1Bean.getData();
        checkBoxes = new ArrayList<>();
        mBinding.llTitle.removeAllViews();
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
     * 刷新顶部title状态
     */
    private void refreshTitleStatus() {
        if (checkBoxes != null) {
            for (int i = 0; i < dataBeans.size(); i++) {
                checkBoxes.get(i).setChecked(dataBeans.get(i).isCheck());
            }
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
                dataBeans.get(j).setCheck(true);
            } else {
                checkBoxes.get(j).setChecked(false);
                dataBeans.get(j).setCheck(false);
            }
        }
        category = dataBeans.get(i).getCategory1();
        selectCategory3ByCategory2();
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.cancelAllRequest();
        }
        super.onDestroy();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        SoftKeyBoardUtils.setListener(getActivity(), new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {

            }
        });
        searchLayoutBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    word = searchLayoutBinding.etSearch.getText().toString().trim();
                    //TODO 恢复当前页到初始状态
                    initTowTitle();
                    refreshOneTileStatus();

                    isSearch = true;
                    selectCategory3ByCategory2();
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

    /**
     * 查询品牌一级分类（白名单接口）
     */
    private void brandselectBrandCategory1() {
        presenter.brandselectBrandCategory1(null);
    }

    /**
     * POST /app/brand/selectOldHouseStyleByCategory
     * 根据(一/二/三级)分类查询老系统风格列表，其中category=-1时表示主推品
     */
    private void selectCategory3ByCategory2() {
        Map<String, String> map = new HashMap<>();
        if (category != Constants.AllBean) {
            map.put("category", String.valueOf(category));//二级分类ID
        }
        presenter.brandselectOldHouseStyleByCategory(map);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshTitleStatus();
    }

    private void refreshOneTileStatus() {
        for (int j = 0; j < checkBoxes.size(); j++) {
            if (j == 0) {
                checkBoxes.get(j).setChecked(true);
                dataBeans.get(j).setCheck(true);
            } else {
                checkBoxes.get(j).setChecked(false);
                dataBeans.get(j).setCheck(false);
            }
        }
        category = dataBeans.get(0).getCategory1();
    }
}
