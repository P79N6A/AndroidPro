package com.ogmamllpadnew.ui.activity;

import android.app.ActivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.databinding.DialogXitongBinding;
import com.ogmamllpadnew.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/12/23.
 */

public class SystemErrorActivity extends BaseActivity<DialogXitongBinding,Object> {
    @Override
    public int bindLayout() {
        return R.layout.dialog_xitong;
    }

    @Override
    public void onLeftClick() {
        EventBus.getDefault().post(new MessageEvent(MsgConstant.FINISHACTIVITY));
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.tvLeft.setText("系统维护中");
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        mBinding.tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeftClick();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            EventBus.getDefault().post(new MessageEvent(MsgConstant.FINISHACTIVITY));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
