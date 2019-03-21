package com.ogmallpad.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ogmallpad.ui.fragment.BaginLeftChildFragment;

import java.util.List;

/**
 * Created by chenjunshan on 17-2-26.
 */

public class ViewPagerAdapter3 extends FragmentStatePagerAdapter {
    private List<? extends Fragment> list;
    private List<String> titls;

    public ViewPagerAdapter3(FragmentManager fm, List<? extends Fragment> list, List<String> titls) {
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
