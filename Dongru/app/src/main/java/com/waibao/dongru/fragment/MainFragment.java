package com.waibao.dongru.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.gwm.annotation.Layout;
import com.gwm.base.BaseFragment;
import com.waibao.dongru.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by Administrator on 2019/3/21.
 */
@Layout(R.layout.fg_main)
public class MainFragment extends BaseFragment {
    private FragmentManager fragmentManager;
    private List<BaseFragment> fragments = new ArrayList<>();
    @Override
    public void onCreateView(LayoutInflater inflater, Bundle savedInstanceState) {
        super.onCreateView(inflater, savedInstanceState);
        fragmentManager = getChildFragmentManager();
        fragments.add(new HomeFragment());
        fragments.add(new FenGroupFragment());
        fragments.add(new SettingFragment());
        jumpFragment(fragments.get(0));
    }

    public void jumpFragment(BaseFragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_content,fragment);
        fragmentTransaction.commit();
    }

    @OnClick({R.id.ll_home,R.id.ll_fenzhu,R.id.ll_setting})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_home:
                jumpFragment(fragments.get(0));
                break;
            case R.id.ll_fenzhu:
                jumpFragment(fragments.get(1));
                break;
            case R.id.ll_setting:
                jumpFragment(fragments.get(2));
                break;
        }
    }

    @Override
    public void onRequestSuccessData(Object data) {

    }
}
