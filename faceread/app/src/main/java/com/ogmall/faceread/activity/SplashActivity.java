package com.ogmall.faceread.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.gwm.annotation.Layout;
import com.gwm.util.PermissionUtil;
import com.iflytek.sunflower.FlowerCollector;
import com.ogmall.faceread.contact.CacheContact;
import com.ogmalllarge.R;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by root on 18-5-21.
 */

@Layout(R.layout.activity_splash_layout)
public class SplashActivity extends com.gwm.base.BaseActivity{


    private String bigmirrorid;//大屏id
    private String gateid;//闸门id

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.et_in)
    EditText etIn;

    @Override
    public void onCreate(FragmentManager manager, Bundle savedInstanceState) {
        checkPsermissions();
        bigmirrorid = getCache().getAsString(CacheContact.BIGMIRRORID);
        gateid = getCache().getAsString(CacheContact.GATEID);
        if (!TextUtils.isEmpty(bigmirrorid) && !TextUtils.isEmpty(gateid)) {
            startActivity(Detect2Activity.class);
            finish();
        }
    }

    @OnClick(R.id.btn)
    public void query(View view) {
        String text = et.getText().toString().trim();
        String textIn = etIn.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            showToast("请先设置大屏ID");
            return;
        }
        if (TextUtils.isEmpty(textIn)) {
            showToast("请先设置闸门ID");
            return;
        }
        if (!textIn.equals("1") && !textIn.equals("0")) {
            showToast("设置闸门ID不对");
            return;
        }
        getCache().put(CacheContact.BIGMIRRORID, text);
        getCache().put(CacheContact.GATEID, textIn);
        startActivity(Detect2Activity.class);
        finish();
    }

    /**
     * 检查权限并授权
     */
    public void checkPsermissions() {
        permission(new PermissionUtil.RequestPermissionCallback() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(Object... permission) {

            }
        },
                Manifest.permission.CAMERA,
                Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_SETTINGS);
    }

    @Override
    public void onRequestSuccessData(Object data) {

    }

    @Override
    public void onError() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        FlowerCollector.onPageEnd("TAG");
        FlowerCollector.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FlowerCollector.onResume(this);
        FlowerCollector.onPageStart("TAG");
    }
}
