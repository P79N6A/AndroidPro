package com.gwm.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gwm.R;
import com.gwm.base.BaseFragment;
import com.gwm.base.BaseTitleActivity;

/**
 * Created by Administrator on 2019/3/4.
 */

public class CommonFragmentActivity extends BaseTitleActivity {
    public static final String FRAGMENT_CLASS = "FRAGMENT_CLASS";

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_common_fragment);
    }

    @Override
    public void onCreate(FragmentManager manager, Bundle savedInstanceState) {
        Class<? extends BaseFragment> fragment = (Class<? extends BaseFragment>) getIntent().getSerializableExtra(FRAGMENT_CLASS);
        try {
            BaseFragment baseFragment = fragment.newInstance();
            jumpFragment(baseFragment,fragment.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void jumpFragment(BaseFragment frag, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_content,frag);
        transaction.commit();
    }

    @Override
    public void onRequestSuccessData(Object data) {

    }
}
