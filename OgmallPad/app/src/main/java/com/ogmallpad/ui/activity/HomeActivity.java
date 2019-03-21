package com.ogmallpad.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.anyapps.sdk.AAConfig;
import com.anyapps.sdk.AE7showDirector;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.listener.TitleBarListener;
import com.junshan.pub.manager.TabManager;
import com.junshan.pub.utils.AppUtils;
import com.junshan.pub.utils.MD5Utils;
import com.junshan.pub.utils.PermissionUtis;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.constant.TabConstant;
import com.ogmallpad.contract.HomeContract;
import com.ogmallpad.databinding.DialogQueryLayoutBinding;
import com.ogmallpad.databinding.FgHomeLayoutOneBinding;
import com.ogmallpad.presenter.HomePresenter;
import com.ogmallpad.service.NetService;
import com.ogmallpad.ui.BaseActivity;
import com.ogmallpad.ui.fragment.AdduserFragment;
import com.ogmallpad.ui.fragment.BaginFragment;
import com.ogmallpad.ui.fragment.BrandlookTabFragment;
import com.ogmallpad.ui.fragment.HomeFragment;
import com.ogmallpad.ui.fragment.HomeOneFragment;
import com.ogmallpad.ui.fragment.MineFragment;
import com.ogmallpad.ui.fragment.PlanTabFragment;
import com.ogmallpad.ui.fragment.ProductlookFragment;
import com.ogmallpad.widget.DragImageView;
import com.shan.netlibrary.bean.YqxLoginBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/13.
 */

public class HomeActivity extends BaseActivity<FgHomeLayoutOneBinding,Object> implements TitleBarListener,HomeContract.View{
    public static final String SHOW_CURRENT = "SHOW_CURRENT";
    private FragmentManager fmanager;
    TabManager tabManager;
    private CommonDialog mDialog;
    private DialogQueryLayoutBinding queryLayoutBinding;
    private DragImageView dragImageView;
    private int currentCurr;
    private int width;
    private int height;
    private String name;
    private HomePresenter presenter;

    @Override
    public int bindLayout() {
        return R.layout.fg_home_layout_one;
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        fmanager = getSupportFragmentManager();
        presenter = new HomePresenter(this,this);
        currentCurr = getIntent().getIntExtra(SHOW_CURRENT,0);
        tabManager = new TabManager(this, mBinding.flHost, TabConstant.MAIN_FRAGMENT, TabConstant.MAIN_IAMGEVIEW, TabConstant.MAIN_TEXTVIEW, getSupportFragmentManager());
        tabManager.initTab(R.id.fl_content);
        checkPsermissions();
        checkUser();
        mBinding.tvAddUser.setOnClickListener(this);
        mBinding.icLogout.setOnClickListener(this);

        tabManager.setCurrentTab(currentCurr);
        width = ScreenUtils.getScreenWidth();
        height = ScreenUtils.getScreenHeight();
        name = (String) SPUtils.get(SpConstant.USERNAME, "");
        addDragView();
    }

    private void checkUser() {
        if (TextUtils.isEmpty(MyApp.getInstance().getCurrentCustomerName())){
            mBinding.tvAddUser.setVisibility(View.VISIBLE);
            mBinding.icLogout.setVisibility(View.GONE);
        }else {
            mBinding.tvAddUser.setVisibility(View.INVISIBLE);
            mBinding.icLogout.setVisibility(View.VISIBLE);
        }
        EventBus.getDefault().post(new MessageEvent(MsgConstant.LOGOU_USER));
    }

    public void jumpFragment(int position){
        tabManager.setCurrentTab(position);
    }

    public FgHomeLayoutOneBinding getBinding(){
        return mBinding;
    }

    @Override
    public void setTitleBarTitle(int position) {

    }

    @Override
    public void isLogin() {
        if (MyApp.getInstance().getLoginBean().getData().getUserName() == null){
            startActivity(LoginActivity.class,null);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_add_user:
                startFragment(AdduserFragment.class, null);
                break;
            case R.id.ic_logout:
                showRdDialog();
                break;
        }
    }

    private void showRdDialog() {
        if (mDialog == null) {
            mDialog = new CommonDialog.Builder(this)
                    .setWidth(0.3f)
                    .setHeight(0.3f)
                    .setResId(R.layout.dialog_query_layout)
                    .build();
            queryLayoutBinding = (DialogQueryLayoutBinding) mDialog.getBinding();
            queryLayoutBinding.tvHint.setText("退出客户");
            queryLayoutBinding.tvMsg.setText("确定要退出当前客户吗？");
            queryLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                }
            });
            queryLayoutBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, String> map = new HashMap<>();
                    map.put("userId", String.valueOf(MyApp.getInstance().getLoginBean().getData().getUserId()));
                    presenter.customerlogout(map);
                    //停止录音
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.STOPRECORD));
                    MyApp.getInstance().setCurrentCustomerName(null);
                    ToastUtils.toast(getResources().getString(R.string.str_tccg));
                    mDialog.dismiss();
                    checkUser();
                }
            });
        }
        mDialog.show();
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.LOGOUT){
            MyApp.getInstance().setLoginBean(null);
            MyApp.getInstance().currentTabHost = 0;
            SPUtils.put(SpConstant.PASSWORD, "");//清除登录密码
            startActivity(LoginActivity.class, null);
        }else if (msgEvent.getType() == MsgConstant.STARTRECORD){
            mBinding.tvAddUser.setVisibility(View.INVISIBLE);
            mBinding.icLogout.setVisibility(View.VISIBLE);
        }
    }
    public void checkPsermissions() {
        String[] PERMISSIONS = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        };
        if (PermissionUtis.checkPermissions(this, PERMISSIONS)) {
            PermissionUtis.requestPermissions(this, PermissionUtis.REQUESTCODE, PERMISSIONS);
        } else {
            startNetService();
        }
    }

    /**
     * 授权回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionUtis.REQUESTCODE:
                if (!PermissionUtis.hasAllPermissionsGranted(grantResults)) {
                    ToastUtils.toast(getString(R.string.str_sqsb));
                } else {
                    startNetService();
                }
                break;
        }
    }
    private void startNetService() {
        //开启服务
        Intent intent = new Intent(this, NetService.class);
        startService(intent);
    }

    /**
     * 添加可拖动View
     */
    private void addDragView() {

        dragImageView = new DragImageView(this) {
            @Override
            public void up() {
                super.up();
                int x = dragImageView.getLeft();
                int y = dragImageView.getTop();
                SPUtils.put(SpConstant.MOVE_VIEW_X, x);
                SPUtils.put(SpConstant.MOVE_VIEW_Y, y);
                onrefreshPos();
            }
        };
        dragImageView.setImageResource(R.mipmap.ic_3d);
        mBinding.ll.addView(dragImageView);
        dragImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginYqx();
            }
        });
        onrefreshPos();
    }
    private void onrefreshPos() {
        int leftMargin = (int) SPUtils.get(SpConstant.MOVE_VIEW_X, (int) (width * 0.85));
        int topMargin = (int) SPUtils.get(SpConstant.MOVE_VIEW_Y, (int) (height * 0.75));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = width / 8;
        params.height = width / 8;
        params.leftMargin = leftMargin;
        params.topMargin = topMargin;
        dragImageView.setLayoutParams(params);
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
        AAConfig c = MyApp.getInstance().getAAConfig(this);
        // 开启3D设计工具
        AE7showDirector.start(c);
        finish();
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
}
