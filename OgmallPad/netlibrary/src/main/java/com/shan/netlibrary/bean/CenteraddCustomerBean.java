package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * 添加客户,同时建立与业务员的会话
 * Created by chenjunshan on 2018-07-05.
 */

public class CenteraddCustomerBean extends BaseBean {

    /**
     * data : {"customerId":10,"customerMobile":"13751023915","userId":5104,"customerName":"刚刚还"}
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
         * customerId : 10
         * customerMobile : 13751023915
         * userId : 5104
         * customerName : 刚刚还
         */

        private int customerId;
        private String customerMobile;
        private int userId;
        private String customerName;

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public String getCustomerMobile() {
            return customerMobile;
        }

        public void setCustomerMobile(String customerMobile) {
            this.customerMobile = customerMobile;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }
    }
}