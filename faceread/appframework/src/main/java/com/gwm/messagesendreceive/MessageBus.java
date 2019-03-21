package com.gwm.messagesendreceive;

import android.text.TextUtils;

import com.gwm.android.Handler;
import com.gwm.android.ThreadManager;
import com.gwm.annotation.Subscrition;
import com.gwm.base.BaseRunnable;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Administrator on 2018/4/4 0004.
 * 消息站收发消息
 */

public class MessageBus {
    private static MessageBus messageBus = new MessageBus();

    private HashMap<String,Method> methods = new HashMap<>();
    private HashMap<String,Object> objs = new HashMap<>();

    private MessageBus(){}

    public static synchronized MessageBus getBus(){
        return messageBus;
    }

    /**
     * 注册消息监听
     */
    public void register(Object obj){
        findSubscritionMethod(obj);
    }



    /**
     * 取消消息监听
     */
    public void unregister(Object obj){
        findSubscritionRemove(obj);
    }

    /**
     * 发送消息，自行判断群发还是单发
     * @param obj
     */
    public void postMsg(MessageBusMessage obj){
        if (!TextUtils.isEmpty(obj.getAction())) {
            post(obj);
        }else {
            postAll(obj);
        }
    }

    /**
     * 发送消息  一对一
     * @param obj
     */
    public void post(final MessageBusMessage obj){
        String action = obj.getAction();
        if (!TextUtils.isEmpty(obj.getAction())) {
            final Method method = methods.get(action);
            final Object object = objs.get(action);
            exectuteMsg(obj, method, object);
        }
    }

    /**
     * 发送消息
     * @param obj   消息内容
     * @param method  目的方法
     * @param object  目的类
     */
    private void exectuteMsg(final MessageBusMessage obj, final Method method, final Object object) {
        Subscrition subscrition = method.getAnnotation(Subscrition.class);
        Subscrition.ThreadMode threadMode = subscrition.threadMode();
        if (threadMode == Subscrition.ThreadMode.MAIN){  //主线程
            Handler.getHandler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        method.invoke(object,obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else if (threadMode == Subscrition.ThreadMode.CHILD){  //子线程
            ThreadManager.getInstance().run(new BaseRunnable() {
                @Override
                public void run() {
                    super.run();
                    try {
                        method.invoke(object,obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }else if (threadMode == Subscrition.ThreadMode.CURRENT){ //当前线程
            try {
                method.invoke(object,obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 一对多  群发
     * @param obj
     */
    public void postAll(final MessageBusMessage obj){
        if (TextUtils.isEmpty(obj.getAction())) {
            Set<String> keySet = methods.keySet();
            for (String key : keySet) {
                Object object = objs.get(key);
                Method method = methods.get(key);
                exectuteMsg(obj, method, object);
            }
        }
    }

    private void findSubscritionMethod(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        for (int i = 0 ; i < methods.length ; i++){
            if (methods[i].isAnnotationPresent(Subscrition.class)){
                Subscrition subscrition = methods[i].getAnnotation(Subscrition.class);
                String action = TextUtils.isEmpty(subscrition.action()) ? obj.getClass().getSimpleName() + methods[i].getName():subscrition.action();
                this.methods.put(action,methods[i]);
                this.objs.put(action,obj);

            }
        }
    }

    private void findSubscritionRemove(Object obj) {
        Method[] methods = obj.getClass().getMethods();
        for (int i = 0 ; i < methods.length ; i++){
            if (methods[i].isAnnotationPresent(Subscrition.class)){
                Subscrition subscrition = methods[i].getAnnotation(Subscrition.class);
                String action = TextUtils.isEmpty(subscrition.action()) ? obj.getClass().getSimpleName() + methods[i].getName():subscrition.action();
                this.methods.remove(action);
                this.objs.remove(action);
            }
        }
    }

}
