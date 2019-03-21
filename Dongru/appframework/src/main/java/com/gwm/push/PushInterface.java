package com.gwm.push;

import android.content.Context;
import android.content.Intent;

public class PushInterface {
    public static void init(Context context, String url){
        Intent intent = new Intent("com.push.websocket.service");
        intent.putExtra(PushService.PUSH_URL,url);
        context.startService(intent);
    }
}
