package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.contract.MineContract;
import com.ogmamllpadnew.databinding.DialogAddUserLayoutBinding;
import com.ogmamllpadnew.databinding.DialogChangeDetailsLayoutBinding;
import com.ogmamllpadnew.databinding.DialogChangePasswdLayoutBinding;
import com.shan.netlibrary.bean.AppaccountInfoBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的
 * Created by 陈俊山 on 2018-11-02.
 */

public class MinePresenter extends HttpPresenter implements MineContract.Presenter {
    private Context mContext;
    private MineContract.View mView;
    private AppaccountInfoBean infoBean;
    private boolean isAddUser;//是否为添加客户

    public MinePresenter(Context mContext, MineContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    /**
     * #######################################添加客户###################################################
     */
    private CommonDialog dialogAddUser;
    private DialogAddUserLayoutBinding addUserLayoutBinding;

    public CommonDialog getDialogAddUser() {
        return dialogAddUser;
    }

    /**
     * 添加客户
     */
    @Override
    public void addUser() {
        isAddUser = true;
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


    /**
     * #######################################修改密码###################################################
     */
    private CommonDialog dialogChangePasswd;
    private DialogChangePasswdLayoutBinding changePasswdLayoutBinding;

    /**
     * 修改密码
     */
    @Override
    public void changePasswd() {
        if (dialogChangePasswd == null) {
            dialogChangePasswd = new CommonDialog.Builder(mContext)
                    .setWidth(0.5f)
                    .setHeight(0.6f)
                    .setResId(R.layout.dialog_change_passwd_layout)
                    .build();
            changePasswdLayoutBinding = (DialogChangePasswdLayoutBinding) dialogChangePasswd.getBinding();
            changePasswdLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogChangePasswd.dismiss();
                }
            });
            changePasswdLayoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogChangePasswd.dismiss();
                }
            });
            changePasswdLayoutBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String oldPasswd = changePasswdLayoutBinding.etOldPasswd.getText().toString().trim();
                    String newPasswd = changePasswdLayoutBinding.etNewPasswd.getText().toString().trim();
                    String queryPasswd = changePasswdLayoutBinding.etQueryPasswd.getText().toString().trim();
                    if (TextUtils.isEmpty(oldPasswd)) {
                        JitterUtils.start(changePasswdLayoutBinding.etOldPasswd);
                        return;
                    }
                    if (TextUtils.isEmpty(newPasswd)) {
                        JitterUtils.start(changePasswdLayoutBinding.etNewPasswd);
                        return;
                    }
                    if (TextUtils.isEmpty(queryPasswd)) {
                        JitterUtils.start(changePasswdLayoutBinding.etQueryPasswd);
                        return;
                    }
                    if (!queryPasswd.equals(newPasswd)) {
                        ToastUtils.toast("新密码和确认密码不一样，请重新输入");
                        return;
                    }
                    Map<String, String> map = new HashMap<>();
                    map.put("oldPassword", oldPasswd);
                    map.put("newPassword", newPasswd);
                    appupdatePassword(map);
                }
            });
        }
        dialogChangePasswd.show();
    }

    @Override
    public void updatePasswordSuccess() {
        changePasswdLayoutBinding.etOldPasswd.setText("");
        changePasswdLayoutBinding.etNewPasswd.setText("");
        changePasswdLayoutBinding.etQueryPasswd.setText("");
        dialogChangePasswd.dismiss();
    }

    /**
     * #######################################修改资料###################################################
     */
    private CommonDialog dialogChangeDetails;
    private DialogChangeDetailsLayoutBinding detailsLayoutBinding;

    public CommonDialog getDialogChangeDetails() {
        return dialogChangeDetails;
    }

    /**
     * 修改详细资料
     */
    @Override
    public void changeDetails() {
        isAddUser = false;
        if (dialogChangeDetails == null) {
            dialogChangeDetails = new CommonDialog.Builder(mContext)
                    .setWidth(0.55f)
                    .setHeight(0.6f)
                    .setResId(R.layout.dialog_change_details_layout)
                    .build();
            detailsLayoutBinding = (DialogChangeDetailsLayoutBinding) dialogChangeDetails.getBinding();
            detailsLayoutBinding.btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.setDismissEditDialog(false);
                    mView.selectPicture();
                }
            });
            detailsLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogChangeDetails.dismiss();
                }
            });
            detailsLayoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogChangeDetails.dismiss();
                }
            });
            detailsLayoutBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.setDismissEditDialog(true);
                    appeditPersonalData();
                }
            });
            detailsLayoutBinding.tvProvince.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.toAddress(1, "");
                }
            });
            detailsLayoutBinding.tvCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.toAddress(2, provinceId);
                }
            });
            detailsLayoutBinding.tvCounty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.toAddress(3, cityId);
                }
            });
        }
        infoBean = MyApp.getInstance().getInfoBean();

        String name = infoBean.getData().getContactName();
        String provinceName = infoBean.getData().getProvinceName();
        String cityName = infoBean.getData().getCityName();
        String districtName = infoBean.getData().getDistrictName();
        provinceId = infoBean.getData().getProvinceId();
        cityId = infoBean.getData().getCityId();
        districtId = infoBean.getData().getDistrictId();
        address = infoBean.getData().getAddress();
        detailsLayoutBinding.etName.setText(name);
        detailsLayoutBinding.tvProvince.setText(provinceName);
        detailsLayoutBinding.tvCity.setText(cityName);
        detailsLayoutBinding.tvCounty.setText(districtName);
        detailsLayoutBinding.etAddress.setText(address);

        detailsLayoutBinding.etName.setText(infoBean.getData().getContactName());
        dialogChangeDetails.show();
    }

    /**
     * 刷新头像
     *
     * @param img
     */
    @Override
    public void refreshHeadImage(String img) {
        ImageCacheUtils.loadImage(mContext, img, detailsLayoutBinding.ivHead);
    }

    private void appeditPersonalData() {
        String name = detailsLayoutBinding.etName.getText().toString().trim();
        address = detailsLayoutBinding.etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            JitterUtils.start(detailsLayoutBinding.etName);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            JitterUtils.start(detailsLayoutBinding.etAddress);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("contactName", name);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("districtId", districtId);
        map.put("address", address);
        appeditPersonalData(map);
    }

    private String provinceId;
    private String cityId;
    private String districtId;
    private String address;
    private String provinceIdUser;
    private String cityIdUser;
    private String districtIdUser;
    private String addressUser;

    @Override
    public void setAddressBean(AddressCallbackBean addressBean) {
        if (addressBean != null) {
            if (isAddUser) {
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
            } else {
                if (addressBean.getType() == 1) {
                    provinceId = addressBean.getId();
                    detailsLayoutBinding.tvProvince.setText(addressBean.getName());
                    cityId = null;
                    districtId = null;
                    detailsLayoutBinding.tvCity.setText("");
                    detailsLayoutBinding.tvCounty.setText("");
                } else if (addressBean.getType() == 2) {
                    cityId = addressBean.getId();
                    detailsLayoutBinding.tvCity.setText(addressBean.getName());
                    districtId = null;
                    detailsLayoutBinding.tvCounty.setText("");
                } else if (addressBean.getType() == 3) {
                    districtId = addressBean.getId();
                    detailsLayoutBinding.tvCounty.setText(addressBean.getName());
                }
            }
        }
    }
}