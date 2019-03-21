package com.ogmallpad.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ogmallpad.ui.fragment.GuideFragment;
import com.ogmallpad.utils.Utils;

import java.util.List;

public class GuideAdapter extends FragmentPagerAdapter {
    private static List<Utils.LibraryObject> list;

    public GuideAdapter(FragmentManager fm, List<Utils.LibraryObject> list) {
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