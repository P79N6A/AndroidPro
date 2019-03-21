package com.ogmamllpadnew.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.anyapps.sdk.AE7showDirector;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.PermissionUtis;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.contract.LoginContract;
import com.ogmamllpadnew.databinding.FgLoginItemLayoutBinding;
import com.ogmamllpadnew.presenter.LoginPresenter;
import com.ogmamllpadnew.ui.BaseActivity;
import com.shan.netlibrary.bean.LoginBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * Created by 陈俊山 on 2018-11-13.
 */

public class LoginActivity extends BaseActivity<FgLoginItemLayoutBinding, Object> implements LoginContract.View {
    private LoginPresenter presenter;

    @Override
    public int bindLayout() {
        return R.layout.fg_login_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.tvLeft.setText(getString(R.string.str_dl));
        titleBinding.btnLeft.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new LoginPresenter(this, this);
        checkPsermissions();
        logoutUser();

        initLogo();
    }

    private void initLogo() {
        String logoStr = getResources().getString(R.string.strKey);
        if (logoStr.equals("Meiliyun")){
            mBinding.logo.setImageResource(R.mipmap.ic_home_logo_meiliyun);
        }else if (logoStr.equals("Jzrz")){  //ic_home_logo_jzrz
            mBinding.logo.setImageResource(R.mipmap.ic_home_logo_jzrz);
        }else if (logoStr.equals("Sdrz")){
            mBinding.logo.setImageResource(R.mipmap.ic_home_logo_sdrz);
        }else if (logoStr.equals("Ogmamllpadnew")){
            mBinding.logo.setImageResource(R.mipmap.ic_home_logo);
        }else if (logoStr.equals("Tianqin")){
            mBinding.logo.setImageResource(R.mipmap.ic_home_logo_tianqin);
        }
    }

    /**
     * 退出客户
     */
    private void logoutUser() {
        MyApp.getInstance().setCurrentUser(null);
        EventBus.getDefault().post(new MessageEvent(MsgConstant.STOPRECORD));//停止录音
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.APPLOGIN) {
            ToastUtils.toast(getString(R.string.str_dlcg));
            LoginBean loginBean = (LoginBean) baseBean;
            MyApp.getInstance().setLoginBean(loginBean);
            SPUtils.put(SpConstant.USERNAME, username);
            SPUtils.put(SpConstant.PASSWORD, passwd);
            startActivity(MainActivity.class, null);
            finish();
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
        mBinding.btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
        }
    }

    private String username;
    private String passwd;

    /**
     * 登录
     */
    private void login() {
        username = mBinding.etUser.getText().toString().trim();
        passwd = mBinding.etPasswd.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            JitterUtils.start(mBinding.etUser);
            return;
        }
        if (TextUtils.isEmpty(passwd)) {
            JitterUtils.start(mBinding.etPasswd);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("contactPhone", username);
        map.put("password", passwd);
        presenter.applogin(map);
    }

    @Override
    protected void initData() {
        super.initData();
        String name = (String) SPUtils.get(SpConstant.USERNAME, "");
        String passwd = (String) SPUtils.get(SpConstant.PASSWORD, "");
        mBinding.etUser.setText(name);
        mBinding.etPasswd.setText(passwd);
    }

    /**
     * 检查权限并授权
     */
    public void checkPsermissions() {
        String[] PERMISSIONS = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };
        if (PermissionUtis.checkPermissions(this, PERMISSIONS)) {
            PermissionUtis.requestPermissions(this, PermissionUtis.REQUESTCODE, PERMISSIONS);
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
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AE7showDirector.end();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
         if (msgEvent.getType() == MsgConstant.SYSTEMERROR) {
            startActivity(SystemErrorActivity.class,null);
        }
    }
}