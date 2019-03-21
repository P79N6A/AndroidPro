package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * 上传图片
 * Created by 陈俊山 on 2018-11-13.
 */

public class AppuploadImgBean extends BaseBean {

    /**
     * data : {"path":"http://192.168.1.120:8080/ogtablet/img/temp/15423387952735483.png"}
     */

    private DataBean data;

    public DataBean getData() {        if (data == null) {            data = new DataBean();        }        return data;    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * path : http://192.168.1.120:8080/ogtablet/img/temp/15423387952735483.png
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}