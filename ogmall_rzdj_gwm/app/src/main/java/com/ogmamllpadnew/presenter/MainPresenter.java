package com.ogmamllpadnew.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.AppUtils;
import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.NetUtils;
import com.junshan.pub.utils.PermissionUtis;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.utils.download.DownloadFile;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.MainContract;
import com.ogmamllpadnew.databinding.DialogAddUserLayoutBinding;
import com.ogmamllpadnew.databinding.DialogQueryLayoutBinding;
import com.ogmamllpadnew.databinding.DialogUpgradeBinding;
import com.shan.netlibrary.bean.UpgradeBean;
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

public class MainPresenter extends HttpPresenter implements MainContract.Presenter {
    private Context mContext;
    private MainContract.View mView;

    public MainPresenter(Context mContext, MainContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
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
        Map<String,String> map = new HashMap<>();
        map.put("packageName",mContext.getPackageName());
        HttpCallback callback = new HttpCallback<UpgradeBean>(mContext, this, false) {
            @Override
            protected void onSuccess(UpgradeBean baseBean) {
                upgradeBean = baseBean;
                int version = AppUtils.getVersionCode(mContext);
                if (baseBean.getData().getVersionCode() > version) {
                    showUpgradeDialog(baseBean);
                }
            }

            @Override
            protected void onFailure(Throwable e) {
            }
        };
        startRequest(HttpBuilder.httpService.getVersion(AppParams.getParams(null)), callback);
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
                    .setShape(R.drawable.dialog_tra_shape)
                    .setTouchOutside(false)
                    .setAnimResId(0)
                    .setWidth(0.33f)
                    .setHeight(0.75f)
                    .build();
            upgradeBinding = (DialogUpgradeBinding) upgradeDialog.getBinding();
            String content = baseBean.getData().getIntroduce();
            String versionName = baseBean.getData().getVersionName();
            if (!TextUtils.isEmpty(versionName)) {
                if (versionName.contains("_")) {
                    versionName = versionName.split("_")[0];
                }
                upgradeBinding.tvVersion.setText("V " + versionName);
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
        downloadFile.start(baseBean.getData().getUpdateUrl());
        //downloadFile.start("http://192.168.1.109:8080/examples/test.apk");
    }
    //版本升级结束-----------------------------------------------------------//
    //-----------------------------------------------------------//

    private CommonDialog mDialog;
    private DialogQueryLayoutBinding queryLayoutBinding;

    @Override
    public void queryDialog() {
        if (mDialog == null) {
            mDialog = new CommonDialog.Builder(mContext)
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
                    mView.setUserStatus(false);
                    //停止录音
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.STOPRECORD));
                    MyApp.getInstance().setCurrentUser(null);
                    ToastUtils.toast(mContext.getResources().getString(R.string.str_tccg));
                    mDialog.dismiss();
                }
            });
        }
        mDialog.show();
    }

    private CommonDialog hintDialog;
    private DialogQueryLayoutBinding hintBinding;

    @Override
    public void hintDialog() {
        if (hintDialog == null) {
            hintDialog = new CommonDialog.Builder(mContext)
                    .setWidth(0.3f)
                    .setHeight(0.3f)
                    .setResId(R.layout.dialog_query_layout)
                    .build();
            hintBinding = (DialogQueryLayoutBinding) hintDialog.getBinding();
            hintBinding.tvHint.setText("提示");
            hintBinding.tvMsg.setText("该账户未开通权限，请联系客服！");
            hintBinding.btnCancel.setVisibility(View.GONE);
            hintBinding.view.setVisibility(View.GONE);
            hintBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hintDialog.dismiss();

                }
            });
        }
        hintDialog.show();
    }

    /**
     * #######################################添加客户###################################################
     */
    private CommonDialog dialogAddUser;
    private DialogAddUserLayoutBinding addUserLayoutBinding;

    public CommonDialog getDialogAddUser() {
        return dialogAddUser;
    }

    private String provinceIdUser;
    private String cityIdUser;
    private String districtIdUser;

    /**
     * 添加客户
     */
    @Override
    public void addUser() {
        if (dialogAddUser == null) {
            dialogAddUser = new CommonDialog.Builder(mContext)
                    .setWidth(0.5f)
                    .setHeight(0.6f)
                    .setResId(R.layout.dialog_add_user_layout)
                    .build();
            addUserLayoutBinding = (DialogAddUserLayoutBinding) dialogAddUser.getBinding();
            addUserLayoutBinding.tvProvince.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.toAddress(1, "");
                }
            });
            addUserLayoutBinding.tvCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(provinceIdUser)) {
                        ToastUtils.toast("请先选择省份");
                        return;
                    }
                    mView.toAddress(2, provinceIdUser);
                }
            });
            addUserLayoutBinding.tvCounty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(cityIdUser)) {
                        ToastUtils.toast("请先选择市");
                        return;
                    }
                    mView.toAddress(3, cityIdUser);
                }
            });
            addUserLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddUser.dismiss();
                }
            });
            addUserLayoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddUser.dismiss();
                }
            });
            addUserLayoutBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    appAddCustomer();
                }
            });
        }
        clearUserInfo();
        dialogAddUser.show();
    }

    @Override
    public void clearUserInfo() {
        addUserLayoutBinding.etName.setText("");
        addUserLayoutBinding.etAddress.setText("");
        addUserLayoutBinding.etMobile.setText("");
        addUserLayoutBinding.tvProvince.setText("");
        addUserLayoutBinding.tvCity.setText("");
        addUserLayoutBinding.tvCounty.setText("");
    }

    /**
     * 添加客户
     */
    private void appAddCustomer() {
        String name = addUserLayoutBinding.etName.getText().toString().trim();
        String mobile = addUserLayoutBinding.etMobile.getText().toString().trim();
        String address = addUserLayoutBinding.etAddress.getText().toString().trim();
        /*if (TextUtils.isEmpty(name)) {
            JitterUtils.start(addUserLayoutBinding.etName);
            return;
        }*/
        if (TextUtils.isEmpty(mobile)) {
            JitterUtils.start(addUserLayoutBinding.etMobile);
            return;
        }
        if (mobile.length() != 11) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrsywsjhm));
            JitterUtils.start(addUserLayoutBinding.etMobile);
            return;
        }
        /*if (TextUtils.isEmpty(address)) {
            JitterUtils.start(addUserLayoutBinding.etAddress);
            return;
        }*/
        Map<String, String> map = new HashMap<>();
        map.put("contactName", name);
        map.put("contactPhone", mobile);
        map.put("provinceId", provinceIdUser);
        map.put("cityId", cityIdUser);
        map.put("districtId", districtIdUser);
        map.put("address", address);
        useraddCustomer(map);
    }

    @Override
    public void setAddressBean(AddressCallbackBean addressBean) {
        if (addressBean != null) {
            if (addressBean.getType() == 1) {
                provinceIdUser = addressBean.getId();
                addUserLayoutBinding.tvProvince.setText(addressBean.getName());
                cityIdUser = null;
                districtIdUser = null;
                addUserLayoutBinding.tvCity.setText("");
                addUserLayoutBinding.tvCounty.setText("");
            } else if (addressBean.getType() == 2) {
                cityIdUser = addressBean.getId();
                addUserLayoutBinding.tvCity.setText(addressBean.getName());
                districtIdUser = null;
                addUserLayoutBinding.tvCounty.setText("");
            } else if (addressBean.getType() == 3) {
                districtIdUser = addressBean.getId();
                addUserLayoutBinding.tvCounty.setText(addressBean.getName());
            }
        }
    }
}