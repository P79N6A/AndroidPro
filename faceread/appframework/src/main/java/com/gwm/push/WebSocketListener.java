package com.gwm.push;


import android.content.Intent;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * 规定消息类型  范本：
 *      {"type":"notification","content":"文本内容"}
 */
public class WebSocketListener extends okhttp3.WebSocketListener {
    @Override
    public void onOpen(WebSocket webSocket, Response response) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        excuteMsg(text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        byte[] array = bytes.asByteBuffer().array();
        try {
            String string = new String(array,"utf-8");
            excuteMsg(string);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    }

    private void excuteMsg(String text) {
        WebSocketMessage wsm = JSON.parseObject(text,WebSocketMessage.class);
        Intent intent = new Intent("com.gwm.websocket.message");
        intent.putExtra("message",wsm);
        if (PushService.mContext != null){
            PushService.mContext.sendBroadcast(intent);
        }
    }
}
