package com.shan.netlibrary.bean;


import android.text.TextUtils;

import com.shan.netlibrary.net.BaseBean;

/**
 * 业务员登录
 * Created by chenjunshan on 2018-07-05.
 */

public class UserloginBean extends BaseBean {

    /**
     * data : {"realName":null,"userImage":null,"userName":"15919832817","userId":5104,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1MTA0IiwiZXhwIjoxNTMxMzg5NzAzLCJpYXQiOjE1MzA3ODQ5MDN9.TRq_CeLEK5RCN_6hIWKYKljrrogpb9LXZ7Iv1rHELuk"}
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
         * realName : null
         * userImage : null
         * userName : 15919832817
         * userId : 5104
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1MTA0IiwiZXhwIjoxNTMxMzg5NzAzLCJpYXQiOjE1MzA3ODQ5MDN9.TRq_CeLEK5RCN_6hIWKYKljrrogpb9LXZ7Iv1rHELuk
         */

        private String realName;
        private String userImage;
        private String userName;
        private int userId;
        private String token;
        private String userType;
        private String typeName;

        /**
         * Normal(0,2.2D,"普通用户"),
         * Manager(2,1.0D,"管理员"),
         * //vip设计师1.15普通设计师为2.2
         * Designer(4,1.15D,"设计师"),
         * City(5,1D,"运营商"),
         * Factory(6,2.2D,"工厂"),
         * DecorateCompany(7,1.1D,"装修公司"),
         * Seed(8,2.2D,"种子用户"),
         * Test(9,2.2D,"测试用户"),
         * Employee(10,2.2D,"欧工员工"),
         * OgSchool(11,2.2D,"欧工软装学院");
         */
        public String getTypeName() {
            if (userType != null) {
                if (userType.equals("Normal")) {
                    typeName = "普通用户";
                } else if (userType.equals("Manager")) {
                    typeName = "管理员";
                } else if (userType.equals("Designer")) {
                    typeName = "设计师";
                } else if (userType.equals("Manager")) {
                    typeName = "管理员";
                } else if (userType.equals("City")) {
                    typeName = "运营商";
                } else if (userType.equals("Factory")) {
                    typeName = "工厂";
                } else if (userType.equals("DecorateCompany")) {
                    typeName = "装修公司";
                } else if (userType.equals("Seed")) {
                    typeName = "种子用户";
                } else if (userType.equals("Test")) {
                    typeName = "测试用户";
                } else if (userType.equals("Employee")) {
                    typeName = "欧工员工";
                } else if (userType.equals("OgSchool")) {
                    typeName = "欧工软装学院";
                } else {
                    typeName = "";
                }
            } else {
                typeName = "";
            }
            return typeName;
        }

        public String getUserType() {
            if (userType == null)
                return "";
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getRealName() {
            if (TextUtils.isEmpty(realName)) {
                realName = userName;
            }
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}