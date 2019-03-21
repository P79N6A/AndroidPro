package com.ogmamllpadnew.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ogmamllpadnew.ui.fragment.GuideFragment;

import java.util.List;


public class GuideAdapter extends FragmentPagerAdapter {
    private static List<String> list;

    public GuideAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return GuideFragment.newInstance(list.get(position));
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