package com.ogmamllpadnew.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.anyapps.sdk.AAConfig;
import com.anyapps.sdk.AE7showDirector;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.listener.TitleBarListener;
import com.junshan.pub.manager.TabManager;
import com.junshan.pub.utils.MD5Utils;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.constant.TabConstant;
import com.ogmamllpadnew.contract.MainContract;
import com.ogmamllpadnew.databinding.FgMainLayoutBinding;
import com.ogmamllpadnew.presenter.MainPresenter;
import com.ogmamllpadnew.service.NetService;
import com.ogmamllpadnew.ui.BaseActivity;
import com.ogmamllpadnew.ui.fragment.AddressActivity;
import com.ogmamllpadnew.widget.DragImageView;
import com.shan.netlibrary.bean.UseraddCustomerBean;
import com.shan.netlibrary.bean.UserselectCustomerBean;
import com.shan.netlibrary.bean.YqxLoginBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页
 * Created by chenjunshan on 2018-03-09.
 */
public class MainActivity extends BaseActivity<FgMainLayoutBinding, Object> implements MainContract.View, TitleBarListener {
    private MainPresenter presenter;
    private TabManager tabManager;
    private int position = 0;//底部Tab的位置
    private int width;
    private int height;
    private String name;

    @Override
    public int bindLayout() {
        return R.layout.fg_main_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new MainPresenter(this, this);
        isSlidingClose = false;
        width = ScreenUtils.getScreenWidth();
        height = ScreenUtils.getScreenHeight();
        name = (String) SPUtils.get(SpConstant.USERNAME, "");
        initTab();
        startNetService();
        addDragView();

        requestPermissions();
    }

    private void initTab() {
        tabManager = new TabManager(this, mBinding.tabhost, TabConstant.MAIN_FRAGMENT, TabConstant.MAIN_IAMGEVIEW, TabConstant.MAIN_TEXTVIEW, getSupportFragmentManager());
        tabManager.initTab(R.id.fl_content);

        /*mBinding.tabhost.getTabWidget().getChildTabViewAt(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MyApp.getInstance().isLogin()) {
                    startActivity(LoginActivity.class, null);
                } else {
                    tabManager.setCurrentTab(4);
                }
            }
        });*/
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
        } else if (mTag == HttpPresenter.USERADDCUSTOMER) {
            //添加客户
            ToastUtils.toast(getString(R.string.str_tjcg));
            UseraddCustomerBean customerBean = (UseraddCustomerBean) baseBean;
            presenter.getDialogAddUser().dismiss();
            UserselectCustomerBean.DataBean dataBean = new UserselectCustomerBean.DataBean(customerBean.getData());

            MyApp.getInstance().setCurrentUser(dataBean);
            MyApp.getInstance().setCreateTime(System.currentTimeMillis());
            EventBus.getDefault().post(new MessageEvent(MsgConstant.SHOWUSER));
            //开始录音
            EventBus.getDefault().post(new MessageEvent(MsgConstant.STARTRECORD));
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
        mBinding.ivLogoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.queryDialog();
            }
        });
        mBinding.tvAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addUser();
            }
        });
        //键盘监听
        SoftKeyBoardUtils.setListener(this, new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
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
    }

    @Override
    public void setTitleBarTitle(int position) {
        this.position = position;
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        if (msgEvent.getType() == MsgConstant.TABHOST) {
            int pos = (int) msgEvent.getBean();
            tabManager.setCurrentTab(pos);
        } else if (msgEvent.getType() == MsgConstant.LOGOUTTOLOGIN) {
            SPUtils.put(SpConstant.TOKEN, "");
            SPUtils.put(SpConstant.PASSWORD, "");
            MyApp.getInstance().setLoginBean(null);
            MyApp.getInstance().setInfoBean(null);
            //跳转到首页
            //EventBus.getDefault().post(new MessageEvent(MsgConstant.TABHOST, 0));
            startActivity(LoginActivity.class, null);
            finish();
        } else if (msgEvent.getType() == MsgConstant.GETVERSION) {
            presenter.getLastVersion();
        } else if (msgEvent.getType() == MsgConstant.SHOWUSER) {
            setUserStatus(true);
            mBinding.tvName.setText(MyApp.getInstance().getCurrentUser().getContactName());
        } else if (msgEvent.getType() == MsgConstant.ADDRESS) {
            AddressCallbackBean addressBean = (AddressCallbackBean) msgEvent.getBean();
            presenter.setAddressBean(addressBean);
        } else if (msgEvent.getType() == MsgConstant.SYSTEMERROR) {
            startActivity(SystemErrorActivity.class,null);
        }else if (msgEvent.getType() == MsgConstant.UPDATE_CURRENT_USER){
            mBinding.tvName.setVisibility(View.VISIBLE);
            mBinding.tvName.setText(MyApp.getInstance().getCurrentUser().getContactName());
        }
    }

    /**
     * 设置用户显示状态
     */
    @Override
    public void setUserStatus(boolean isShow) {
        if (isShow) {
            mBinding.ivLogoutUser.setVisibility(View.VISIBLE);
            mBinding.tvAddUser.setVisibility(View.GONE);
            mBinding.tvName.setVisibility(View.VISIBLE);
        } else {
            mBinding.ivLogoutUser.setVisibility(View.GONE);
            mBinding.tvAddUser.setVisibility(View.VISIBLE);
            mBinding.tvName.setVisibility(View.GONE);
        }
    }

    private long lastPressTime = 0L;//最后一次点击时间

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (position != 0 && tabManager.geteCurrentTab() != 0) {
                tabManager.setCurrentTab(0);
                return true;
            }

            long currentThreadTimeMillis = System.currentTimeMillis();
            if (currentThreadTimeMillis - lastPressTime > 2000) {
                lastPressTime = currentThreadTimeMillis;
                ToastUtils.toast(getResources().getString(R.string.str_zayc));
                return true;
            }
            AE7showDirector.end();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 开启录音服务
     */
    private void startNetService() {
        //开启服务
        Intent intent = new Intent(this, NetService.class);
        startService(intent);
    }

    private DragImageView dragImageView;

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
        mBinding.llMove.addView(dragImageView);
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
    public void toAddress(int type, String id) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, type);
        bundle.putString(Constants.ID, id);
        startActivity(AddressActivity.class, bundle);
    }

    private void requestPermissions(){
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                int permission = ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permission!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]
                            {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
                }
                if(permission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[] {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},0x0010);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
