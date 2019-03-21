package com.gwm.android;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

/**
 * 底层内部接口，请查看ThreadManager跟BaseRunnable类，在使用时完全察觉不到有该类的存在
 * 不允许单独使用
 *
 * @see ThreadManager
 * @see com.gwm.base.BaseRunnable
 *
 */
public class Handler extends android.os.Handler {
    private static final SparseArray<HandlerListener> array = new SparseArray<>();
    private static final Handler handler = new Handler(Looper.getMainLooper());
    private Handler(Looper mainLooper) {
        super(mainLooper);
    }

    public static synchronized Handler getHandler(){
        return handler;
    }
    public void sengMessage(int what,int arg1,int arg2,Object obj,HandlerListener handlerListener){
        if (handlerListener != null)
            array.put(what,handlerListener);
        Message msg = obtainMessage(what,arg1,arg2,obj,null);
        handler.sendMessage(msg);
    }
    public void sendMessage(int what, Bundle bundle, HandlerListener handlerListener){
        if (handlerListener != null)
            array.put(what,handlerListener);
        Message msg = obtainMessage(what,0,0,null,bundle);
        handler.sendMessage(msg);
    }
    public void sendEmptyMessage(int what,HandlerListener listener){
        if (listener != null)
            array.put(what,listener);
        handler.sendEmptyMessage(what);
    }

    public void sendMessageDelayed(int what,int arg1,int arg2,Object obj,long delayMillis, HandlerListener handlerListener){
        if (handlerListener != null)
            array.put(what,handlerListener);
        Message msg = obtainMessage(what,arg1,arg2,obj,null);
        handler.sendMessageDelayed(msg,delayMillis);
    }
    public void sendMessageDelayed(int what,Bundle bundle,long delayMillis, HandlerListener handlerListener){
        if (handlerListener != null)
            array.put(what,handlerListener);
        Message msg = obtainMessage(what,0,0,null,bundle);
        handler.sendMessageDelayed(msg,delayMillis);
    }
    public void sendEmptyMessageDelayed(int what,long delayMillis, HandlerListener handlerListener){
        if (handlerListener != null)
            array.put(what,handlerListener);
        handler.sendEmptyMessageDelayed(what,delayMillis);
    }

    public void sendMessageAtTime(int what, int arg1, int arg2, Object obj, long uptimeMillis,HandlerListener handlerListener) {
        if (handlerListener != null)
            array.put(what,handlerListener);
        Message msg = obtainMessage(what,arg1,arg2,obj,null);
        handler.sendMessageAtTime(msg,uptimeMillis);
    }

    public void sendMessageAtTime(int what, Bundle bundle, long uptimeMillis, HandlerListener handlerListener) {
        if (handlerListener != null)
            array.put(what,handlerListener);
        Message msg = obtainMessage(what,0,0,null,bundle);
        handler.sendMessageAtTime(msg,uptimeMillis);
    }
    private Message obtainMessage(int what,int arg1,int arg2,Object obj,Bundle data){
        Message message = Message.obtain();
        message.what = what;
        if(arg1 != 0)
            message.arg1 = arg1;
        if(arg2 != 0)
            message.arg2 = arg2;
        if(obj != null)
            message.obj = obj;
        if(data != null && !data.isEmpty())
            message.setData(data);
        return message;
    }



    @Override
    public void handleMessage(Message msg) {
        HandlerListener listener = array.get(msg.what);
        if(listener != null){
            listener.handleMessage(msg);
            array.remove(msg.what);
        }
    }

    public interface HandlerListener{
        void handleMessage(Message msg);
    }
}
