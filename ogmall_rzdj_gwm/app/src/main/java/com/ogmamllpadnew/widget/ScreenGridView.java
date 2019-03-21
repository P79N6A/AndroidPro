package com.ogmamllpadnew.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by Administrator on 2018/12/22.
 */

public class ScreenGridView extends GridView {
    public ScreenGridView(Context context) {
        super(context);
    }

    public ScreenGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
