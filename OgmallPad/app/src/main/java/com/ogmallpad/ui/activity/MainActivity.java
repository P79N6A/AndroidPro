package com.ogmallpad.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.anyapps.sdk.AAConfig;
import com.anyapps.sdk.AE7showDirector;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.listener.TitleBarListener;
import com.junshan.pub.manager.TabManager;
import com.junshan.pub.utils.AppUtils;
import com.junshan.pub.utils.MD5Utils;
import com.junshan.pub.utils.PermissionUtis;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.constant.TabConstant;
import com.ogmallpad.contract.MainContract;
import com.ogmallpad.databinding.FgMainLayoutBinding;
import com.ogmallpad.presenter.MainPresenter;
import com.ogmallpad.service.NetService;
import com.ogmallpad.ui.BaseActivity;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页
 * Created by chenjunshan on 2018-03-09.
 */

public class MainActivity extends BaseActivity<FgMainLayoutBinding, Object> implements MainContract.View, TitleBarListener {
    private MainPresenter presenter;
    private TabManager tabManager;
    private int position = 0;//底部Tab的位置
    private String name;

    @Override
    public int bindLayout() {
        return R.layout.fg_main_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        /*CrashBean crashBean = null;
        crashBean.setTest(null);*/

        presenter = new MainPresenter(this, this, mBinding);
        isSlidingClose = false;
        intiStatusBar();
        iniTab();
        checkPsermissions();
        AppUtils.getDeviceInfo();
        presenter.getLastVersion();
    }

