package com.junshan.pub.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.junshan.pub.App;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

/**
 * App相关辅助工具类
 * Created by chenjunshan on 2016/7/31.
 */

public class AppUtils {
    private static final String TAG = "AppUtils";

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    //版本号
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取系统的版本
     *
     * @return
     */
    public static int getSystemVersion() {
        /*String phoneInfo="";
        phoneInfo = "Product: " + android.os.Build.PRODUCT;
               phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;
                phoneInfo += ", TAGS: " + android.os.Build.TAGS;
                 phoneInfo += ", VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE;
                phoneInfo += ", MODEL: " + android.os.Build.MODEL;
               phoneInfo += ", SDK_INT: " + Build.VERSION.SDK_INT;
                phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
                phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
                phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
                phoneInfo += ", BRAND: " + android.os.Build.BRAND;
                phoneInfo += ", BOARD: " + android.os.Build.BOARD;
                phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
                phoneInfo += ", ID: " + android.os.Build.ID;
                phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
               phoneInfo += ", USER: " + android.os.Build.USER;*/
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = App.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = App.getInstance().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取Android设备ID（android的唯一标志）
     *
     * @return
     */
    public static String getDeviceId() {
        return Settings.Secure.getString(App.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取类名
     */
    public static String getClassName() {
        ActivityManager manager = (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo cinfo = runningTasks.get(0);
        ComponentName component = cinfo.topActivity;
        return component.getClassName();
    }

    /**
     * 获取系统语言
     *
     * @return
     */
    public static String getLanguage() {
        //获取系统当前使用的语言
        String mCurrentLanguage = Locale.getDefault().getLanguage();
        return mCurrentLanguage;
    }

    /**
     * 获取系统的版本
     *
     * @return
     */
    public static void getPhoneInfo() {
        String phoneInfo = "";
        phoneInfo = "Product: " + android.os.Build.PRODUCT;
        phoneInfo += ", CPU_ABI: " + android.os.Build.CPU_ABI;
        phoneInfo += ", TAGS: " + android.os.Build.TAGS;
        phoneInfo += ", VERSION_CODES.BASE: " + android.os.Build.VERSION_CODES.BASE;
        phoneInfo += ", MODEL: " + android.os.Build.MODEL;
        phoneInfo += ", SDK_INT: " + Build.VERSION.SDK_INT;
        phoneInfo += ", VERSION.RELEASE: " + android.os.Build.VERSION.RELEASE;
        phoneInfo += ", DEVICE: " + android.os.Build.DEVICE;
        phoneInfo += ", DISPLAY: " + android.os.Build.DISPLAY;
        phoneInfo += ", BRAND: " + android.os.Build.BRAND;
        phoneInfo += ", BOARD: " + android.os.Build.BOARD;
        phoneInfo += ", FINGERPRINT: " + android.os.Build.FINGERPRINT;
        phoneInfo += ", ID: " + android.os.Build.ID;
        phoneInfo += ", MANUFACTURER: " + android.os.Build.MANUFACTURER;
        phoneInfo += ", USER: " + android.os.Build.USER;
        Log.i(TAG, "getPhoneInfo: " + phoneInfo);
    }


    /**
     * 获取设备信息 * * @return
     */
    public static String getDeviceInfo() {
        StringBuffer sb = new StringBuffer();
        sb.append("主板：" + Build.BOARD + "\n");
        sb.append("系统启动程序版本号：" + Build.BOOTLOADER + "\n");
        sb.append("系统定制商：" + Build.BRAND + "\n");
        sb.append("cpu指令集：" + Build.CPU_ABI + "\n");
        sb.append("cpu指令集2：" + Build.CPU_ABI2 + "\n");
        sb.append("设置参数：" + Build.DEVICE + "\n");
        sb.append("显示屏参数：" + Build.DISPLAY + "\n");
        sb.append("无线电固件版本：" + Build.getRadioVersion() + "\n");
        sb.append("硬件识别码：" + Build.FINGERPRINT + "\n");
        sb.append("硬件名称：" + Build.HARDWARE + "\n");
        sb.append("HOST：" + Build.HOST + "\n");
        sb.append("修订版本列表：" + Build.ID + "\n");
        sb.append("硬件制造商：" + Build.MANUFACTURER + "\n");
        sb.append("版本：" + Build.MODEL + "\n");
        sb.append("硬件序列号：" + Build.SERIAL + "\n");
        sb.append("手机制造商：" + Build.PRODUCT + "\n");
        sb.append("描述Build的标签：" + Build.TAGS + "\n");
        sb.append("TIME：" + Build.TIME + "\n");
        sb.append("builder类型：" + Build.TYPE + "\n");
        sb.append("USER：" + Build.USER + "\n");
        LogUtils.d(sb.toString());
        return sb.toString();
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取android系统版本
     *
     * @return
     */
    public static String getVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    /**
     * 获取IP地址
     *
     * @return
     */
    public static String getHostIP() {
        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }

}
