package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * 产品分享
 * Created by chenjunshan on 2018-09-28.
 */

public class ProductsshareBean extends BaseBean {

    /**
     * data : {"url":"http://118.24.37.192/product/product_detail_mobile/43888"}
     * message : null
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
         * url : http://118.24.37.192/product/product_detail_mobile/43888
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}