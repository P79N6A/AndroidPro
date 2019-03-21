package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.contract.MydesignContract;
import com.ogmamllpadnew.databinding.DialogAddDesignerLayoutBinding;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的设计师
 * Created by 陈俊山 on 2018-11-16.
 */

public class MydesignPresenter extends HttpPresenter implements MydesignContract.Presenter {
    private Context mContext;
    private MydesignContract.View mView;

    public MydesignPresenter(Context mContext, MydesignContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    private CommonDialog dialogAddUser;
    private DialogAddDesignerLayoutBinding addDesignerLayoutBinding;

    @Override
    public void showAddDesigner() {
        if (dialogAddUser == null) {
            dialogAddUser = new CommonDialog.Builder(mContext)
                    .setWidth(0.5f)
                    .setHeight(0.6f)
                    .setResId(R.layout.dialog_add_designer_layout)
                    .build();
            addDesignerLayoutBinding = (DialogAddDesignerLayoutBinding) dialogAddUser.getBinding();
            addDesignerLayoutBinding.tvProvince.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.toAddress(1, "");
                }
            });
            addDesignerLayoutBinding.tvCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(provinceId)) {
                        ToastUtils.toast("请先选择省份");
                        return;
                    }
                    mView.toAddress(2, provinceId);
                }
            });
            addDesignerLayoutBinding.tvCounty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(cityId)) {
                        ToastUtils.toast("请先选择市");
                        return;
                    }
                    mView.toAddress(3, cityId);
                }
            });
            addDesignerLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddUser.dismiss();
                }
            });
            addDesignerLayoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogAddUser.dismiss();
                }
            });
            addDesignerLayoutBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    useraddAccountOfDesigner();
                }
            });
        }
        dialogAddUser.show();
    }

    private String provinceId;
    private String cityId;
    private String districtId;

    /**
     * 增加店铺
     */
    private void useraddAccountOfDesigner() {
        String name = addDesignerLayoutBinding.etName.getText().toString().trim();
        String mobile = addDesignerLayoutBinding.etMobile.getText().toString().trim();
        String address = addDesignerLayoutBinding.etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            JitterUtils.start(addDesignerLayoutBinding.etName);
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            JitterUtils.start(addDesignerLayoutBinding.etMobile);
            return;
        }
        if (mobile.length() != 11) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrsywsjhm));
            JitterUtils.start(addDesignerLayoutBinding.etMobile);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            JitterUtils.start(addDesignerLayoutBinding.etAddress);
            return;
        }
        if (TextUtils.isEmpty(provinceId)) {
            ToastUtils.toast("请选择省份");
            return;
        }
        if (TextUtils.isEmpty(cityId)) {
            ToastUtils.toast("请选择市");
            return;
        }
        if (TextUtils.isEmpty(districtId)) {
            ToastUtils.toast("请选择区");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("contactName", name);
        map.put("contactPhone", mobile);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("districtId", districtId);
        map.put("address", address);
        useraddAccountOfDesigner(map);
    }

    @Override
    public void setAddressBean(AddressCallbackBean addressBean) {
        if (addressBean != null) {
            if (addressBean.getType() == 1) {
                provinceId = addressBean.getId();
                addDesignerLayoutBinding.tvProvince.setText(addressBean.getName());
                cityId = null;
                districtId = null;
                addDesignerLayoutBinding.tvCity.setText("");
                addDesignerLayoutBinding.tvCounty.setText("");
            } else if (addressBean.getType() == 2) {
                cityId = addressBean.getId();
                addDesignerLayoutBinding.tvCity.setText(addressBean.getName());
                districtId = null;
                addDesignerLayoutBinding.tvCounty.setText("");
            } else if (addressBean.getType() == 3) {
                districtId = addressBean.getId();
                addDesignerLayoutBinding.tvCounty.setText(addressBean.getName());
            }
        }
    }

    /**
     * 添加成功
     */
    @Override
    public void addOnsuccess() {
        addDesignerLayoutBinding.etName.setText("");
        addDesignerLayoutBinding.etMobile.setText("");
        addDesignerLayoutBinding.etAddress.setText("");
        addDesignerLayoutBinding.tvCity.setText("");
        addDesignerLayoutBinding.tvCounty.setText("");
        addDesignerLayoutBinding.tvProvince.setText("");
        dialogAddUser.dismiss();
    }
}