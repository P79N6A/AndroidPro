package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.anyapps.sdk.AAConfig;
import com.anyapps.sdk.AE7showDirector;
import com.junshan.pub.utils.MD5Utils;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.contract.HomeContract;
import com.ogmallpad.contract.HomeOneContract;
import com.ogmallpad.databinding.HomeOneLayoutBinding;
import com.ogmallpad.databinding.HomeOneLayoutItemBinding;
import com.ogmallpad.presenter.HomePresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.ui.activity.HomeActivity;
import com.ogmallpad.widget.DragImageView;
import com.shan.netlibrary.bean.YqxLoginBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/13.
 */

public class HomeOneFragment extends BaseFragment<HomeOneLayoutBinding,Object> {

    @Override
    public int bindLayout() {
        return R.layout.home_one_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();

    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof HomeActivity) {
            ((HomeActivity) getActivity()).getBinding().icLogo.setVisibility(View.VISIBLE);
            ((HomeActivity) getActivity()).getBinding().icTitleIcon.setVisibility(View.GONE);
            ((HomeActivity) getActivity()).getBinding().tvTitle.setVisibility(View.GONE);
            ((HomeActivity) getActivity()).getBinding().llSearch.setVisibility(View.GONE);
        }
    }
}
