package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.AdduserContract;
import com.ogmallpad.databinding.FgAdduserLayoutBinding;
import com.ogmallpad.presenter.AdduserPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.CenteraddCustomerBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加客户
 * Created by chenjunshan on 2018-07-03.
 */

public class AdduserFragment extends BaseFragment<FgAdduserLayoutBinding, Object> implements AdduserContract.View {
    private AdduserPresenter presenter;

    @Override
    public int bindLayout() {
        return R.layout.fg_adduser_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.tvLeft.setText(getString(R.string.str_tjkh));
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new AdduserPresenter(getActivity(), this);

    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.CENTERADDCUSTOMER) {
            ToastUtils.toast(getString(R.string.str_tjcg));
            CenteraddCustomerBean bean = (CenteraddCustomerBean) baseBean;
            MyApp.getInstance().currentTabHost = 0;
            MyApp.getInstance().setCustomerId(bean.getData().getCustomerId());
            MyApp.getInstance().setCreateTime(System.currentTimeMillis());
            String name = bean.getData().getCustomerName();
            if (TextUtils.isEmpty(name)) {
                name = bean.getData().getCustomerMobile();
            }
            MyApp.getInstance().setCurrentCustomerName(name);
            EventBus.getDefault().post(new MessageEvent(MsgConstant.STARTRECORD));
            getActivity().finish();
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
        mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomer();
            }
        });
        mBinding.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftKeyBoardUtils.closeKeybord(mBinding.etMobile, getActivity());
            }
        });
    }

    private String name;
    private String mobile;

    /**
     * 添加客户
     */
    private void addCustomer() {
        name = mBinding.etName.getText().toString().trim();
        mobile = mBinding.etMobile.getText().toString().trim();
        if (TextUtils.isEmpty(name) && TextUtils.isEmpty(mobile)) {
            JitterUtils.start(mBinding.etName);
            return;
        }
        if (!TextUtils.isEmpty(mobile) && mobile.length() != 11) {
            ToastUtils.toast(getString(R.string.str_qsrzqdsjhm));
            JitterUtils.start(mBinding.etMobile);
            return;
        }
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(name)) {
            map.put("cName", name);
        }
        if (!TextUtils.isEmpty(mobile)) {
            map.put("phone", mobile);
        }
        map.put("userId", String.valueOf(loginBean.getData().getUserId()));
        presenter.centeraddCustomer(map);
    }
}
