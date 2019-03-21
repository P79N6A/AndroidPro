package com.ogmamllpadnew.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogmamllpadnew.R;
import com.ogmamllpadnew.utils.Utils;

import java.util.List;

import static com.ogmamllpadnew.utils.Utils.setupItem;


/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {

    private List<Utils.LibraryObject> libraries;

    private LayoutInflater mLayoutInflater;


    public HorizontalPagerAdapter(final Context context, List<Utils.LibraryObject> libraries) {
        this.libraries = libraries;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return libraries.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.item, container, false);
        setupItem(view, libraries.get(position));
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImage(libraries, position);
            }
        });
        setListener(view);
        return view;
    }

    public void setListener(View view) {

    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }

    public void showImage(List<Utils.LibraryObject> libraries, int position) {

    }
}
