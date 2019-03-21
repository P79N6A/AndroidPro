package com.ogmall.faceread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ogmall.faceread.activity.SplashActivity;


/**
 * Created by root on 18-8-3.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //标准的写法是需要判别Action的类型的
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            //你想执行的操作
            Intent mintent = new Intent(context, SplashActivity.class);
            mintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mintent);
        }
    }
}
