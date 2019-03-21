package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * 软件升级
 * Created by chenjunshan on 17-5-3.
 */

public class UpgradeBean extends BaseBean {

    /**
     * data : {"introduce":"测试","versionName":"1.0","uri":"apk/download?fileName=Ogmallpad.apk","versionCode":2018871,"isForced":true}
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
         * introduce : 测试
         * versionName : 1.0
         * uri : apk/download?fileName=Ogmallpad.apk
         * versionCode : 2018871
         * isForced : true
         */

        private String introduce;
        private String versionName;
        private String uri;
        private int versionCode;
        private boolean isForced;

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

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

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
    }
}
