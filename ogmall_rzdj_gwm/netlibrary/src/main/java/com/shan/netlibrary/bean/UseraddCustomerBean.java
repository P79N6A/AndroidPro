package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * 增加客户
 * Created by 陈俊山 on 2018-11-16.
 */

public class UseraddCustomerBean extends BaseBean {

    /**
     * data : {"address":"8？IO？guilguil","cityId":302,"contactName":"发给你系统","contactPhone":"52617261261","createAccountId":169,"createTime":"2018-11-20 10:34:52","districtId":2913,"id":46,"provinceId":30,"sign":0,"typeAreaAgentId":59,"typeCityAgentId":67,"typeFactoryId":56,"typeStoreId":169}
     */

    private DataBean data;

    public DataBean getData() {        if (data == null) {            data = new DataBean();        }        return data;    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 8？IO？guilguil
         * cityId : 302
         * contactName : 发给你系统
         * contactPhone : 52617261261
         * createAccountId : 169
         * createTime : 2018-11-20 10:34:52
         * districtId : 2913
         * id : 46
         * provinceId : 30
         * sign : 0
         * typeAreaAgentId : 59
         * typeCityAgentId : 67
         * typeFactoryId : 56
         * typeStoreId : 169
         */

        private String address;
        private int cityId;
        private String contactName;
        private String contactPhone;
        private int createAccountId;
        private String createTime;
        private int districtId;
        private int id;
        private int provinceId;
        private int sign;
        private int typeAreaAgentId;
        private int typeCityAgentId;
        private int typeFactoryId;
        private int typeStoreId;

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

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        public int getTypeAreaAgentId() {
            return typeAreaAgentId;
        }

        public void setTypeAreaAgentId(int typeAreaAgentId) {
            this.typeAreaAgentId = typeAreaAgentId;
        }

        public int getTypeCityAgentId() {
            return typeCityAgentId;
        }

        public void setTypeCityAgentId(int typeCityAgentId) {
            this.typeCityAgentId = typeCityAgentId;
        }

        public int getTypeFactoryId() {
            return typeFactoryId;
        }

        public void setTypeFactoryId(int typeFactoryId) {
            this.typeFactoryId = typeFactoryId;
        }

        public int getTypeStoreId() {
            return typeStoreId;
        }

        public void setTypeStoreId(int typeStoreId) {
            this.typeStoreId = typeStoreId;
        }
    }
}