package com.ogmallpad.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.SPUtils;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.SpConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 欢迎页面
 * Created by chenjunshan on 18-8-8.
 */

public class SplashActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.splash_layout);
        String name = (String) SPUtils.get(SpConstant.USERNAME, "");
        String passwd = (String) SPUtils.get(SpConstant.PASSWORD, "");
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(passwd)) {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        } else {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.putExtra(LoginActivity.Splash,true);
            startActivity(intent);
        }
        finish();
    }
}
