package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺商查询自己的指定设计师其下的客户
 * Created by 增加设计师 on 2018-11-16.
 */

public class UserselectCustomerOfDesignerBean extends BaseBean {

    /**
     * count : 1
     * data : [{"address":"客户地址","cityId":208,"cityName":"河源市","contactName":"客户姓名1","contactPhone":"1466516516","createAccountId":171,"createAccountName":"某某设计师1","createAccountType":"10","createTime":"2018-11-16 22:00:20","districtId":2034,"districtName":"源城区","id":40,"provinceId":21,"provinceName":"广东省","sign":2,"signName":"意向客户"}]
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

    public static class DataBean {
        /**
         * address : 客户地址
         * cityId : 208
         * cityName : 河源市
         * contactName : 客户姓名1
         * contactPhone : 1466516516
         * createAccountId : 171
         * createAccountName : 某某设计师1
         * createAccountType : 10
         * createTime : 2018-11-16 22:00:20
         * districtId : 2034
         * districtName : 源城区
         * id : 40
         * provinceId : 21
         * provinceName : 广东省
         * sign : 2
         * signName : 意向客户
         */

        private String address;
        private int cityId;
        private String cityName;
        private String contactName;
        private String contactPhone;
        private int createAccountId;
        private String createAccountName;
        private String createAccountType;
        private String createTime;
        private int districtId;
        private String districtName;
        private int id;
        private int provinceId;
        private String provinceName;
        private String sign;
        private String signName;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
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

        public int getCreateAccountId() {
            return createAccountId;
        }

        public void setCreateAccountId(int createAccountId) {
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

        public int getDistrictId() {
            return districtId;
        }

        public void setDistrictId(int districtId) {
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

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
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