package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询拎包入住分类
 * Created by 陈俊山 on 2018-11-22.
 */

public class HandbagselectHandbagCategoryBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 卧室家具
         * value : [{"id":383,"name":"床"},{"id":384,"name":"床垫"},{"id":385,"name":"衣柜"},{"id":386,"name":"床尾凳"},{"id":387,"name":"穿衣镜"},{"id":388,"name":"休闲椅"},{"id":389,"name":"衣帽架"},{"id":390,"name":"梳妆台/妆镜/妆凳"},{"id":391,"name":"斗柜"},{"id":392,"name":"脚凳"},{"id":534,"name":"床头柜"},{"id":542,"name":"其他"}]
         */

        private String name;
        private List<ValueBean> value;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ValueBean> getValue() {
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public static class ValueBean {
            /**
             * id : 383
             * name : 床
             */

            private int id;
            private String name;
            private boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}