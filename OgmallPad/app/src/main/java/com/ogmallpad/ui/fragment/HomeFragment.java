package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.anyapps.sdk.AAConfig;
import com.anyapps.sdk.AE7showDirector;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.ui.activity.CommonActivity;
import com.junshan.pub.utils.MD5Utils;
import com.junshan.pub.utils.SPUtils;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.contract.HomeContract;
import com.ogmallpad.databinding.FgHomeLayoutBinding;
import com.ogmallpad.presenter.HomePresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.ui.activity.HomeActivity;
import com.shan.netlibrary.bean.YqxLoginBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页
 * Created by chenjunshan on 2018-07-02.
 */

public class HomeFragment extends BaseFragment<FgHomeLayoutBinding, Object> implements HomeContract.View {
    private HomePresenter presenter;
    private String name;

    @Override
    public int bindLayout() {
        return R.layout.fg_home_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getBinding().getRoot().setVisibility(View.VISIBLE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
//        name = (String) SPUtils.get(SpConstant.USERNAME,"");
        name = "12300000003";
        presenter = new HomePresenter(getActivity(), this);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (HttpPresenter.YQXLOGIN == mTag) {
            YqxLoginBean yqxLoginBean = (YqxLoginBean) baseBean;
            int status = yqxLoginBean.getData().getStatus();
            if (status != 0) {
                presenter.hintDialog();
            } else {
                MyApp.getInstance().setmYqxToken(yqxLoginBean.getData().getData().getToken());
                MyApp.getInstance().setmYqxUserid(yqxLoginBean.getData().getData().getUser_id());
                //打开3D设计
                start3DDesign();
            }
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
        mBinding.ll1.setOnClickListener(this);
        mBinding.ll2.setOnClickListener(this);
        mBinding.ll3.setOnClickListener(this);
        mBinding.ll4.setOnClickListener(this);

        mBinding.ll5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll1:
                //是否为设计师
                if (loginBean.getData().getUserType().equals("Designer")) {
                    MyApp.getInstance().currentTabHost = 6;
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.TABHOST, 6));
                } else {
                    MyApp.getInstance().currentTabHost = 2;
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.TABHOST, 2));
                }
                break;
            case R.id.ll2:
                MyApp.getInstance().currentTabHost = 1;
                EventBus.getDefault().post(new MessageEvent(MsgConstant.TABHOST, 1));
                break;
            case R.id.ll3:
                MyApp.getInstance().currentTabHost = 4;
                EventBus.getDefault().post(new MessageEvent(MsgConstant.TABHOST, 4));
                break;
            case R.id.ll4:
                /*MyApp.getInstance().currentTabHost = 5;
                EventBus.getDefault().post(new MessageEvent(MsgConstant.TABHOST, 5));*/
                Bundle bundle = new Bundle();
                bundle.putBoolean(CommonActivity.ISSLIDINGCLOSE, false);
                startFragment(BaginFragment.class, bundle);
                break;
            case R.id.ll5:
                loginYqx();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bindData();
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        //用户类型
        /**Normal(0,2.2D,"普通用户"),
         Manager(2,1.0D,"管理员"),
         //vip设计师1.15普通设计师为2.2
         Designer(4,1.15D,"设计师"),
         City(5,1D,"运营商"),
         Factory(6,2.2D,"工厂"),
         DecorateCompany(7,1.1D,"装修公司"),
         Seed(8,2.2D,"种子用户"),
         Test(9,2.2D,"测试用户"),
         Employee(10,2.2D,"欧工员工"),
         OgSchool(11,2.2D,"欧工软装学院");
         */
        //是否为设计师
        if (loginBean.getData().getUserType().equals("Designer")) {
            mBinding.tvPlan.setText(getString(R.string.str_wdfa));
        } else {
            mBinding.tvPlan.setText(getString(R.string.str_fazs));
        }
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.LOGINSUCCESS) {
            loginBean = MyApp.getInstance().getLoginBean();
            bindData();
        }
    }

    /**
     * 登录一起秀
     */
    private void loginYqx() {
        if (TextUtils.isEmpty(MyApp.getInstance().getmYqxToken())) {
            Map<String, String> map = new HashMap<>();
            //map.put("username", "18500000001");
            //map.put("password", MD5Utils.encryption("123456"));
            map.put("username", name);
            map.put("password", MD5Utils.encryption(Constants.YQXPASSWORD));
            map.put("csys", "1");//1-android 2-ios
            presenter.yqxLogin(map);
        } else {
            //打开3D设计
            start3DDesign();
        }
    }

    private void start3DDesign() {
        // 当前环境配置
        AAConfig c = MyApp.getInstance().getAAConfig(getActivity());
        // 开启3D设计工具
        AE7showDirector.start(c);
        getActivity().finish();
    }
}
