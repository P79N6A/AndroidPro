package com.ogmamllpadnew.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.bean.SelectBean;
import com.ogmamllpadnew.contract.MyuserContract;
import com.ogmamllpadnew.databinding.DialogEditUserLayoutBinding;
import com.shan.netlibrary.bean.UserselectCustomerBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的客户
 * Created by 陈俊山 on 2018-11-12.
 */

public class MyuserPresenter extends HttpPresenter implements MyuserContract.Presenter {
    private Context mContext;
    private MyuserContract.View mView;

    public MyuserPresenter(Context mContext, MyuserContract.View mView) {
        super(mContext, mView);
        this.mContext = mContext;
        this.mView = mView;
    }

    /**
     * #######################################添加客户###################################################
     */
    private CommonDialog dialogEditUser;
    private DialogEditUserLayoutBinding editUserLayoutBinding;

    /**
     * 编辑客户
     */
    @Override
    public void editUser(final UserselectCustomerBean.DataBean item) {
        if (dialogEditUser == null) {
            dialogEditUser = new CommonDialog.Builder(mContext)
                    .setWidth(0.5f)
                    .setHeight(0.6f)
                    .setResId(R.layout.dialog_edit_user_layout)
                    .build();
            editUserLayoutBinding = (DialogEditUserLayoutBinding) dialogEditUser.getBinding();
            editUserLayoutBinding.tvProvince.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.toAddress(1, "");
                }
            });
            editUserLayoutBinding.tvCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(provinceId)) {
                        ToastUtils.toast("请先选择省份");
                        return;
                    }
                    mView.toAddress(2, provinceId);
                }
            });
            editUserLayoutBinding.tvCounty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(cityId)) {
                        ToastUtils.toast("请先选择市");
                        return;
                    }
                    mView.toAddress(3, cityId);
                }
            });
            editUserLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogEditUser.dismiss();
                }
            });
            editUserLayoutBinding.ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogEditUser.dismiss();
                }
            });
            editUserLayoutBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    usereditCustomer(String.valueOf(item.getId()));
                }
            });
            editUserLayoutBinding.tvType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView.toSelectType();
                }
            });
        }
        provinceId = item.getProvinceId();
        cityId = item.getCityId();
        districtId = item.getDistrictId();
        userTypeId = item.getSign();
        editUserLayoutBinding.etName.setText(item.getContactName());
        editUserLayoutBinding.etMobile.setText(item.getContactPhone());
        editUserLayoutBinding.tvProvince.setText(item.getProvinceName());
        editUserLayoutBinding.tvCity.setText(item.getCityName());
        editUserLayoutBinding.tvCounty.setText(item.getDistrictName());
        editUserLayoutBinding.etAddress.setText(item.getAddress());
        editUserLayoutBinding.tvType.setText(item.getSignName());
        dialogEditUser.show();
    }

    private String userTypeName = "客户类型名称";
    private String userTypeId = "客户类型id";

    //设置客户类型
    @Override
    public void setUserType(SelectBean selectBean){
        userTypeId = selectBean.getId();
        userTypeName = selectBean.getName();
        editUserLayoutBinding.tvType.setText(userTypeName);
    }

    /**
     * 编辑客户
     */
    private void usereditCustomer(String id) {
        String name = editUserLayoutBinding.etName.getText().toString().trim();
        String mobile = editUserLayoutBinding.etMobile.getText().toString().trim();
        String address = editUserLayoutBinding.etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            JitterUtils.start(editUserLayoutBinding.etName);
            return;
        }
        if (TextUtils.isEmpty(mobile)) {
            JitterUtils.start(editUserLayoutBinding.etMobile);
            return;
        }
        if (mobile.length() != 11) {
            ToastUtils.toast(mContext.getResources().getString(R.string.str_qsrsywsjhm));
            JitterUtils.start(editUserLayoutBinding.etMobile);
            return;
        }
        if (TextUtils.isEmpty(address)) {
            JitterUtils.start(editUserLayoutBinding.etAddress);
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("contactName", name);
        map.put("contactPhone", mobile);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("districtId", districtId);
        map.put("address", address);
        map.put("sign", userTypeId);//0=普通客户，1=潜在意向客户，2=意向客户
        usereditCustomer(map);
    }

    private String provinceId;
    private String cityId;
    private String districtId;

    @Override
    public void setAddressBean(AddressCallbackBean addressBean) {
        if (addressBean != null) {
            if (addressBean.getType() == 1) {
                provinceId = addressBean.getId();
                editUserLayoutBinding.tvProvince.setText(addressBean.getName());
                cityId = null;
                districtId = null;
                editUserLayoutBinding.tvCity.setText("");
                editUserLayoutBinding.tvCounty.setText("");
            } else if (addressBean.getType() == 2) {
                cityId = addressBean.getId();
                editUserLayoutBinding.tvCity.setText(addressBean.getName());
                districtId = null;
                editUserLayoutBinding.tvCounty.setText("");
            } else if (addressBean.getType() == 3) {
                districtId = addressBean.getId();
                editUserLayoutBinding.tvCounty.setText(addressBean.getName());
            }
        }
    }

    @Override
    public void dissMiss() {
        dialogEditUser.dismiss();
    }
}