package com.shan.netlibrary.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 18-8-17.
 */

public class UrlBean {
    private List<DataBean> data;

    public List<DataBean> getData() {
        if (data == null)
            data = new ArrayList<>();
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * url : http://318.ogmall.com
         * isCheck : false
         */

        private String url;
        private boolean isCheck;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isIsCheck() {
            return isCheck;
        }

        public void setIsCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }
    }
}
