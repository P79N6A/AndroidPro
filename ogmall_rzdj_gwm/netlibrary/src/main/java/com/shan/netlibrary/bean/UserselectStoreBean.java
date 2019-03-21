package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 市代理查询自己的店铺
 * Created by 增加设计师 on 2018-11-16.
 */

public class UserselectStoreBean extends BaseBean {

    /**
     * count : 1
     * data : [{"address":"更多更多更多","cityId":314,"cityName":"海南藏族自治州","contactName":"还是","contactPhone":"1342525253553","createTime":"2018-11-16 19:12:48","districtId":2998,"districtName":"贵德县","id":164,"provinceId":31,"provinceName":"青海省","storeName":"彼此彼此不","type":20,"typeAreaAgentId":59,"typeCityAgentId":67,"typeFactoryId":56}]
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
         * address : 更多更多更多
         * cityId : 314
         * cityName : 海南藏族自治州
         * contactName : 还是
         * contactPhone : 1342525253553
         * createTime : 2018-11-16 19:12:48
         * districtId : 2998
         * districtName : 贵德县
         * id : 164
         * provinceId : 31
         * provinceName : 青海省
         * storeName : 彼此彼此不
         * type : 20
         * typeAreaAgentId : 59
         * typeCityAgentId : 67
         * typeFactoryId : 56
         */

        private String address;
        private int cityId;
        private String cityName;
        private String contactName;
        private String contactPhone;
        private String createTime;
        private int districtId;
        private String districtName;
        private String id;
        private int provinceId;
        private String provinceName;
        private String storeName;
        private int type;
        private int typeAreaAgentId;
        private int typeCityAgentId;
        private int typeFactoryId;

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

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
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
    }
}