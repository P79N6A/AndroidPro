package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * 根据用户id 获取获取设计师用户数据！
 * Created by chenjunshan on 2018-08-20.
 */

public class DesignerofUserIdBean extends BaseBean {

    /**
     * data : {"designerId":2676,"headImage":"http://images.ogmall.com/bffc4cec-c48b-410e-97b6-c8b177da2fbe","workAge":3,"cityId":"深圳市","provinceName":"广东省","goodAtStyle":"现代风格","userName":"测试设计师","userId":5508,"provinceId":197}
     * message : null
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * designerId : 2676
         * headImage : http://images.ogmall.com/bffc4cec-c48b-410e-97b6-c8b177da2fbe
         * workAge : 3
         * cityId : 深圳市
         * provinceName : 广东省
         * goodAtStyle : 现代风格
         * userName : 测试设计师
         * userId : 5508
         * provinceId : 197
         */

        private int designerId;
        private String headImage;
        private int workAge;
        private String cityName;
        private String provinceName;
        private String goodAtStyle;
        private String userName;
        private int userId;
        private int provinceId;

        public int getDesignerId() {
            return designerId;
        }

        public void setDesignerId(int designerId) {
            this.designerId = designerId;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public int getWorkAge() {
            return workAge;
        }

        public void setWorkAge(int workAge) {
            this.workAge = workAge;
        }

        public String getCityName() {
            if (cityName == null)
                cityName = "";
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getProvinceName() {
            if (provinceName == null)
                return "";
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getGoodAtStyle() {
            return goodAtStyle;
        }

        public void setGoodAtStyle(String goodAtStyle) {
            this.goodAtStyle = goodAtStyle;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }
    }
}