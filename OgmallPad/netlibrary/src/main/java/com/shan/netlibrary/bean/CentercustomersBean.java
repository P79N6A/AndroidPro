package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 获取用户列表
 * Created by chenjunshan on 2018-07-05.
 */

public class CentercustomersBean extends BaseBean {


    /**
     * data : {"list":[{"customerId":1,"customerMobile":"15918884628","userId":5104,"customerName":"老王"},{"customerId":2,"customerMobile":"15918884628","userId":5104,"customerName":"老王"},{"customerId":3,"customerMobile":"15918884628","userId":5104,"customerName":"老刘"},{"customerId":4,"customerMobile":"15918884628","userId":5104,"customerName":"小刘"},{"customerId":5,"customerMobile":"15918884628","userId":5104,"customerName":"小刘"},{"customerId":6,"customerMobile":"15919832817","userId":5104,"customerName":"小王"},{"customerId":7,"customerMobile":"15919832817","userId":5104,"customerName":"小王"},{"customerId":8,"customerMobile":null,"userId":5104,"customerName":"小杨"},{"customerId":9,"customerMobile":null,"userId":5104,"customerName":"小杨"},{"customerId":10,"customerMobile":"13751023915","userId":5104,"customerName":"刚刚还"},{"customerId":11,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":12,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":13,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":14,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":15,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":16,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":17,"customerMobile":"13751023915","userId":5104,"customerName":"该公司公司"},{"customerId":18,"customerMobile":"13751023915","userId":5104,"customerName":"该公司公司"},{"customerId":19,"customerMobile":"13751023915","userId":5104,"customerName":"刚刚说过"}],"pageResult":{"pageNum":1,"pageSize":20,"size":19,"total":19,"pageList":[],"totalPage":1}}
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

    public static class DataBean  implements Serializable{
        /**
         * list : [{"customerId":1,"customerMobile":"15918884628","userId":5104,"customerName":"老王"},{"customerId":2,"customerMobile":"15918884628","userId":5104,"customerName":"老王"},{"customerId":3,"customerMobile":"15918884628","userId":5104,"customerName":"老刘"},{"customerId":4,"customerMobile":"15918884628","userId":5104,"customerName":"小刘"},{"customerId":5,"customerMobile":"15918884628","userId":5104,"customerName":"小刘"},{"customerId":6,"customerMobile":"15919832817","userId":5104,"customerName":"小王"},{"customerId":7,"customerMobile":"15919832817","userId":5104,"customerName":"小王"},{"customerId":8,"customerMobile":null,"userId":5104,"customerName":"小杨"},{"customerId":9,"customerMobile":null,"userId":5104,"customerName":"小杨"},{"customerId":10,"customerMobile":"13751023915","userId":5104,"customerName":"刚刚还"},{"customerId":11,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":12,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":13,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":14,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":15,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":16,"customerMobile":"13751023915","userId":5104,"customerName":"色的的"},{"customerId":17,"customerMobile":"13751023915","userId":5104,"customerName":"该公司公司"},{"customerId":18,"customerMobile":"13751023915","userId":5104,"customerName":"该公司公司"},{"customerId":19,"customerMobile":"13751023915","userId":5104,"customerName":"刚刚说过"}]
         * pageResult : {"pageNum":1,"pageSize":20,"size":19,"total":19,"pageList":[],"totalPage":1}
         */

        private PageResultBean pageResult;
        private List<ListBean> list;

        public PageResultBean getPageResult() {
            if (pageResult == null)
                pageResult = new PageResultBean();
            return pageResult;
        }

        public void setPageResult(PageResultBean pageResult) {
            this.pageResult = pageResult;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageResultBean  implements Serializable{
            /**
             * pageNum : 1
             * pageSize : 20
             * size : 19
             * total : 19
             * pageList : []
             * totalPage : 1
             */

            private int pageNum;
            private int pageSize;
            private int size;
            private int total;
            private int totalPage;
            private List<?> pageList;

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public List<?> getPageList() {
                return pageList;
            }

            public void setPageList(List<?> pageList) {
                this.pageList = pageList;
            }
        }

        public static class ListBean implements Serializable{
            /**
             * customerId : 1
             * customerMobile : 15918884628
             * userId : 5104
             * customerName : 老王
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
}