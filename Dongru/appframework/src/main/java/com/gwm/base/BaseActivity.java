package com.gwm.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gwm.R;
import com.gwm.activity.CommonFragmentActivity;
import com.gwm.android.ACache;
import com.gwm.android.Handler;
import com.gwm.android.ThreadManager;
import com.gwm.android.ViewBindUtils;
import com.gwm.android.smscast.SmsListener;
import com.gwm.haware.fingerprint.FingerprintUtil;
import com.gwm.messagesendreceive.MessageBus;
import com.gwm.util.AppUtils;
import com.gwm.util.MyLogger;
import com.gwm.util.PermissionUtil;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 该类加入以下功能：
 * 1."再按一次退出程序"提示(只需要调用addFirstToast()方法把activity添加到退出通知提示的集合即可)
 * 2.带有MVC，MVP设计思想
 * 3.会自动根据需要是否隐藏系统键盘
 * 4.封装了Thread+Handler消息处理机制
 * 5.实现了短信消息监听（可以用来做短信验证码的自动填充）
 * 6.支持注解ButterKnife的相关用法
 * 7.图片加载框架
 * 8.支持MessageBus消息站
 * 9.支持BindIntent、Layout注解
 * 10.加入了动态权限申请机制
 * 11.全面支持ACache存储
 * 12.指纹识别
 */
public abstract class BaseActivity extends AppCompatActivity implements Handler.HandlerListener,ViewModelCallback{
	private List<String> activities;
	private AppUtils app_util;
	private FragmentManager manager;
	protected MyLogger Log = MyLogger.kLog();
	private long exitTime = 0;
    private InputMethodManager imm;

    protected BaseCommon common;

	private View view;

	private PermissionUtil permissionUtil;
	@Override
	public final void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		manager = getSupportFragmentManager();
		app_util = AppUtils.getInstance(getApplicationContext());

        if (getApplication() instanceof BaseAppcation){
            activities = ((BaseAppcation) getApplication()).getUIS();
            ((BaseAppcation) getApplication()).addActivity(this);
        }
		MessageBus.getBus().register(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        common = BaseCommon.getCommon(getApplicationContext());
		common.bindIntent(this);
		permissionUtil = PermissionUtil.getInstance();
		setContentView();
		common.bindCacheParam(this);
		ButterKnife.bind(this,getView());
		onCreate(manager, savedInstanceState);
    }
	protected void setContentView(){
		ViewBindUtils.bindActivity(this);
    }
    /**
	 * 将Activity添加到退出通知
	 */
	public void addFirstToast() {
		activities.add(getActivityName());
	}

