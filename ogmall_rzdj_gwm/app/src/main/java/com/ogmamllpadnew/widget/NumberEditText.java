package com.ogmamllpadnew.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.widget.ClearEditText;

/**
 * Created by root on 18-12-15.
 */

public class NumberEditText extends ClearEditText {
    private int intLength = 8;//整数长度
    private int decimalsLength = 2;//小数长度

    public NumberEditText(Context context) {
        super(context);
        initEvent();
    }

    public NumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEvent();
    }

    private String oldText = "";

    private void initEvent() {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                oldText = charSequence.toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                LogUtils.d("afterTextChanged:" + text);
                if (!TextUtils.isEmpty(text)) {
                    if (text.contains(".")) {
                        int index = text.indexOf(".");
                        String intData = text.substring(0, index);
                        LogUtils.d("intData:" + intData);
                        String decimalsData = text.substring(index + 1, text.length());
                        LogUtils.d("decimalsData:" + decimalsData);
                        if (intData.length() > intLength) {
                            setText(oldText);
                            setSelection(oldText.length() - 1);
                        }
                        if (decimalsData.length() > decimalsLength) {
                            setText(oldText);
                            setSelection(oldText.length() - 1);
                        }
                    } else if (text.length() > intLength) {
                        setText(oldText);
                        setSelection(oldText.length() - 1);
                    }
                }
            }
        });
    }
}
