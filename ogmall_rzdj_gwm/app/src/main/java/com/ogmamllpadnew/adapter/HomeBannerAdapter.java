package com.ogmamllpadnew.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmamllpadnew.R;
import com.shan.netlibrary.bean.AppselectHomeDataBean;


/**
 * Created by chenjunshan on 2017/02/05.
 * <p>
 * 图片轮播控件ConvenientBanner
 */
public class HomeBannerAdapter implements Holder<AppselectHomeDataBean.DataBean.BannerBean> {
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_viewpager_item_layout, null);
        mImageView = (ImageView) view.findViewById(R.id.imageView);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, AppselectHomeDataBean.DataBean.BannerBean data) {

        ImageCacheUtils.loadImage(context, data.getPicture(), mImageView);
    }
}