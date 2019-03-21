package com.ogmallpad.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.contract.LoginContract;
import com.ogmallpad.databinding.FgLoginLayoutBinding;
import com.ogmallpad.presenter.LoginPresenter;
import com.ogmallpad.ui.BaseActivity;
import com.shan.netlibrary.bean.UserloginBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * Created by chenjunshan on 2018-07-03.
 */

public class LoginActivity extends BaseActivity<FgLoginLayoutBinding, Object> implements LoginContract.View {
    public static final String Splash = "Splash";
    private LoginPresenter presenter;
    private boolean isSplsh;

    @Override
    public int bindLayout() {
        return R.layout.fg_login_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.tvLeft.setText(getString(R.string.str_dl));
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        isSplsh = getIntent().getBooleanExtra(Splash,false);
        presenter = new LoginPresenter(this, this);
        isSlidingClose = false;
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.USERLOGIN) {
            ToastUtils.toast(getString(R.string.str_dlcg));
            UserloginBean bean = (UserloginBean) baseBean;
            SPUtils.put(SpConstant.USERNAME, name);
            SPUtils.put(SpConstant.PASSWORD, passwd);
            MyApp.getInstance().setLoginBean(bean);
            Bundle bundle = new Bundle();
            bundle.putInt(HomeActivity.SHOW_CURRENT,0);
            startActivity(HomeActivity.class,null);
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
        mBinding.llParent.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.ll_parent:
                SoftKeyBoardUtils.closeKeybord(mBinding.etName, this);
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        String name = (String) SPUtils.get(SpConstant.USERNAME, "");
        String passwd = (String) SPUtils.get(SpConstant.PASSWORD, "");
        mBinding.etName.setText(name);
        mBinding.etPasswd.setText(passwd);
    }

    private String name;
    private String passwd;

    /**
     * 登录
     */
    private void login() {
        name = mBinding.etName.getText().toString().trim();
        passwd = mBinding.etPasswd.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            JitterUtils.start(mBinding.etName);
            return;
        }
        if (TextUtils.isEmpty(passwd)) {
            JitterUtils.start(mBinding.etPasswd);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("userName", name);
        map.put("password", passwd);
        presenter.userlogin(map);
    }
}
