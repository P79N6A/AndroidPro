package com.gwm.base;

import android.app.Activity;
import android.os.Process;
import android.support.multidex.MultiDexApplication;

import com.gwm.android.ACache;
import com.gwm.android.ThreadManager;
import com.gwm.android.smscast.SmsListener;
import com.gwm.annotation.Contact;
import com.gwm.messagesendreceive.HermsMessageBus;
import com.gwm.messagesendreceive.HermsMessageBusService;
import com.gwm.util.MyLogger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 做了一些处理的Application的类，为了维持该框架的运行特意写的一个类
 * 集成了前端运行日志的采集
 * @author gwm
 */
public abstract class BaseAppcation extends MultiDexApplication{

	private List<String> UIS = new ArrayList<>(); //需要退出提示的activity集合
	private List<Activity> activitys = new ArrayList<>(); //界面集合
    private List<BaseBroadcastReceiver> receivers = new ArrayList<>();
	private Map<String,SmsListener> smsListener = new HashMap<>();
	private static BaseAppcation instance;
	public MyLogger Log = MyLogger.kLog();
	private ACache aCache;

	/**
	 * 获取该类的实例
	 * @return
	 */
	public static BaseAppcation getInstance(){
		return instance;
	}



	@Override
	public void onCreate(){
		super.onCreate();
		if(instance == null){
			instance = this;
		}
//		HermsMessageBus.init(this);
		aCache = ACache.get(instance);
    }

	public Map<String,SmsListener> getSmsListener(){
		return smsListener;
	}

	public void addSmsListener(String phone,SmsListener listener){
		smsListener.put(phone,listener);
	}

    public List<BaseBroadcastReceiver> getReceivers(){
        return receivers;
    }
	/**
	 * 添加Activity到自定义回退栈中
	 * @param activity
	 */
	public void addActivity(Activity activity){
		if(activitys.indexOf(activity) == -1)
			activitys.add(activity);
		else{
			activitys.remove(activity);
			activitys.add(activity);
		}
	}
	/**
	 * 退出该应用程序
	 */
	public void exit(){
		UIS.clear();
		receivers.clear();
		smsListener.clear();
        ThreadManager.getInstance().close();  //关闭所有的延时任务
		for(Activity activity : activitys){   //关闭所有Activity
			activity.finish();
		}
		Process.killProcess(Process.myPid()); //自杀
	}
	/**
	 * 从自定义回退栈中删除activity并执行finish()方法
	 * @param activity
	 */
	public void delActivity(Activity activity){
		activitys.remove(activity);
	}
	public List<String> getUIS(){
		return UIS;
	}

    public ACache getCache() {
        return aCache;
    }

	/**
	 * 获取跨进程通讯的服务集
	 * @return
	 */
	public List<Class<? extends HermsMessageBusService>> getHersMessageServices(){
		try {
			Class clazz = Class.forName(Contact.PACKAGENAME +".HermsMessageUtil");
			Method bind = clazz.getMethod("getHersMessageServices");
			return (ArrayList<Class<? extends HermsMessageBusService>>) bind.invoke(null,null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
