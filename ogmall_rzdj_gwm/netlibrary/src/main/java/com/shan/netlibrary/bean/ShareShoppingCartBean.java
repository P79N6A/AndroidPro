package com.shan.netlibrary.bean;

import com.shan.netlibrary.net.BaseBean;

/**
 * Created by root on 18-11-23.
 */

public class ShareShoppingCartBean extends BaseBean{
    /**
     * data : {"share":"http://192.168.1.120/ogtablet/app/share/6"}
     */

    private DataBean data;

    public DataBean getData() {        if (data == null) {            data = new DataBean();        }        return data;    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * share : http://192.168.1.120/ogtablet/app/share/6
         */

        private String share;

        public String getShare() {
            return share;
        }

        public void setShare(String share) {
            this.share = share;
        }
    }
}
