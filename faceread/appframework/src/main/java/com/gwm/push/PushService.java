package com.gwm.push;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gwm.android.ThreadManager;
import com.gwm.base.BaseRunnable;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Request;
public class PushService extends Service{
    public static final String PUSH_URL = "push_url";

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(PUSH_URL);
        WebSocketListener socketListener = new WebSocketListener();
        ThreadManager.getInstance().run(new WebSocket(url,socketListener));
        return super.onStartCommand(intent, flags, startId);
    }

    static class WebSocket extends BaseRunnable {
        private WebSocketListener webSocketListener;
        private String url;

        public WebSocket(String url, WebSocketListener webSocketListener){
            this.url = url;
            this.webSocketListener = webSocketListener;
        }
        @Override
        public void run() {
            Request request = new Request.Builder().url(url).build();
            OkHttpUtils.getInstance().getOkHttpClient().newWebSocket(request, webSocketListener);
        }
    }
}
