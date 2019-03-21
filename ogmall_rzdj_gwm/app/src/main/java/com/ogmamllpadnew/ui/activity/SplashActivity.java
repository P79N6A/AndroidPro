package com.ogmamllpadnew.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.junshan.pub.utils.SPUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.databinding.SplashLayoutBinding;
import com.ogmamllpadnew.ui.BaseActivity;
import com.ogmamllpadnew.ui.fragment.ScomboDateilFragment;

/**
 * Created by root on 18-11-19.
 */

public class SplashActivity extends BaseActivity<SplashLayoutBinding, Object> {
    @Override
    public int bindLayout() {
        return R.layout.splash_layout;
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        String name = (String) SPUtils.get(SpConstant.USERNAME, "");
        String passwd = (String) SPUtils.get(SpConstant.PASSWORD, "");
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(passwd)) {
            startActivity(MainActivity.class, null);
        } else {
            startActivity(LoginActivity.class, null);
        }
        finish();
//        Bundle bundle = new Bundle();
//        bundle.putInt(Constants.ID,23);
//        startFragment(ScomboDateilFragment.class,bundle);

//        String brand = Build.BRAND;//系统定制商
//        String model = Build.MODEL;//版本
//        if (brand.equals("VOYO") && (model.equals("HR1083") || model.equals("i8-Max"))) {
//            String name = (String) SPUtils.get(SpConstant.USERNAME, "");
//            String passwd = (String) SPUtils.get(SpConstant.PASSWORD, "");
//            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(passwd)) {
//                startActivity(MainActivity.class, null);
//            } else {
//                startActivity(LoginActivity.class, null);
//            }
//            finish();
//        } else {
//            ToastUtils.toast("安装失败");
//            finish();
//        }
    }
}
