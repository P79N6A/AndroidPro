package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * 根据三级分类查出对应的一级，二级，三级分类
 * Created by 陈俊山 on 2018-12-14.
 */

public class GoodsselectCategoryByCategory3IdBean extends BaseBean {

    /**
     * data : {"category3Name":"三级分类4","category1Name":"一级分类1","category2Id":48,"category2Name":"二级分类5","category3Id":50,"category1Id":33}
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
         * category3Name : 三级分类4
         * category1Name : 一级分类1
         * category2Id : 48
         * category2Name : 二级分类5
         * category3Id : 50
         * category1Id : 33
         */

        private String category3Name;
        private String category1Name;
        private int category2Id;
        private String category2Name;
        private int category3Id;
        private int category1Id;

        public String getCategory3Name() {
            return category3Name;
        }

        public void setCategory3Name(String category3Name) {
            this.category3Name = category3Name;
        }

        public String getCategory1Name() {
            return category1Name;
        }

        public void setCategory1Name(String category1Name) {
            this.category1Name = category1Name;
        }

        public int getCategory2Id() {
            return category2Id;
        }

        public void setCategory2Id(int category2Id) {
            this.category2Id = category2Id;
        }

        public String getCategory2Name() {
            return category2Name;
        }

        public void setCategory2Name(String category2Name) {
            this.category2Name = category2Name;
        }

        public int getCategory3Id() {
            return category3Id;
        }

        public void setCategory3Id(int category3Id) {
            this.category3Id = category3Id;
        }

        public int getCategory1Id() {
            return category1Id;
        }

        public void setCategory1Id(int category1Id) {
            this.category1Id = category1Id;
        }
    }
}