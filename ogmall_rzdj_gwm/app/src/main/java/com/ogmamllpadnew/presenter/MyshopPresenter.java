package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.contract.MyshopContract;
import com.ogmamllpadnew.databinding.DialogAddShopLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的店铺商
 * Created by 陈俊山 on 2018-11-12.
 */

public class MyshopPresenter extends HttpPresenter implements MyshopContract.Presenter {
    private Context mContext;
    private MyshopContract.View mView;

    public MyshopPresenter(Context mContext, MyshopContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    private CommonDialog dialogAddUser;
    private DialogAddShopLayoutBinding addUserLayoutBinding;

    public CommonDialog getDialogAddUser() {
        return dialogAddUser;
    }

    public void setDialogAddUser(CommonDialog dialogAddUser) {
        this.dialogAddUser = dialogAddUser;
    }

    @Override
    public void showAddShop() {
        if (dialogAddUser == null) {
            dialogAddUser = new CommonDialog.Builder(mContext)
                    .setWidth(0.5f)
                    .setHeight(0.6f)
                    .setResId(R.layout.dialog_add_shop_layout)
                    .build();
            addUserLayoutBinding = (DialogAddShopLayoutBinding) dialogAddUser.getBinding();
            addUserLayoutBinding.tvProvince.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.toAddress(1, "");
                }
            });
            addUserLayoutBinding.tvCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(provinceId)) {
                        ToastUtils.toast("请先选择省份");
                        return;
                    }
                    mView.toAddress(2, provinceId);
                }
            });
            addUserLayoutBinding.tvCounty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(cityId)) {
                        ToastUtils.toast("请先选择市");
                        return;
                    }
                    mView.toAddress(3, cityId);
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
                    addAccountOfStore();
                }
            });
        }
        dialogAddUser.show();
    }

    public void clearData(){
        addUserLayoutBinding.etName.setText("");
        addUserLayoutBinding.etUser.setText("");
        addUserLayoutBinding.etMobile.setText("");
        addUserLayoutBinding.etAddress.setText("");
        addUserLayoutBinding.tvCity.setText("");
        addUserLayoutBinding.tvCounty.setText("");
        addUserLayoutBinding.tvProvince.setText("");
    }

    private String provinceId;
    private String cityId;
    private String districtId;

    /**
     * 增加店铺
     */
    private void addAccountOfStore() {
        String name = addUserLayoutBinding.etName.getText().toString().trim();
        String username = addUserLayoutBinding.etUser.getText().toString().trim();
        String mobile = addUserLayoutBinding.etMobile.getText().toString().trim();
        String address = addUserLayoutBinding.etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            JitterUtils.start(addUserLayoutBinding.etName);
            return;
        }
        if (TextUtils.isEmpty(username)) {
            JitterUtils.start(addUserLayoutBinding.etUser);
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            JitterUtils.start(addUserLayoutBinding.etMobile);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            JitterUtils.start(addUserLayoutBinding.etAddress);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("storeName", name);
        map.put("contactName", username);
        map.put("contactPhone", mobile);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("districtId", districtId);
        map.put("address", address);
        useraddAccountOfStore(map);
    }

    @Override
    public void setAddressBean(AddressCallbackBean addressBean) {
        if (addressBean != null) {
            if (addressBean.getType() == 1) {
                provinceId = addressBean.getId();
                addUserLayoutBinding.tvProvince.setText(addressBean.getName());
                cityId = null;
                districtId = null;
                addUserLayoutBinding.tvCity.setText("");
                addUserLayoutBinding.tvCounty.setText("");
            } else if (addressBean.getType() == 2) {
                cityId = addressBean.getId();
                addUserLayoutBinding.tvCity.setText(addressBean.getName());
                districtId = null;
                addUserLayoutBinding.tvCounty.setText("");
            } else if (addressBean.getType() == 3) {
                districtId = addressBean.getId();
                addUserLayoutBinding.tvCounty.setText(addressBean.getName());
            }
        }
    }
}