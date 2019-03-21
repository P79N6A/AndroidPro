package com.gwm.messagesendreceive;

import android.os.Bundle;

public class MessageBusMessage {
    private String action;
    private Object data;
    private Bundle bundle;

    public MessageBusMessage(String action, Bundle bundle) {
        this.action = action;
        this.bundle = bundle;
    }

    public MessageBusMessage(String action) {
        this.action = action;
    }

    public MessageBusMessage(String action, Object data) {
        this.action = action;
        this.data = data;
    }

    public MessageBusMessage(HermsMessage obj) {
        this.action = obj.getAction();
        this.bundle = obj.getBundle();
    }

    public Object getData() {
        return data;
    }

    public Object getData(String key){
        if (bundle == null)
            return null;
        return bundle.get(key);
    }

    public String getAction() {
        return action;
    }
}
