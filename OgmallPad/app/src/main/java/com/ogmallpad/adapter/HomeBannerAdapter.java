package com.ogmallpad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmallpad.R;

/**
 * Created by Administrator on 2019/2/14.
 */

public class HomeBannerAdapter implements Holder<String> {
    private ImageView mImageView;
    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_viewpager_item_layout, null);
        mImageView = (ImageView) view.findViewById(R.id.imageView);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {

        ImageCacheUtils.loadImage(context, data, mImageView);
    }
}
