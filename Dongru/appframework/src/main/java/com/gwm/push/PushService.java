package com.gwm.push;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gwm.android.ThreadManager;
import com.gwm.base.BaseRunnable;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Request;
public class PushService extends IntentService{
    public static final String PUSH_URL = "push_url";

    public static Context mContext;

    public PushService() {
        super("PushService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String url = intent.getStringExtra(PUSH_URL);
        WebSocketListener socketListener = getWebSocketListener();
        new WebSocket(url,socketListener).run();
    }

    public WebSocketListener getWebSocketListener(){
        return null;
    }

    static class WebSocket{
        private WebSocketListener webSocketListener;
        private String url;

        public WebSocket(String url, WebSocketListener webSocketListener){
            this.url = url;
            this.webSocketListener = webSocketListener;
        }
        public void run() {
            Request request = new Request.Builder().url(url).build();
            OkHttpUtils.getInstance().getOkHttpClient().newWebSocket(request, webSocketListener);
        }
    }
}
