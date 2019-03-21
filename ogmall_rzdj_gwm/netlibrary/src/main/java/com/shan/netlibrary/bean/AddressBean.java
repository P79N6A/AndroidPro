package com.shan.netlibrary.bean;

import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 18-11-16.
 */

public class AddressBean extends BaseBean {
    private List<DataBean> data;

    public List<DataBean> getData() {        if (data == null) {            data = new ArrayList<>();        }        return data;    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * fullletter : aomentebiexingzhengqu
         * id : 36
         * ishot : 0
         * letter : a
         * name : 澳门特别行政区
         * provinceId : 31
         * cityId : 310
         */

        private String fullletter;
        private int id;
        private int ishot;
        private String letter;
        private String name;
        private int provinceId;
        private int cityId;

        public String getFullletter() {
            return fullletter;
        }

        public void setFullletter(String fullletter) {
            this.fullletter = fullletter;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIshot() {
            return ishot;
        }

        public void setIshot(int ishot) {
            this.ishot = ishot;
        }

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }
    }
}
