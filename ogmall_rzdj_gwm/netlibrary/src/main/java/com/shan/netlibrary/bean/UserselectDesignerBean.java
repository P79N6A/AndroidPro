package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺商查询自己的设计师
 * Created by 增加设计师 on 2018-11-16.
 */

public class UserselectDesignerBean extends BaseBean {

    /**
     * count : 3
     * data : [{"address":"这个地址好帅","cityId":286,"cityName":"西安市","contactName":"某某设计师3","contactPhone":"15813861480","createTime":"2018-11-16 21:08:21","customerCount":0,"districtId":2760,"districtName":"新城区","id":173,"provinceId":29,"provinceName":"陕西省","type":10,"typeAreaAgentId":59,"typeCityAgentId":67,"typeFactoryId":56,"typeStoreId":169},{"address":"这个地址好帅","cityId":286,"cityName":"西安市","contactName":"某某设计师2","contactPhone":"15813861479","createTime":"2018-11-16 21:08:11","customerCount":0,"districtId":2760,"districtName":"新城区","id":172,"provinceId":29,"provinceName":"陕西省","type":10,"typeAreaAgentId":59,"typeCityAgentId":67,"typeFactoryId":56,"typeStoreId":169},{"address":"这个地址好帅","cityId":286,"cityName":"西安市","contactName":"某某设计师1","contactPhone":"15813861478","createTime":"2018-11-16 21:07:52","customerCount":0,"districtId":2760,"districtName":"新城区","id":171,"provinceId":29,"provinceName":"陕西省","type":10,"typeAreaAgentId":59,"typeCityAgentId":67,"typeFactoryId":56,"typeStoreId":169}]
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
         * address : 这个地址好帅
         * cityId : 286
         * cityName : 西安市
         * contactName : 某某设计师3
         * contactPhone : 15813861480
         * createTime : 2018-11-16 21:08:21
         * customerCount : 0
         * districtId : 2760
         * districtName : 新城区
         * id : 173
         * provinceId : 29
         * provinceName : 陕西省
         * type : 10
         * typeAreaAgentId : 59
         * typeCityAgentId : 67
         * typeFactoryId : 56
         * typeStoreId : 169
         */

        private String address;
        private int cityId;
        private String cityName;
        private String contactName;
        private String contactPhone;
        private String createTime;
        private String customerCount;
        private int districtId;
        private String districtName;
        private String id;
        private int provinceId;
        private String provinceName;
        private int type;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCustomerCount() {
            return customerCount;
        }

        public void setCustomerCount(String customerCount) {
            this.customerCount = customerCount;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
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