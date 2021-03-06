package com.junshan.pub.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.junshan.pub.R;
import com.junshan.pub.utils.ScreenUtils;


/**
 * 公共弹出框
 * Created by 陈俊山 on 2016/3/24.
 */
public class CommonDialog {
    private Dialog dialog;
    private int gravity;//弹出宽的位置，默认居中
    private float width;//弹出框的宽度,默认0.8，例如：1表示全屏，0.8表示为屏幕宽度的0.8倍
    private float height;//默认布局自动加载高度
    private int shape;//弹出框形状，默认方角
    private int resId;//布局id
    private int animResId;//动画id
    private boolean isTouchOutside;//点击屏幕是否可消失
    private ViewDataBinding binding;
    public static final int CIRCLE = R.drawable.dialog_circle_shape;//圆角
    public static final int SQUARE = R.drawable.dialog_square_shape;//方角
    public static final int DIALOG_OnLine_IN_OUT = R.style.DarkAnimation;//默认弹出
    public static final int DIALOG_IN_OUT = R.style.dialog_in_out;//向上弹起向下滑落
    public static final int DIALOG_LEFT_RIGHT = R.style.dialog_left_right;//从左弹出从右关闭


    private CommonDialog(Builder builder) {
        this.gravity = builder.gravity;
        this.width = builder.width;
        this.height = builder.height;
        this.shape = builder.shape;
        this.resId = builder.resId;
        this.animResId = builder.animResId;
        this.binding = builder.binding;
        this.dialog = builder.dialog;
        this.isTouchOutside = builder.isTouchOutside;
        this.dialog.setOnKeyListener(listener);
    }

    public static class Builder {
        private WindowManager windowManager;
        private Display display;
        private Activity activity;
        private Dialog dialog;
        private ViewDataBinding binding;
        private int gravity = Gravity.CENTER;
        private float width = 0.9f;
        private float height = 0.0f;
        private int shape = SQUARE;
        private int resId;
        private int animResId = DIALOG_IN_OUT;
        private boolean isTouchOutside = true;

        public Builder(Context context) {
            this.activity = (Activity) context;
            windowManager = activity.getWindowManager();
            display = windowManager.getDefaultDisplay();
        }

        public Builder setGravity(int GRAVITY) {
            this.gravity = GRAVITY;
            return this;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public Builder setShape(int shape) {
            this.shape = shape;
            return this;
        }

        public Builder setResId(int resId) {
            this.resId = resId;
            return this;
        }

        public Builder setAnimResId(int animResId) {
            this.animResId = animResId;
            return this;
        }

        public Builder setTouchOutside(boolean touchOutside) {
            isTouchOutside = touchOutside;
            return this;
        }

        public CommonDialog build() {
            binding = DataBindingUtil.inflate(LayoutInflater.from(activity), resId, null, false);
            dialog = new Dialog(activity) {
                @Override
                public void show() {
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                    super.show();
                    fullScreenImmersive(getWindow().getDecorView());
                    this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                }
            };
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题
            dialog.setCanceledOnTouchOutside(isTouchOutside);//点击屏幕消失
            dialog.setContentView(binding.getRoot());
            Window dialogwWindow = dialog.getWindow();
            WindowManager.LayoutParams lp = dialogwWindow.getAttributes();
            if (width != 0)
                lp.width = (int) (display.getWidth() * width);//设置Dialog宽度
            if (height != 0)
                lp.height = (int) ((display.getHeight() + ScreenUtils.getStatusHeight() * 2) * height);//设置Dialog高度
            dialogwWindow.setAttributes(lp);
            dialogwWindow.setGravity(gravity);//设置dialog显示位置
            dialogwWindow.setBackgroundDrawableResource(shape);//设置dialog背景风格
            if (animResId != 0) {
                dialogwWindow.setWindowAnimations(animResId);//设置动画效果
            }
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    fullScreenImmersive(activity.getWindow().getDecorView());
                    diaLogDissmiss();
                }
            });
            return new CommonDialog(this);
        }

        private void fullScreenImmersive(View view) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                //| View.SYSTEM_UI_FLAG_FULLSCREEN;
                view.setSystemUiVisibility(uiOptions);
            }
        }

        public void diaLogDissmiss() {
        }
    }

    /**
     * 关闭dialog
     */
    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 显示dialog
     */
    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 是否show
     *
     * @return
     */
    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    private DialogInterface.OnKeyListener listener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.KEYCODE_BACK) {
                return !isTouchOutside;
            }
            return false;
        }
    };

    public Dialog getDialog() {
        return dialog;
    }
}
