package com.junshan.pub.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.junshan.pub.R;
import com.junshan.pub.utils.load.CircleProgress;

/**
 * Created by chenjunshan on 2016/9/7.
 */

public class PDUtils extends Dialog {
    private CircleProgress progress = null;

    public PDUtils(Context context, boolean isCancel) {
        super(context, R.style.progress_dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.loading_layout, null);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题
        this.setCanceledOnTouchOutside(isCancel);//点击屏幕消失
        this.setContentView(view);
        Window dialogwWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogwWindow.getAttributes();
        dialogwWindow.setGravity(Gravity.CENTER);
        dialogwWindow.setAttributes(lp);
        dialogwWindow.setBackgroundDrawableResource(R.drawable.loding_dialog_square);
        //setOnDismissListener(new );
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (progress != null) {
            progress.stopAnim();
        }
    }



    /*private DialogInterface.OnKeyListener listener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.KEYCODE_BACK && !iscancel) {
                return true;
            }
            return false;
        }
    };


    public void clear(){
        pdUtils = null;
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d("onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("onCreate");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d("onStop");
    }

    @Override
    public void show() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
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
}
