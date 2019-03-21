package com.gwm.messagesendreceive;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import com.gwm.HermsMessageAidl;
import com.gwm.base.BaseAppcation;
import com.gwm.haware.base.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 支持跨进程的MessageBus
 */
public class HermsMessageBus {
    private static HermsMessageBus hermsMessageBus = new HermsMessageBus();

    private static List<HermsMessageAidl> aidls = new ArrayList<>();
    private HermsMessageBus(){}
    public static HermsMessageBus getBus(){
        return hermsMessageBus;
    }

    public static void init(BaseAppcation appcation){
        List<Class<? extends HermsMessageBusService>> services = appcation.getHersMessageServices();
        if (services != null && !services.isEmpty())
            for (Class service : services){
                appcation.bindService(new Intent(appcation,service), new ServiceConnection() {
                    private HermsMessageAidl hermsMessageAidl;
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        hermsMessageAidl = HermsMessageAidl.Stub.asInterface(service);
                        aidls.add(hermsMessageAidl);
                    }
                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        aidls.remove(hermsMessageAidl);
                        hermsMessageAidl = null;
                    }
                },Context.BIND_AUTO_CREATE);
            }
    }
    public void register(Object obj){
        MessageBus.getBus().register(obj);
    }
    public void unregister(Object object){
        MessageBus.getBus().unregister(object);
    }
    /**
     * 发送消息，自行判断群发还是单发
     * @param obj
     */
    public void postMsg(HermsMessage obj){
        if (!TextUtils.isEmpty(obj.getAction())) {
            post(obj);
        }else {
            postAll(obj);
        }
    }

    public void postAll(HermsMessage obj) {
        try {
            for (HermsMessageAidl aidl : aidls){
                try {
                    if (aidl != null)
                        aidl.postAll(obj);
                }catch (NullPointerException e){
                    continue;
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void post(HermsMessage obj) {
        try {
            for (HermsMessageAidl aidl : aidls){
                try {
                    if (aidl != null)
                        aidl.post(obj);
                }catch (NullPointerException e){
                    continue;
                }
            }
        } catch (RemoteException e){
            e.printStackTrace();
        }
    }

}
