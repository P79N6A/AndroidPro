package com.ogmall.faceread.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.ogmalllarge.R;

/**
 * Created by Administrator on 2019/3/14.
 */

public class TextInfoView extends RelativeLayout {
    public TextInfoView(Context context) {
        super(context);
        initView();
    }

    public TextInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_text_info,this,false);

        addView(view);
    }
}
