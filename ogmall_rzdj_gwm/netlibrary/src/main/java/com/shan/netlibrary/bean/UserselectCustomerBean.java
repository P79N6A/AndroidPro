package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询客户
 * Created by 陈俊山 on 2018-11-16.
 */

public class UserselectCustomerBean extends BaseBean {

    /**
     * count : 2
     * data : [{"address":"吃醋吃饭","cityId":328,"cityName":"博尔塔拉蒙古自治州","contactName":"才吃饭","contactPhone":"13344444443","createAccountId":67,"createAccountName":"陈俊山","createAccountType":"30","createTime":"2018-11-16 17:02:05","districtId":3074,"districtName":"温泉县","id":38,"provinceId":33,"provinceName":"新疆维吾尔自治区","sign":0,"signName":"普通客户"},{"address":"地址的首发式发生","cityId":208,"cityName":"河源市","contactName":"客户weqer","contactPhone":"133333333","createAccountId":67,"createAccountName":"陈俊山","createAccountType":"30","createTime":"2018-11-16 16:45:59","districtId":2034,"districtName":"源城区","id":37,"provinceId":21,"provinceName":"广东省","sign":0,"signName":"普通客户"}]
     */

    private int count;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * address : 吃醋吃饭
         * cityId : 328
         * cityName : 博尔塔拉蒙古自治州
         * contactName : 才吃饭
         * contactPhone : 13344444443
         * createAccountId : 67
         * createAccountName : 陈俊山
         * createAccountType : 30
         * createTime : 2018-11-16 17:02:05
         * districtId : 3074
         * districtName : 温泉县
         * id : 38
         * provinceId : 33
         * provinceName : 新疆维吾尔自治区
         * sign : 0
         * signName : 普通客户
         */

        private String address;
        private String cityId;
        private String cityName;
        private String contactName;
        private String contactPhone;
        private String createAccountId;
        private String createAccountName;
        private String createAccountType;
        private String createTime;
        private String districtId;
        private String districtName;
        private int id;
        private String provinceId;
        private String provinceName;
        private String sign;
        private String signName;

        public DataBean(UseraddCustomerBean.DataBean data) {
            address = data.getAddress();
            cityId = String.valueOf(data.getCityId());
            contactName = data.getContactName();
            contactPhone = data.getContactPhone();
            createAccountId = String.valueOf(data.getCreateAccountId());
            createTime = data.getCreateTime();
            districtId = String.valueOf(data.getDistrictId());
            id = data.getId();
            provinceId = String.valueOf(data.getProvinceId());
            sign = String.valueOf(data.getSign());
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getCreateAccountId() {
            return createAccountId;
        }

        public void setCreateAccountId(String createAccountId) {
            this.createAccountId = createAccountId;
        }

        public String getCreateAccountName() {
            return createAccountName;
        }

        public void setCreateAccountName(String createAccountName) {
            this.createAccountName = createAccountName;
        }

        public String getCreateAccountType() {
            return createAccountType;
        }

        public void setCreateAccountType(String createAccountType) {
            this.createAccountType = createAccountType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDistrictId() {
            return districtId;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(String provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSignName() {
            return signName;
        }

        public void setSignName(String signName) {
            this.signName = signName;
        }
    }
}