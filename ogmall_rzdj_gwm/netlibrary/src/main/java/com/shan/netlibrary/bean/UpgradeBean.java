package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * 软件升级
 * Created by chenjunshan on 17-5-3.
 */

public class UpgradeBean extends BaseBean {


    /**
     * data : {"versionCode":201810151,"isForced":true,"introduce":"测试","versionName":"1.2","updateUrl":"http://192.168.1.120:8080/ogtablet/vir/apk/15426026609048357.apk"}
     */

    private DataBean data;

    public DataBean getData() {        if (data == null) {            data = new DataBean();        }        return data;    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * versionCode : 201810151
         * isForced : true
         * introduce : 测试
         * versionName : 1.2
         * updateUrl : http://192.168.1.120:8080/ogtablet/vir/apk/15426026609048357.apk
         */

        private int versionCode;
        private boolean isForced;
        private String introduce;
        private String versionName;
        private String updateUrl;

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public boolean isIsForced() {
            return isForced;
        }

        public void setIsForced(boolean isForced) {
            this.isForced = isForced;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }

        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }
    }
}