    public View getView(){
    	if (view == null){
			view = getWindow().getDecorView();
		}
        return view;
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if((System.currentTimeMillis() - exitTime) > 2000 && (activities.indexOf(getActivityName()) != -1)){
				Toast.makeText(getApplicationContext(), R.string.app_add_first,Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else if ((activities.indexOf(getActivityName()) != -1)) {
				if(getApplication() instanceof BaseAppcation){
					((BaseAppcation)getApplication()).exit();
				}
			}
		}
		if (keyCode == KeyEvent.KEYCODE_BACK && (activities.indexOf(getActivityName()) != -1)) {
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK){
			if(getApplication() instanceof BaseAppcation){
				finish();
			}
			return true;
		}
		return onKeyDownMethod(keyCode, event);
	}
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		try {
			super.onConfigurationChanged(newConfig);
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				landscape(newConfig);
			} else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
				portrait(newConfig);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 运行耗时任务
	 * @param runnable
	 */
	public void runRunable(BaseRunnable runnable){
		ThreadManager.getInstance().run(runnable);
	}


	/**
	 * 为当前Activity添加短信消息监听
	 * @param phone 需要监听的号码
	 * @param listener 短信监听器
	 */
	public void regSMSCastRevier(String phone,SmsListener listener){
		BaseAppcation.getInstance().addSmsListener(phone,listener);
	}

	/**
	 * 横竖屏切换时切换到竖屏时调用
	 * @param newConfig
	 */
	protected void portrait(Configuration newConfig) {

	}

    /**
     * 横竖屏切换时切换到横屏时调用
     * @param newConfig
     */
	protected void landscape(Configuration newConfig) {

	}

	/**
	 * 无需重写onKeyDown()方法,需要时应该重写该方法 已经屏蔽了返回键，如需处理返回键，请重写onKeyDown()方法
	 * @param keyCode
	 * @param event
	 * @return
	 */
	protected boolean onKeyDownMethod(int keyCode, KeyEvent event){
		//如果启用了BasePage类，下面两行代码需要复制在activity的onKeyDown()方法中
//		BaseFragment frag = (BaseFragment) manager.findFragmentByTag("");
//		return frag.getPager().onKeyDownMethod(keyCode,event);

		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 如果启动了BasePager，请将如下代码迁移到BasePager所依附的Activity中
	 * 		但需在此之前调用setCurrentFragment()方法才会有效
	 */
//	public boolean onKeyDownMethod(int keyCode, KeyEvent event){ 
//		return current_Fragment.getCurrentPage().onKeyDownMethod(keyCode, event);
//	}
	/**
	 * 获取当前正在运行的Activity
	 * @return
	 */
	private String getActivityName() {
		return app_util.getActivityName();
	}
	/**
	 *
	 * 传递数据到指定的Fragment中，会调用对应的Fragment的setArguments()方法传递数据。Fragment需要重写该方法接收数据
	 * @param tag  Fragment的标识
	 * @param data 要传递的数据
	 */
	public void addToFragmentData(String tag,Bundle data){
		BaseFragment frag = getFragment(tag);
		addToFragmentData(frag, data);
	}

	public void addToFragmentData(BaseFragment frag,Bundle data){
		frag.setArguments(data);
	}
	/**
	 * 获取当前应用的版本号
	 * @return
	 * @throws Exception
	 */
	public double getVersionName() {
		return Double.parseDouble(app_util.getVersionName());
	}

    public ActivityManager getActivityManager(){
        return (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    }
	/**
	 * 通过Tag找Fragment
	 * @param tag
	 * @return
	 */
	public BaseFragment getFragment(String tag){
		return (BaseFragment) manager.findFragmentByTag(tag);
	}

	/**
	 * 启动一个Activity
	 * @param clz Activity实例的Class对象
	 */
	public void startActivity(Class<? extends BaseActivity> clz) {
		Intent intent = new Intent(this, clz);
		startActivity(intent);
	}
	/**
	 * 启动一个Activity，带有启动动画
	 * @param clz Activity实例的Class对象
	 */
	public void startActivity(Class<? extends BaseActivity> clz, int enterAnim,
			int exitAnim) {
		Intent intent = new Intent(this, clz);
		startActivity(intent, enterAnim, exitAnim);
	}
	/**
	 * 启动一个Activity，带有启动动画
	 * @param action Activity实例的action动作
	 */

	public void startActivity(String action) {
		Intent intent = new Intent(action);
		startActivity(intent);

	}

	/**
	 * activity之间的切换，默认渐入渐出动画
	 */
	@Override
	public void startActivity(Intent intent){
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		super.startActivity(intent);
		//overridePendingTransition(R.anim.fade, R.anim.hold);
		//overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit); //缩放动画
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
	}

	public void startActivityForResult(String action, int requestCode) {
		Intent intent = new Intent(action);
		startActivityForResult(intent, requestCode);
	}


	public void startActivityForResult(Class<? extends BaseActivity> clazz, int requestCode) {
		Intent intent = new Intent(this,clazz);
		startActivityForResult(intent, requestCode);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		permissionUtil.onRequestPermissionsResult(requestCode,permissions,grantResults);
	}

	protected void showToast(String text){
		com.gwm.android.Toast.makeText(getApplicationContext(), text, com.gwm.android.Toast.LENGTH_SHORT).show();
	}
	protected void showLongToast(String text){
		com.gwm.android.Toast.makeText(getApplicationContext(), text, com.gwm.android.Toast.LENGTH_LONG).show();
	}


	/**
	 * 优化过后的获取Context对象的方法
	 */
	@Override
	public Context getApplicationContext() {
		return BaseAppcation.getInstance().getApplicationContext();
	}
	/**
	 * 自定义activity之间的切换动画
	 * @param intent
	 * @param enterAnim
	 *                 进入时动画
	 * @param exitAnim
	 *                 出去时动画
	 */
	public void startActivity(Intent intent, int enterAnim, int exitAnim) {
		super.startActivity(intent);
		overridePendingTransition(enterAnim, exitAnim);
		BaseAppcation.getInstance().addActivity(this);
	}

	@Override
	protected void onDestroy() {
		MessageBus.getBus().unregister(this);
		BaseAppcation.getInstance().delActivity(this);
		super.onDestroy();
	}

	/**
	 * 在当前Activity上显示对话框
	 * @param dialog
	 */
	protected void showDialog(BaseDialog dialog){
		manager = getSupportFragmentManager();
		dialog.show(manager,dialog.getClass().getSimpleName());
	}
	/**
	 * 退出当前应用程序
	 */
	public void exit(){
		BaseAppcation.getInstance().exit();
	}

	/**
	 * 动态权限申请
	 * @param callback 回调
	 * @param permission 权限列表
	 */
	protected void permission(PermissionUtil.RequestPermissionCallback callback,String... permission){
		permissionUtil.setCallback(callback);
		permissionUtil.checkAndRequestPermissions(this,permission);
	}
	/**
	 * Activity的生命周期onCreate()方法
	 * @param manager
	 *                 管理Fragment的对象，如果使用Fragment可以跟本框架做到无缝的结合
	 * @param savedInstanceState
	 *                 Activity的状态保存
	 */
	public abstract void onCreate(FragmentManager manager, Bundle savedInstanceState);
	/**
	 * Fragment之间的切换方法，加载Fragment方法.<br />
	 * 重写该方法时要注意的问题：
	 * <ol>
	 * <li>该方法尽量放在activity中。避免Fragment的嵌套出现id资源找不到的问题,最好Fragment里面不要在嵌套Fragment.</li>
	 * <li>在加载之前需判断是否已经加载了，如果是 请先移除，在加载。避免报该Fragment已经加载了的问题.</li>
	 * <li>该方法不能在activity的生命周期中使用。避免回退栈的问题.<br/>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 * (原因：addToBackStack()方法不能在activity的生命周期中使用)</li>
	 * </ol>
	 * @param frag 
	 * 					中间容器显示的Fragment
	 * @param tag
	 *                 中间容器的标识
	 */
	public void jumpFragment(BaseFragment frag,String tag){
	}
	/**
	 * Fragment之间的切换方法，加载Fragment方法
	 * @param frag
	 * @param tag
	 * @param isHiddenNav  是否隐藏导航栏，至于如何实现隐藏需开发者自己考虑
	 */
	public void jumpFragment(BaseFragment frag, String tag,boolean isHiddenNav){

	}

    @Override
    public void handleMessage(Message msg) {

    }

    public ACache getCache(){
		return BaseAppcation.getInstance().getCache();
	}

    /**
     * 启用Android 指纹识别
     */
	public void fingerprint(){
        FingerprintUtil.getInstance(this).callFingerPrint(new FingerprintUtil.OnCallBackListenr() {
            @Override
            public void onSupportFailed() {

            }

            @Override
            public void onInsecurity() {

            }

            @Override
            public void onEnrollFailed() {

            }

            @Override
            public void onAuthenticationStart() {

            }

            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {

            }

            @Override
            public void onAuthenticationFailed() {

            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {

            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {

            }
        });
	}

	public void startFragment(Class<? extends BaseFragment> fragment,Bundle bundle){
		Intent intent = new Intent(getApplicationContext(), CommonFragmentActivity.class);
		intent.putExtras(bundle);
		intent.putExtra(CommonFragmentActivity.FRAGMENT_CLASS, fragment);
		if (bundle != null && !bundle.isEmpty()){
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

}