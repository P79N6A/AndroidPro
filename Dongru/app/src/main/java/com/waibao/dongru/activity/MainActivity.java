package com.waibao.dongru.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.waibao.dongru.R;
import com.waibao.dongru.fragment.MainFragment;
import com.waibao.dongru.fragment.MainLeftFragment;

/**
 * Created by Administrator on 2019/3/21.
 */

public class MainActivity extends SlidingActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_content);

        // set the Behind View
        setBehindContentView(R.layout.frame_menu);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MainLeftFragment menuFragment = new MainLeftFragment();
        fragmentTransaction.replace(R.id.menu, menuFragment);
        fragmentTransaction.replace(R.id.content, new MainFragment());
        fragmentTransaction.commit();

        // customize the SlidingMenu
        SlidingMenu sm = getSlidingMenu();
        sm.setBehindOffset(260);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
    }
}
