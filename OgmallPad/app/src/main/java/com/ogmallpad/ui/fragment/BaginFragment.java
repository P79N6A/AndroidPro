package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.ogmallpad.R;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.BaginContract;
import com.ogmallpad.databinding.FgBaginLayoutBinding;
import com.ogmallpad.presenter.BaginPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.ui.activity.HomeActivity;
import com.shan.netlibrary.net.BaseBean;

import org.greenrobot.eventbus.EventBus;

/**
 * 拎包入住
 * Created by chenjunshan on 2018-08-21.
 */

public class BaginFragment extends BaseFragment<FgBaginLayoutBinding, Object> implements BaginContract.View {
    private BaginPresenter presenter;
    public static BaginLeftFragment leftFragment;//左边布局
    public static BaginRightFragment rightFragment;//右边布局

    @Override
    public int bindLayout() {
        return R.layout.fg_bagin_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.tvLeft.setText(getString(R.string.str_lbrz));
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BaginPresenter(getActivity(), this);
        leftFragment = new BaginLeftFragment();
        rightFragment = new BaginRightFragment();
        //动态加载fragment
        fragmentReplace(leftFragment, R.id.fl_left);
        fragmentReplace(rightFragment, R.id.fl_right);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TAG","PAUSE");
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)getActivity()).getBinding().icLogo.setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getBinding().icTitleIcon.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().tvTitle.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().icTitleIcon.setImageResource(R.mipmap.ic_bagin_on);
        ((HomeActivity)getActivity()).getBinding().tvTitle.setText("拎包入住");
        ((HomeActivity)getActivity()).getBinding().llSearch.setVisibility(View.GONE);
        EventBus.getDefault().post(new MessageEvent(MsgConstant.SHOW_HX));
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {

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
        SoftKeyBoardUtils.setListener(getActivity(), new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }
}
