package com.ogmallpad.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.junshan.pub.adapter.ViewPagerAdapter;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.R;
import com.ogmallpad.bean.ShopScreenBean;
import com.ogmallpad.contract.BktjtabContract;
import com.ogmallpad.databinding.FgBktjtabLayoutBinding;
import com.ogmallpad.databinding.SearchHeaderLayoutBinding;
import com.ogmallpad.presenter.BktjtabPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.BrandlistBean;
import com.shan.netlibrary.bean.CategorysBean;
import com.shan.netlibrary.bean.CategorysofNameBean;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ogmallpad.ui.fragment.BaginLeftFragment.currentPos;
import static com.ogmallpad.ui.fragment.BaginLeftFragment.viewPager;

/**
 * 爆款推荐
 * Created by chenjunshan on 2018-07-06.
 */

public class BktjtabFragment extends BaseFragment<FgBktjtabLayoutBinding, Object> implements BktjtabContract.View {
    private BktjtabPresenter presenter;
    private String sortType = "1";
    private String order = "0";
    private String categoryId = "487";
    private StylesBean stylesBean;
    private BrandlistBean brandlistBean;

    @Override
    public int bindLayout() {
        return R.layout.fg_bktjtab_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.VISIBLE);
        titleBinding.tvLeft.setText(getString(R.string.str_bkzq));
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BktjtabPresenter(getActivity(), this);
        categorysOfName();
        addHeadView();
        style();
        brandlist();
    }

    /**
     * 根据分类名称搜索分类信息
     */
    private void categorysOfName() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "爆款");
        presenter.categorysofName(map);
    }

    /**
     * 获取产品分类列表 categoryId 不传时，获取所有的一级分类
     */
    private void getData1(int id) {
        //获取产品分类列表 categoryId 不传时，获取所有的一级分类
        Map<String, String> map = new HashMap<>();
        map.put("categoryId", String.valueOf(id));
        presenter.categorys(map);
    }

    private List<BrandproductFragment> list;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.CATEGORYSOFNAME) {
            CategorysofNameBean bean = (CategorysofNameBean) baseBean;
            if (bean.getData().size() > 0) {
                getData1(bean.getData().get(0).getId());
            }
        } else if (mTag == HttpPresenter.CATEGORYS) {
            CategorysBean bean = (CategorysBean) baseBean;
            list = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (int i = 0; i < bean.getData().size(); i++) {
                BrandproductFragment brandproductFragment = new BrandproductFragment();
                brandproductFragment.setId(bean.getData().get(i).getId());
                brandproductFragment.setAddHeader(false);
                list.add(brandproductFragment);
                titles.add(bean.getData().get(i).getName());
                initSecond(bean.getData().get(i).getName());
            }
            ((RadioButton)mBinding.llTop.getChildAt(0)).setChecked(true);
            mBinding.bktjViewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(), list, titles));
            mBinding.bktjTabs.setViewPager(mBinding.bktjViewPager);
            mBinding.bktjTabs.setTextSize(14);

            mBinding.bktjViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0 ; i < mBinding.llTop.getChildCount() ; i++){
                        if (i == position){
                            ((RadioButton)mBinding.llTop.getChildAt(i)).setChecked(true);
                        }else {
                            ((RadioButton)mBinding.llTop.getChildAt(i)).setChecked(false);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }else if (mTag == HttpPresenter.STYLES) {
            stylesBean = (StylesBean) baseBean;
        } else if (mTag == HttpPresenter.BRANDLIST) {
            brandlistBean = (BrandlistBean) baseBean;
        }
    }

    private void initSecond(String name) {
        final RadioButton childView = (RadioButton) LayoutInflater.from(getActivity()).inflate(R.layout.spzs_header_top_item_layout, mBinding.llTop, false);
        childView.setText(name);
        mBinding.llTop.addView(childView);

        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0 ; i < mBinding.llTop.getChildCount() ; i++){
                    if (childView == mBinding.llTop.getChildAt(i)){
                        ((RadioButton)mBinding.llTop.getChildAt(i)).setChecked(true);
                        mBinding.bktjViewPager.setCurrentItem(i);
                    }else {
                        ((RadioButton)mBinding.llTop.getChildAt(i)).setChecked(false);
                    }
                }

            }
        });
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
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_sp_sx:
                presenter.showSxDialog(stylesBean,brandlistBean);
                break;
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.ivToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list != null && list.size() > 0) {
                    mBinding.bktjViewPager.setCurrentItem(0);
                }
            }
        });
    }
    private SearchHeaderLayoutBinding headerBinding;
    public void addHeadView(){

        headerBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.search_header_layout, titleBinding.frameRight, false);
        titleBinding.frameRight.addView(headerBinding.getRoot());
        headerBinding.etSearch.setHint("商品名称");
        headerBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER){
                    search();
                    SoftKeyBoardUtils.closeKeybord(view, getActivity());
                    return true;
                }
                return false;
            }
        });
        headerBinding.tvSpSx.setOnClickListener(this);
    }
    private void search() {
        String text = headerBinding.etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.toast("请输入商品名称");
            return;
        }
        //隐藏键盘
        SoftKeyBoardUtils.closeKeybord(headerBinding.etSearch, getActivity());
        list.get(currentPos).startSearch(text);
    }

    @Override
    public void query(ShopScreenBean bean) {
        pageNum = 1;
        Map<String, String> map = new HashMap<>();
        map.put("categoryId", categoryId);//所属分类id
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        if (bean.getFgData() != 0)
            map.put("styleId", String.valueOf(bean.getFgData()));//风格id
        if (bean.getPpData() != 0)
            map.put("brandId", String.valueOf(bean.getPpData()));//品牌id
        if (bean.getMinPrice() != 0)
            map.put("minPrice", String.valueOf(bean.getMinPrice()));//最小价格
        if (bean.getMaxPrice() != 0)
            map.put("maxPrice", String.valueOf(bean.getMaxPrice()));//最大价格
        map.put("sortType", sortType);//排序方式 1 最热，2 价格，3 最新
        map.put("order", order);//排序方案 升序还是降序 0 降序，1升序
        presenter.productsearch(map, false);
    }

    private void style(){
        Map<String, String> map = new HashMap<>();
        map.put("categotyId", categoryId);
        presenter.styles(map);
    }

    private void brandlist(){
        Map<String, String> map2 = new HashMap<>();
        map2.put("pageNo", "1");
        map2.put("pageSize", "5000");
        map2.put("categoryId", categoryId);
        presenter.brandlist(map2, false);
    }
}
