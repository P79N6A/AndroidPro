package com.junshan.pub.ui.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.junshan.pub.R;
import com.junshan.pub.manager.StatusBar;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.widget.SlidingLayout;

import java.lang.reflect.Method;


/**
 * Created by 陈俊山 on 16/8/30.
 */

public class CommonActivity extends FragmentActivity {
    public static final String FRAGMENT_CLASS = "fragment_class";
    public static final String ISSLIDINGCLOSE = "isSlidingClose";
    public static final String ISFULLSCREEN = "isFullScreen";//是否全屏
    protected boolean isSlidingClose = true;//是否测滑关闭activity

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFullScreen = getIntent().getBooleanExtra(ISFULLSCREEN, false);
        if (isFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        }
        setContentView(R.layout.activity_common);
        //禁止横竖屏切换
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //设置状态栏颜色
        new StatusBar(this).showStatusBar(R.color.transparent);

        try {
            Class fragmentClass = (Class) getIntent().getSerializableExtra(FRAGMENT_CLASS);
            if (fragmentClass == null)
                return;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = null;
            fragment = (Fragment) fragmentClass.newInstance();
            if (fragment == null)
                return;
            transaction.replace(R.id.frame_common, fragment, fragmentClass.getName());
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        isSlidingClose = getIntent().getBooleanExtra(ISSLIDINGCLOSE, true);
        if (isSlidingClose) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        }

        //获取顶层视图
        decorView = getWindow().getDecorView();
        listenSoftKey();
    }

    private View decorView;

    //隐藏底部虚拟键Navigation Bar实现全屏
    private void initNavigationBar() {
        int flag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        //判断当前版本在4.0以上并且存在虚拟按键，否则不做操作
        if (Build.VERSION.SDK_INT < 19 || !checkDeviceHasNavigationBar()) {
            //一定要判断是否存在按键，否则在没有按键的手机调用会影响别的功能。如之前没有考虑到，导致图传全屏变成小屏显示。
            return;
        } else {
            // 获取属性
            decorView.setSystemUiVisibility(flag);
        }
    }

    /**
     * 判断是否存在虚拟按键
     *
     * @return
     */
    public boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNavigationBar();
    }

    /**
     * 监听软件盘
     */
    private void listenSoftKey(){
        //键盘监听
        SoftKeyBoardUtils.setListener(this, new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
            }

            @Override
            public void keyBoardHide(int height) {
            }
        });
    }
}
