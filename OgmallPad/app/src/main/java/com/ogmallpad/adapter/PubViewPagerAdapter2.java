package com.ogmallpad.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by chenjunshan on 17-2-26.
 */

public class PubViewPagerAdapter2<T extends Fragment> extends FragmentPagerAdapter {
    private List<T> list;
    private List<String> titls;

    public PubViewPagerAdapter2(FragmentManager fm, List<T> list, List<String> titls) {
        super(fm);
        this.list = list;
        this.titls = titls;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titls.get(position);
    }
}
