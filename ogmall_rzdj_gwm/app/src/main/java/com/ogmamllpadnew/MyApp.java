package com.ogmamllpadnew;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.anyapps.sdk.AAConfig;
import com.anyapps.sdk.AE7showDirector;
import com.anyapps.sdk.IAADelegate;
import com.junshan.pub.App;
import com.junshan.pub.utils.LogUtils;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.bean.DaoMaster;
import com.ogmamllpadnew.bean.DaoSession;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.ui.activity.LoginActivity;
import com.ogmamllpadnew.ui.activity.MainActivity;
import com.ogmamllpadnew.utils.BuglyUtils;
import com.shan.netlibrary.bean.AppaccountInfoBean;
import com.shan.netlibrary.bean.HandbagselectLayoutBean;
import com.shan.netlibrary.bean.LoginBean;
import com.shan.netlibrary.bean.UserselectCustomerBean;

/**
 * Created by chenjunshan on 2017/3/9.
 */
public class MyApp extends App {
    private static MyApp instance;
    private LoginBean loginBean;
    private AppaccountInfoBean infoBean;//用户信息
    private boolean isLogin;
    private UserselectCustomerBean.DataBean currentUser;//当前客户id
    private static DaoSession daoSession;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = (MyApp) getApplicationContext();
        LogUtils.isLog = true;//是否打印日志
        init();
    }

    /**
     * MyApp初始化操作
     */
    private void init() {
        setupDatabase();
        //initLeakCanary();
        //初始化bugly
        BuglyUtils.init();
        initYqxSDK();
    }

    /**
     * 安装LeakCanary
     */
    /*private void initLeakCanary() {
        //初始化LeakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }*/
    public LoginBean getLoginBean() {
        if (loginBean == null)
            loginBean = new LoginBean();
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        if (loginBean == null) {
            loginBean = new LoginBean();
        }
        if (TextUtils.isEmpty(loginBean.getData().getToken())) {
            SPUtils.put(SpConstant.TOKEN, "");//保存token
            setLogin(false);
        } else {
            SPUtils.put(SpConstant.TOKEN, loginBean.getData().getToken());//保存token
            setLogin(true);
        }
        this.loginBean = loginBean;
    }

    public AppaccountInfoBean getInfoBean() {
        if (infoBean == null)
            infoBean = new AppaccountInfoBean();
        return infoBean;
    }

    public void setInfoBean(AppaccountInfoBean infoBean) {
        this.infoBean = infoBean;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public UserselectCustomerBean.DataBean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserselectCustomerBean.DataBean currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * 是否存在客户在线
     */
    public boolean isHasUser() {
        return currentUser == null ? false : true;
    }

    /**
     * ################################初始化一起秀SDK START#########################################
     */
    private void initYqxSDK() {
        // 初始化 - 生命周期内 只调用一次
        AE7showDirector.init(new ADelegateSample());
    }


    public class ADelegateSample implements IAADelegate {
        @Override
        public void onPreStart() {
            // 开始工具之前
            Log.d("ADelegateSample", "onPreStart");
        }

        @Override
        public void onFinishStart() {
            // 初始化工具完成
            Log.d("ADelegateSample", "onFinishStart");
            AE7showDirector.sendMessage(1002, "传输数据测试");
        }

        @Override
        public void onEnd() {
            // 结束工具
            Log.d("ADelegateSample", "onEnd");
        }

        @Override
        public void onRecvMessage(int nMessageID, String message) {
            // 接受自定义数据
            Log.d("ADelegateSample", "onRecvMessage nMessageID = " + nMessageID + " message = " + message);

            // 假设 1001 为关闭3D工具协议号
            if (1001 == nMessageID) {
                Intent intent = new Intent(AE7showDirector.get3DActivity(), MainActivity.class);
                AE7showDirector.get3DActivity().startActivity(intent);
            } else if (1002 == nMessageID) {
                logoutYqx();
                ToastUtils.toast("你的帐号在其他设备上登录，请重新登录");
                Intent intent = new Intent(AE7showDirector.get3DActivity(), LoginActivity.class);
                AE7showDirector.get3DActivity().startActivity(intent);
            }
        }
    }

    /**
     * 退出清空数据
     */
    public void logoutYqx() {
        c = null;
        mYqxToken = null;
    }

    private String mYqxToken;//一起秀token
    private int mYqxUserid;//一起秀Userid

    public int getmYqxUserid() {
        return mYqxUserid;
    }

    public void setmYqxUserid(int mYqxUserid) {
        this.mYqxUserid = mYqxUserid;
    }

    public String getmYqxToken() {
        return mYqxToken;
    }

    public void setmYqxToken(String mYqxToken) {
        this.mYqxToken = mYqxToken;
    }


    private AAConfig c;

    public AAConfig getAAConfig(Activity activity) {
        if (c == null) {
            // 当前环境配置
            c = new AAConfig();
            // 主活
            c.mActivity = activity;
            // 是否是调试模式 ,在正式环境这个参数务必为false
            c.bDebug = false;
            // 是否支持 Bugly Share GT 等三个插件 , 默认为 false , 如果要支持 请查看SDK文档
            c.bPluginOpen = false;
            // 账户铭牌 , 通过账号登陆绑定后可获得
            c.strToken = mYqxToken;
            c.user_id = mYqxUserid;
            c.designer = 1;//0-否 1-是3D设计师
        }
        return c;
    }

    /**
     * ################################初始化一起秀SDK END#########################################
     */

    //布局数据
    private HandbagselectLayoutBean layoutBean;

    public HandbagselectLayoutBean getLayoutBean() {
        return layoutBean;
    }

    public void setLayoutBean(HandbagselectLayoutBean layoutBean) {
        this.layoutBean = layoutBean;
    }

    private int tabPos = 0;//拎包入住左边tab选中
    private int childPos = 0;//拎包入住左边tab item选中

    public int getTabPos() {
        return tabPos;
    }

    public void setTabPos(int tabPos) {
        this.tabPos = tabPos;
    }

    public int getChildPos() {
        return childPos;
    }

    public void setChildPos(int childPos) {
        this.childPos = childPos;
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库ogmallpad.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "ogmallpadnew.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
        LogUtils.i("GreenDao初始化成功！");
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public long createTime;//创建录音时间

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
