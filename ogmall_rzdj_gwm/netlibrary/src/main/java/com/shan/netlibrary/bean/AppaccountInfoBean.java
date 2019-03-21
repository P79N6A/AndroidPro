package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * 获取平板登录信息
 * Created by 陈俊山 on 2018-11-13.
 */

public class AppaccountInfoBean extends BaseBean {

    /**
     * data : {"address":"地址","cityAgentCityId":203,"cityAgentProvinceId":21,"cityId":208,"cityName":"河源市","collectCount":0,"contactName":"市代理1_1_3","contactPhone":"13800138113","createTime":"2018-11-08 19:21:06","customerCount":0,"designerCount":0,"districtId":2034,"districtName":"源城区","id":67,"picture":"http://images.ogmall.com/7699842a-f2c0-4e49-9d68-efe292c622d3","planCount":19,"provinceId":21,"provinceName":"广东省","storeCount":0,"type":30,"typeAreaAgentId":59,"typeFactoryId":56,"typeName":"市级代理用户"}
     */

    private DataBean data;

    public DataBean getData() {
        if (data == null)
            data = new DataBean();
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : 地址
         * cityAgentCityId : 203
         * cityAgentProvinceId : 21
         * cityId : 208
         * cityName : 河源市
         * collectCount : 0
         * contactName : 市代理1_1_3
         * contactPhone : 13800138113
         * createTime : 2018-11-08 19:21:06
         * customerCount : 0
         * designerCount : 0
         * districtId : 2034
         * districtName : 源城区
         * id : 67
         * picture : http://images.ogmall.com/7699842a-f2c0-4e49-9d68-efe292c622d3
         * planCount : 19
         * provinceId : 21
         * provinceName : 广东省
         * storeCount : 0
         * type : 30
         * typeAreaAgentId : 59
         * typeFactoryId : 56
         * typeName : 市级代理用户
         */

        private String address;
        private int cityAgentCityId;
        private int cityAgentProvinceId;
        private String cityId;
        private String cityName;
        private int collectCount;
        private String contactName;
        private String contactPhone;
        private String createTime;
        private int customerCount;
        private int designerCount;
        private String districtId;
        private String districtName;
        private int id;
        private String picture;
        private int planCount;
        private String provinceId;
        private String provinceName;
        private int storeCount;
        private int type;
        private int typeAreaAgentId;
        private int typeFactoryId;
        private String typeName;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCityAgentCityId() {
            return cityAgentCityId;
        }

        public void setCityAgentCityId(int cityAgentCityId) {
            this.cityAgentCityId = cityAgentCityId;
        }

        public int getCityAgentProvinceId() {
            return cityAgentProvinceId;
        }

        public void setCityAgentProvinceId(int cityAgentProvinceId) {
            this.cityAgentProvinceId = cityAgentProvinceId;
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

        public int getCollectCount() {
            return collectCount;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getCustomerCount() {
            return customerCount;
        }

        public void setCustomerCount(int customerCount) {
            this.customerCount = customerCount;
        }

        public int getDesignerCount() {
            return designerCount;
        }

        public void setDesignerCount(int designerCount) {
            this.designerCount = designerCount;
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

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getPlanCount() {
            return planCount;
        }

        public void setPlanCount(int planCount) {
            this.planCount = planCount;
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

        public int getStoreCount() {
            return storeCount;
        }

        public void setStoreCount(int storeCount) {
            this.storeCount = storeCount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTypeAreaAgentId() {
            return typeAreaAgentId;
        }

        public void setTypeAreaAgentId(int typeAreaAgentId) {
            this.typeAreaAgentId = typeAreaAgentId;
        }

        public int getTypeFactoryId() {
            return typeFactoryId;
        }

        public void setTypeFactoryId(int typeFactoryId) {
            this.typeFactoryId = typeFactoryId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}