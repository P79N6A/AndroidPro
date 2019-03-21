package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.adapter.ViewPagerAdapter;
import com.junshan.pub.utils.PublicUtils;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.contract.ProductlistContract;
import com.ogmallpad.databinding.ProductListTabLayoutBinding;
import com.ogmallpad.presenter.ProductlistPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.DesigndetailsBean;
import com.shan.netlibrary.bean.ProductscategorysBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品展示
 * Created by chenjunshan on 18-7-5.
 */

public class ProductListTabFragment extends BaseFragment<ProductListTabLayoutBinding, Object> implements ProductlistContract.View {
    private DesigndetailsBean designdetailsBean;//方案详情
    private ProductlistPresenter presenter;
    private int category3Id;
    private String title;

    @Override
    public int bindLayout() {
        return R.layout.product_list_tab_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.tvLeft.setText(getString(R.string.str_spqd));
        titleBinding.tvRight.setVisibility(View.VISIBLE);
        if (designdetailsBean != null)
            titleBinding.tvRight.setText(PublicUtils.getRenminbi() + designdetailsBean.getData().getAllMoney());
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        designdetailsBean = (DesigndetailsBean) getActivity().getIntent().getSerializableExtra(Constants.BEAN);
        category3Id = getActivity().getIntent().getIntExtra(Constants.CATEGORY3ID,0);
        title = getActivity().getIntent().getStringExtra(Constants.TITLE);
        presenter = new ProductlistPresenter(getActivity(), this);
        //获取商品风格
        productsCategorys();



    }

    /**
     * 获取商品风格
     */
    private void productsCategorys() {
        Map<String, String> map = new HashMap<>();
        if (designdetailsBean != null){
            map.put("id", String.valueOf(designdetailsBean.getData().getId()));
        }else {
            map.put("id", String.valueOf(category3Id));
        }
        map.put("type", "2");// 1 brand 2 design 3 room
        presenter.productscategorys(map);
    }

    private List<Fragment> list;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PRODUCTSCATEGORYS) {
            ProductscategorysBean bean = (ProductscategorysBean) baseBean;
            if (bean != null && bean.getData().size() > 0) {
                ProductscategorysBean.DataBean allBean = new ProductscategorysBean.DataBean();
                allBean.setCategoryName("全部");
                allBean.setCategoryId(10000);
                bean.getData().add(0, allBean);

                list = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                for (int i = 0; i < bean.getData().size(); i++) {
                    ProductlistFragment productlistFragment = new ProductlistFragment();
                    if (designdetailsBean != null)
                        productlistFragment.setPlanid(designdetailsBean.getData().getId());
                    else
                        productlistFragment.setId(category3Id);
                    productlistFragment.setId(bean.getData().get(i).getCategoryId());
                    list.add(productlistFragment);
                    titles.add(bean.getData().get(i).getCategoryName());
                }
                mBinding.productLsitViewPager.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(), list, titles));
                mBinding.productLsitTabs.setViewPager(mBinding.productLsitViewPager);
                mBinding.productLsitTabs.setTextSize(14);
                if (!TextUtils.isEmpty(title) && category3Id != 0){
                    for (int i = 0 ; i < titles.size() ; i++){
                        String string = titles.get(i);
                        if (string.equals(title)){
                            mBinding.productLsitViewPager.setCurrentItem(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.ivToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list != null && list.size() > 0) {
                    mBinding.productLsitViewPager.setCurrentItem(0);
                }
            }
        });
    }

}
