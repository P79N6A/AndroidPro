package com.junshan.pub;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by chenjnshan on 2016/7/5.
 */
public class App extends MultiDexApplication {
    private static App app;
    public boolean isSwitch = false;//是否开启环境切换功能

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isSwitch = true;//是否开启环境切换功能
        app = (App) getApplicationContext();
    }

    /**
     * 获取Application
     *
     * @return
     */
    public static App getInstance() {
        return app;
    }

}
