package com.ogmall.indexscreen;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Administrator on 2019/3/11.
 */

public class StoreIndexLayout extends FrameLayout {
    private FrameLayout frameTitle;
    private ImageView iv_storeImg;
    private ImageView iv_wai,iv_center,iv_li;
    public StoreIndexLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.index_storey_layout,this,false);
        frameTitle = view.findViewById(R.id.frameTitle);
        iv_storeImg = view.findViewById(R.id.iv_storeyImg);
        iv_wai = view.findViewById(R.id.iv_wai);
        iv_center = view.findViewById(R.id.iv_center);
        iv_li = view.findViewById(R.id.iv_li);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.StoreIndexLayout);
        Drawable titleImg = array.getDrawable(R.styleable.StoreIndexLayout_titleImg);
        Drawable storeImg = array.getDrawable(R.styleable.StoreIndexLayout_storeImg);
        setTitleImg(titleImg);
        setStoreyImg(storeImg);
        array.recycle();
        addView(view);

        initAnim();
    }

    private void initAnim() {
        Animation animWai = AnimationUtils.loadAnimation(getContext(),R.anim.rote_wai);
        iv_wai.startAnimation(animWai);

        Animation animCenter = AnimationUtils.loadAnimation(getContext(),R.anim.rote_center);
        iv_center.startAnimation(animCenter);

        Animation animLi = AnimationUtils.loadAnimation(getContext(), R.anim.rote_li);
        iv_li.startAnimation(animLi);
    }

    private void setStoreyImg(Drawable storeImg) {
        iv_storeImg.setImageDrawable(storeImg);
    }

    private void setTitleImg(Drawable titleImg) {
        frameTitle.setBackground(titleImg);
    }

}
