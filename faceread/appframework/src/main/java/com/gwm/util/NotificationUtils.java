package com.gwm.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.RemoteViews;

import com.gwm.base.BaseActivity;

/**
 * Created by Administrator on 2017/7/27.
 */

public class NotificationUtils {

    private Context context;
    private NotificationManager notificationManager;
    private static NotificationUtils instance;

    private NotificationUtils(Context context){
        this.context = context;
        notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static synchronized NotificationUtils getInstance(Context context){
        if(instance == null){
            instance = new NotificationUtils(context);
        }
        return instance;
    }

    /**
     * 显示普通通知
     * @param activity 点击通知以后启动的activity
     * @param icon  通知图标
     * @param title  通知标题
     * @param content 通知内容
     * @param ticker 在状态栏上显示的内容
     */
    public void showNotification(Class<? extends BaseActivity> activity, int icon, String title, String content, String ticker){
        Notification.Builder builder = new Notification.Builder(
                context);

        Intent intent = new Intent(context, activity);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(icon);// 设置图标
        builder.setWhen(System.currentTimeMillis());// 设置通知来到的时间
        // builder.setAutoCancel(true);
        builder.setContentTitle(title);// 设置通知的标题
        builder.setContentText(content);// 设置通知的内容
        builder.setTicker(ticker);// 状态栏上显示
        builder.setOngoing(true);

        // 获取Android多媒体库内的铃声
        builder.setSound(Uri.withAppendedPath(
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "5"));

        // builder.setVibrate(new long[]{2000,1000,4000}); //需要真机测试
        Notification notification = builder.build();
        // notification.flags =Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(0, notification);
    }

    /**
     * 显示自定义通知
     * @param activity 点击通知以后启动的activity
     * @param icon 通知图标
     * @param customViewId  View视图ID
     * @param ticker 在状态栏上显示的内容
     */
    public RemoteViews showCustomNotification(Class<? extends BaseActivity> activity, int icon, int customViewId, String ticker){
        Notification.Builder builder = new Notification.Builder(
                context);

        Intent intent = new Intent(context, activity);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(icon);// 设置图标
        builder.setWhen(System.currentTimeMillis());// 设置通知来到的时间
        RemoteViews view = new RemoteViews(context.getPackageName(),customViewId);
        builder.setContent(view);
        builder.setTicker(ticker);// 状态栏上显示
        builder.setOngoing(true);
        // 获取Android多媒体库内的铃声
        builder.setSound(Uri.withAppendedPath(
                MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "5"));

        // builder.setVibrate(new long[]{2000,1000,4000}); //需要真机测试
        Notification notification = builder.build();
        notification.flags =Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
        return view;
    }
}
