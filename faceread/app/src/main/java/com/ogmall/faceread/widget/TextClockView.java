package com.ogmall.faceread.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

/**
 * Created by Administrator on 2019/3/13.
 */

public class TextClockView extends AppCompatTextView {
    public TextClockView(Context context) {
        super(context);
    }

    public TextClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void updateStyle(String content){
        setText(content);
//        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"tes.ttf");
//        setTypeface(typeface);

        getPaint().setFakeBoldText(true);
        setGravity(Gravity.CENTER);
    }
}
