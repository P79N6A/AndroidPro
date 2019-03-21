package com.shan.netlibrary.bean;

import com.shan.netlibrary.net.BaseBean;

/**
 * Created by root on 18-11-10.
 */

public class YqxLoginBean extends BaseBean {

    /**
     * data : {"status":0,"info":"success","data":{"user_id":14046,"expire":"2018-11-23 09:11:56","designer":1,"token":"27764b9c0cd455ec595f7a982f5b679d1"},"jsonp":""}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * status : 0
         * info : success
         * data : {"user_id":14046,"expire":"2018-11-23 09:11:56","designer":1,"token":"27764b9c0cd455ec595f7a982f5b679d1"}
         * jsonp :
         */

        private int status;
        private String info;
        private DataBean data;
        private String jsonp;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getJsonp() {
            return jsonp;
        }

        public void setJsonp(String jsonp) {
            this.jsonp = jsonp;
        }

        public static class DataBean {
            /**
             * user_id : 14046
             * expire : 2018-11-23 09:11:56
             * designer : 1
             * token : 27764b9c0cd455ec595f7a982f5b679d1
             */

            private int user_id;
            private String expire;
            private int designer;
            private String token;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getExpire() {
                return expire;
            }

            public void setExpire(String expire) {
                this.expire = expire;
            }

            public int getDesigner() {
                return designer;
            }

            public void setDesigner(int designer) {
                this.designer = designer;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
    }
}