    private void iniTab() {
        tabManager = new TabManager(this, mBinding.tabhost, TabConstant.MAIN_FRAGMENT, TabConstant.MAIN_IAMGEVIEW, TabConstant.MAIN_TEXTVIEW, getSupportFragmentManager());
        tabManager.initTab(R.id.fl_content);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.CUSTOMERLOGOUT) {
            ToastUtils.toast(getString(R.string.str_tccg));
            presenter.dismiss();
            MyApp.getInstance().setCurrentCustomerName(null);
            presenter.refreshTitleData();
            //停止录音
            EventBus.getDefault().post(new MessageEvent(MsgConstant.STOPRECORD));
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.ivMenu.setOnClickListener(this);
        mBinding.ivLogout.setOnClickListener(this);
        mBinding.tvLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_menu:
                presenter.openLeftLayout();
                break;
            case R.id.iv_logout:
            case R.id.tv_logout:
                presenter.showDialog();
                break;
        }
    }

    /**
     * 初始化状态栏
     */
    private void intiStatusBar() {
        if (AppUtils.getSystemVersion() < 19) {
            mBinding.statusBar.setVisibility(View.GONE);
        } else {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mBinding.statusBar.getLayoutParams();
            linearParams.height = AppUtils.getStatusBarHeight();
            mBinding.statusBar.setLayoutParams(linearParams);
        }
    }

    @Override
    public void setTitleBarTitle(int position) {
        this.position = position;
        setTitleColors(position);
    }

    @Override
    public void isLogin() {

    }

    @Override
    public void tabSelect(int position) {
        MyApp.getInstance().currentTabHost = position;
        tabManager.setCurrentTab(position);
        setTitleColors(position);
        presenter.setLeftTabStatus(position);
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        switch (msgEvent.getType()) {
            case MsgConstant.TABHOST:
                int pos = (int) msgEvent.getBean();
                tabSelect(pos);
                break;
            case MsgConstant.LOGOUT:
                //退出登录
                MyApp.getInstance().setLoginBean(null);
                MyApp.getInstance().currentTabHost = 0;
                SPUtils.put(SpConstant.PASSWORD, "");//清除登录密码
                startActivity(LoginActivity.class, null);
                break;
            case MsgConstant.LOGINSUCCESS:
                loginBean = MyApp.getInstance().getLoginBean();
                bindData();
                break;
        }
    }

    private void setTitleColors(int pos) {
        switch (pos) {
            case 0:
                mBinding.tvHome.setTextColor(getResources().getColor(R.color.color_c73c3a));
                mBinding.tvName.setTextColor(getResources().getColor(R.color.color_c73c3a));
                mBinding.tvLogout.setTextColor(getResources().getColor(R.color.color_c73c3a));
                mBinding.statusBar.setBackgroundResource(R.color.color_b3ffffff);
                mBinding.llTitle.setBackgroundResource(R.color.color_b3ffffff);
                mBinding.ivMenu.setImageResource(R.mipmap.ic_menu_red);
                mBinding.ivLogout.setImageResource(R.mipmap.ic_arrows__right_red);
                mBinding.tvHome.setText(getString(R.string.str_sy));
                presenter.refreshTitleData();
                break;
            case 1:
                mBinding.tvHome.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvName.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvLogout.setTextColor(getResources().getColor(R.color.white));
                mBinding.statusBar.setBackgroundResource(R.color.color_c73c3a);
                mBinding.llTitle.setBackgroundResource(R.color.color_c73c3a);
                mBinding.ivMenu.setImageResource(R.mipmap.ic_menu_white);
                mBinding.ivLogout.setImageResource(R.mipmap.ic_arrows_right_white);
                mBinding.tvHome.setText(getString(R.string.str_ppzs));
                presenter.refreshTitleData();
                break;
            case 2:
                mBinding.tvHome.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvName.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvLogout.setTextColor(getResources().getColor(R.color.white));
                mBinding.statusBar.setBackgroundResource(R.color.color_c73c3a);
                mBinding.llTitle.setBackgroundResource(R.color.color_c73c3a);
                mBinding.ivMenu.setImageResource(R.mipmap.ic_menu_white);
                mBinding.ivLogout.setImageResource(R.mipmap.ic_arrows_right_white);
                mBinding.tvHome.setText(getString(R.string.str_fazs));
                presenter.refreshTitleData();
                break;
            case 3:
                mBinding.tvHome.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvName.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvLogout.setTextColor(getResources().getColor(R.color.white));
                mBinding.statusBar.setBackgroundResource(R.color.color_c73c3a);
                mBinding.llTitle.setBackgroundResource(R.color.color_c73c3a);
                mBinding.ivMenu.setImageResource(R.mipmap.ic_menu_white);
                mBinding.ivLogout.setImageResource(R.mipmap.ic_arrows_right_white);
                mBinding.tvHome.setText(getString(R.string.str_grzx));
                presenter.refreshTitleData();
                break;
            case 4:
                mBinding.tvHome.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvName.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvLogout.setTextColor(getResources().getColor(R.color.white));
                mBinding.statusBar.setBackgroundResource(R.color.color_c73c3a);
                mBinding.llTitle.setBackgroundResource(R.color.color_c73c3a);
                mBinding.ivMenu.setImageResource(R.mipmap.ic_menu_white);
                mBinding.ivLogout.setImageResource(R.mipmap.ic_arrows_right_white);
                mBinding.tvHome.setText(getString(R.string.str_spzs));
                presenter.refreshTitleData();
                break;
            case 5:
                mBinding.tvHome.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvName.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvLogout.setTextColor(getResources().getColor(R.color.white));
                mBinding.statusBar.setBackgroundResource(R.color.color_c73c3a);
                mBinding.llTitle.setBackgroundResource(R.color.color_c73c3a);
                mBinding.ivMenu.setImageResource(R.mipmap.ic_menu_white);
                mBinding.ivLogout.setImageResource(R.mipmap.ic_arrows_right_white);
                mBinding.tvHome.setText(getString(R.string.str_bktj));
                mBinding.tvName.setVisibility(View.VISIBLE);
                presenter.refreshTitleData();
                break;
            case 6:
                mBinding.tvHome.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvName.setTextColor(getResources().getColor(R.color.white));
                mBinding.tvLogout.setTextColor(getResources().getColor(R.color.white));
                mBinding.statusBar.setBackgroundResource(R.color.color_c73c3a);
                mBinding.llTitle.setBackgroundResource(R.color.color_c73c3a);
                mBinding.ivMenu.setImageResource(R.mipmap.ic_menu_white);
                mBinding.ivLogout.setImageResource(R.mipmap.ic_arrows_right_white);
                mBinding.tvHome.setText(getString(R.string.str_wdfa));
                presenter.refreshTitleData();
                break;
            default:

                break;
        }
    }

    private long lastPressTime = 0L;//最后一次点击时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mBinding.leftDrawer.isDrawerOpen(mBinding.llMenu)) {
                mBinding.leftDrawer.closeDrawer(mBinding.llMenu);
                return true;
            }
            if (position != 0 && tabManager.geteCurrentTab() != 0) {
                tabSelect(0);
                return true;
            }

            long currentThreadTimeMillis = System.currentTimeMillis();
            if (currentThreadTimeMillis - lastPressTime > 2000) {
                lastPressTime = currentThreadTimeMillis;
                ToastUtils.toast(getResources().getString(R.string.str_zayc));
                return true;
            }
            MyApp.getInstance().currentTabHost = 0;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onResume() {
        super.onResume();
        tabManager.setCurrentTab(MyApp.getInstance().currentTabHost);
        presenter.setLeftTabStatus(MyApp.getInstance().currentTabHost);
        presenter.refreshTitleData();
        bindData();
    }

    /**
     * 检查权限并授权
     */
    public void checkPsermissions() {
        String[] PERMISSIONS = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };
        if (PermissionUtis.checkPermissions(this, PERMISSIONS)) {
            PermissionUtis.requestPermissions(this, PermissionUtis.REQUESTCODE, PERMISSIONS);
        } else {
            startNetService();
        }
    }

    /**
     * 授权回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtis.REQUESTCODE:
                if (!PermissionUtis.hasAllPermissionsGranted(grantResults)) {
                    ToastUtils.toast(getString(R.string.str_sqsb));
                } else {
                    startNetService();
                }
                break;
        }
    }

    /**
     * 开启录音服务
     */
    private void startNetService() {
        //开启服务
        Intent intent = new Intent(this, NetService.class);
        startService(intent);
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        //用户类型
        /**Normal(0,2.2D,"普通用户"),
         Manager(2,1.0D,"管理员"),
         //vip设计师1.15普通设计师为2.2
         Designer(4,1.15D,"设计师"),
         City(5,1D,"运营商"),
         Factory(6,2.2D,"工厂"),
         DecorateCompany(7,1.1D,"装修公司"),
         Seed(8,2.2D,"种子用户"),
         Test(9,2.2D,"测试用户"),
         Employee(10,2.2D,"欧工员工"),
         OgSchool(11,2.2D,"欧工软装学院");
         */
        //是否为设计师
        if (loginBean.getData().getUserType().equals("Designer")) {
            mBinding.tvMenuPlan.setText(getString(R.string.str_wdfa));
        } else {
            mBinding.tvMenuPlan.setText(getString(R.string.str_fa));
        }
    }





}
