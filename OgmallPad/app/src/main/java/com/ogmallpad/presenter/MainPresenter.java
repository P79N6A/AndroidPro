package com.ogmallpad.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.AppUtils;
import com.junshan.pub.utils.NetUtils;
import com.junshan.pub.utils.PermissionUtis;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.utils.download.DownloadFile;
import com.junshan.pub.widget.CommonDialog;
import com.ogmallpad.MyApp;
import com.ogmallpad.R;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.contract.MainContract;
import com.ogmallpad.databinding.DialogUpgradeBinding;
import com.ogmallpad.databinding.FgMainLayoutBinding;
import com.ogmallpad.databinding.LogoutLayoutBinding;
import com.ogmallpad.ui.activity.LoginActivity;
import com.shan.netlibrary.bean.CustomerlogoutBean;
import com.shan.netlibrary.bean.UpgradeBean;
import com.shan.netlibrary.bean.UserloginBean;
import com.shan.netlibrary.net.AppParams;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页
 * Created by chenjunshan on 2018-03-09.
 */

public class MainPresenter extends HttpPresenter implements MainContract.Presenter, View.OnClickListener {
    private Context mContext;
    private MainContract.View mView;
    private FgMainLayoutBinding mBinding;

    public MainPresenter(Context mContext, MainContract.View mView, FgMainLayoutBinding mBinding) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
        this.mBinding = mBinding;
        initEvent();
        customerLogout();
    }

    //左边菜单开关事件
    public void openLeftLayout() {
        if (mBinding.leftDrawer.isDrawerOpen(mBinding.llMenu)) {
            mBinding.leftDrawer.closeDrawer(mBinding.llMenu);
        } else {
            mBinding.leftDrawer.openDrawer(mBinding.llMenu);
        }
    }

    private void initEvent() {
        mBinding.llHome.setOnClickListener(this);
        mBinding.llBrand.setOnClickListener(this);
        mBinding.llPlan.setOnClickListener(this);
        mBinding.llMine.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                openLeftLayout();
                mView.tabSelect(0);
                break;
            case R.id.ll_brand:
                openLeftLayout();
                mView.tabSelect(1);
                break;
            case R.id.ll_plan:
                openLeftLayout();
                //是否为设计师
                if (MyApp.getInstance().getLoginBean().getData().getUserType().equals("Designer")) {
                    mView.tabSelect(6);
                } else {
                    mView.tabSelect(2);
                }

                break;
            case R.id.ll_mine:
                openLeftLayout();
                if (MyApp.getInstance().isLogin()) {
                    mView.tabSelect(3);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }
                break;
        }
    }

    /**
     * 设置右边tab的状态
     *
     * @param pos
     */
    public void setLeftTabStatus(int pos) {
        switch (pos) {
            case 0:
                mBinding.ivMenuHome.setChecked(true);
                mBinding.tvMenuHome.setChecked(true);
                mBinding.ivMenuBrand.setChecked(false);
                mBinding.tvMenuBrand.setChecked(false);
                mBinding.ivMenuPlan.setChecked(false);
                mBinding.tvMenuPlan.setChecked(false);
                mBinding.ivMenuMine.setChecked(false);
                mBinding.tvMenuMine.setChecked(false);
                break;
            case 1:
                mBinding.ivMenuHome.setChecked(false);
                mBinding.tvMenuHome.setChecked(false);
                mBinding.ivMenuBrand.setChecked(true);
                mBinding.tvMenuBrand.setChecked(true);
                mBinding.ivMenuPlan.setChecked(false);
                mBinding.tvMenuPlan.setChecked(false);
                mBinding.ivMenuMine.setChecked(false);
                mBinding.tvMenuMine.setChecked(false);
                break;
            case 2:
                mBinding.ivMenuHome.setChecked(false);
                mBinding.tvMenuHome.setChecked(false);
                mBinding.ivMenuBrand.setChecked(false);
                mBinding.tvMenuBrand.setChecked(false);
                mBinding.ivMenuPlan.setChecked(true);
                mBinding.tvMenuPlan.setChecked(true);
                mBinding.ivMenuMine.setChecked(false);
                mBinding.tvMenuMine.setChecked(false);
                break;
            case 3:
                mBinding.ivMenuHome.setChecked(false);
                mBinding.tvMenuHome.setChecked(false);
                mBinding.ivMenuBrand.setChecked(false);
                mBinding.tvMenuBrand.setChecked(false);
                mBinding.ivMenuPlan.setChecked(false);
                mBinding.tvMenuPlan.setChecked(false);
                mBinding.ivMenuMine.setChecked(true);
                mBinding.tvMenuMine.setChecked(true);
                break;
            case 6:
                mBinding.ivMenuHome.setChecked(false);
                mBinding.tvMenuHome.setChecked(false);
                mBinding.ivMenuBrand.setChecked(false);
                mBinding.tvMenuBrand.setChecked(false);
                mBinding.ivMenuPlan.setChecked(true);
                mBinding.tvMenuPlan.setChecked(true);
                mBinding.ivMenuMine.setChecked(false);
                mBinding.tvMenuMine.setChecked(false);
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String name = (String) SPUtils.get(SpConstant.USERNAME, "");
        String passwd = (String) SPUtils.get(SpConstant.PASSWORD, "");
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(passwd)) {
            Map<String, String> map = new HashMap<>();
            map.put("userName", name);
            map.put("password", passwd);
            HttpCallback callback = new HttpCallback<UserloginBean>(mContext, this, true) {
                @Override
                protected void onSuccess(UserloginBean baseBean) {
                    MyApp.getInstance().setLoginBean(baseBean);
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.LOGINSUCCESS));
                }

                @Override
                protected void onFailure(Throwable e) {
                }
            };
            startRequest(HttpBuilder.httpService.userlogin(AppParams.getParams(map)), callback);
        }
    }

    /**
     * 退出当前客户
     */
    private void customerLogout() {
        int userid = (int) SPUtils.get(SpConstant.USERID, 0);
        if (userid != 0) {
            Map<String, String> map = new HashMap<>();
            map.put("userId", String.valueOf(userid));
            HttpCallback callback = new HttpCallback<CustomerlogoutBean>(mContext, this) {
                @Override
                protected void onSuccess(CustomerlogoutBean baseBean) {
                    login();
                }

                @Override
                protected void onFailure(Throwable e) {
                    login();
                }
            };
            startRequest(HttpBuilder.httpService.customerlogout(AppParams.getParams(map)), callback);
        }
    }

    /**
     * 刷新头部数据
     */
    public void refreshTitleData() {
        if (MyApp.getInstance().isLogin() && !TextUtils.isEmpty(MyApp.getInstance().getCurrentCustomerName())) {
            mBinding.tvName.setVisibility(View.VISIBLE);
            mBinding.tvLogout.setVisibility(View.VISIBLE);
            mBinding.ivLogout.setVisibility(View.VISIBLE);
            mBinding.tvName.setText(mContext.getResources().getString(R.string.str_hi) + MyApp.getInstance().getCurrentCustomerName());
        } else {
            mBinding.tvName.setVisibility(View.INVISIBLE);
            mBinding.tvLogout.setVisibility(View.INVISIBLE);
            mBinding.ivLogout.setVisibility(View.INVISIBLE);
        }
    }

    private CommonDialog dialog;
    private LogoutLayoutBinding binding;

    /**
     * 弹窗
     */
    public void showDialog() {
        if (dialog == null) {
            dialog = new CommonDialog.Builder(mContext)
                    .setWidth(0.5f)
                    .setHeight(0.5f)
                    .setResId(R.layout.logout_layout)
                    .setShape(R.drawable.dialog_tra_shape)
                    .setGravity(Gravity.CENTER)
                    .build();
            binding = (LogoutLayoutBinding) dialog.getBinding();
            binding.ivHint.setImageResource(R.mipmap.ic_hint2);
            binding.tvHint.setText(mContext.getResources().getString(R.string.str_hint5));
            binding.tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, String> map = new HashMap<>();
                    map.put("userId", String.valueOf(MyApp.getInstance().getLoginBean().getData().getUserId()));
                    customerlogout(map);
                }
            });
            binding.tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    //版本升级-----------------------------------------------------------//
    //-----------------------------------------------------------//
    private CommonDialog upgradeDialog;
    private DialogUpgradeBinding upgradeBinding;
    private UpgradeBean upgradeBean;
    private DownloadFile downloadFile;

    /**
     * 7.5根据版本号获取最新版本地址
     */
    @Override
    public void getLastVersion() {
        final int version = AppUtils.getVersionCode(mContext);
        Map<String, String> map = new HashMap<>();
        map.put("type", "4");//4代表pad
        HttpCallback callback = new HttpCallback<UpgradeBean>(mContext) {
            @Override
            protected void onSuccess(UpgradeBean baseBean) {
                upgradeBean = baseBean;
                if (baseBean.getData().getVersionCode() > version) {
                    showUpgradeDialog(baseBean);
                }
            }

            @Override
            protected void onFailure(Throwable e) {
            }
        };
        startRequest(HttpBuilder.httpService.getVersion(AppParams.getParams(map)), callback);
    }

    /**
     * 版本升级
     *
     * @param baseBean
     */
    @Override
    public void showUpgradeDialog(final UpgradeBean baseBean) {
        if (upgradeDialog == null) {
            //deleteApk();
            upgradeDialog = new CommonDialog.Builder(mContext)
                    .setResId(R.layout.dialog_upgrade)
                    .setShape(R.drawable.dialog_circle_10dp)
                    .setTouchOutside(false)
                    .setAnimResId(0)
                    .setWidth(0.4f)
                    .setHeight(0.7f)
                    .build();
            upgradeBinding = (DialogUpgradeBinding) upgradeDialog.getBinding();
            String content = baseBean.getData().getIntroduce();
            String versionName = baseBean.getData().getVersionName();
            if (!TextUtils.isEmpty(versionName)) {
                if (versionName.contains("_")) {
                    versionName = versionName.split("_")[0];
                }
                upgradeBinding.tvVersion.setText(mContext.getResources().getString(R.string.str_zxbb) + versionName);
            }
            if (!TextUtils.isEmpty(content)) {
                upgradeBinding.tvContent.setText(content);
            }
            if (baseBean.getData().isIsForced()) {
                upgradeBinding.btnCancel.setVisibility(View.GONE);
            } else {
                upgradeBinding.btnCancel.setVisibility(View.VISIBLE);
            }

            upgradeBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    upgradeDialog.dismiss();
                }
            });
            upgradeBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!NetUtils.isNetworkConnected()) {
                        ToastUtils.toast(mContext.getResources().getString(R.string.str_not_net));
                        return;
                    }
                    if (baseBean.getData().isIsForced()) {
                        upgradeBinding.llOkCancel.setVisibility(View.GONE);
                        upgradeBinding.llProgress.setVisibility(View.VISIBLE);
                    }

                    String[] permisstions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    if (PermissionUtis.checkPermissions(mContext, permisstions)) {
                        PermissionUtis.requestPermissions((Activity) mContext,
                                PermissionUtis.REQUESTCODEDOWNLOAD, permisstions);
                    } else {
                        startDownload();
                    }


                }
            });
            upgradeBinding.tvInstall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    downloadFile.install();
                }
            });
        }
        upgradeDialog.show();
    }

    /**
     * 开始执行下载
     */
    @Override
    public void startDownload() {
        if (upgradeBean == null)
            return;
        downloadApk(upgradeBean);
        if (!upgradeBean.getData().isIsForced()) {
            upgradeDialog.dismiss();
        }
    }

    /**
     * 下载APK
     *
     * @param baseBean
     */
    private void downloadApk(final UpgradeBean baseBean) {
        downloadFile = new DownloadFile(mContext) {
            @Override
            protected void onProgress(float fraction) {
                if (baseBean.getData().isIsForced()) {
                    if (fraction <= 1) {
                        int progress = (int) (fraction * 100);
                        upgradeBinding.progressBar.setProgress(progress);
                        upgradeBinding.tvProgress.setText(progress + "%");
                    }
                }
            }

            @Override
            protected void onFinish() {
                upgradeBinding.tvInstall.setVisibility(View.VISIBLE);
                upgradeBinding.progressBar.setProgress(100);
                upgradeBinding.tvProgress.setText("100%");
            }
        };
        downloadFile.start(HttpBuilder.BASE_URL + "/" + baseBean.getData().getUri());
    }
    //版本升级结束-----------------------------------------------------------//
    //-----------------------------------------------------------//
}