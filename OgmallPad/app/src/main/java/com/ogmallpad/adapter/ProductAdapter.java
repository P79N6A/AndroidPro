package com.ogmallpad.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ogmallpad.bean.ProductsBean;
import com.ogmallpad.ui.fragment.ProductItemFragment;

import java.util.List;

public class ProductAdapter extends FragmentPagerAdapter {
    private static List<ProductsBean.DataBean> list;

    public ProductAdapter(FragmentManager fm, List<ProductsBean.DataBean> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return ProductItemFragment.newInstance(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}