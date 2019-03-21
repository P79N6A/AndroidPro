package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据一级分类查询二级分类（白名单接口）
 * Created by 陈俊山 on 2018-11-18.
 */

public class ProductselectCategory2ByCategory1Bean extends BaseBean {

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
         * name : 灯具
         * id : 444
         */

        private String name;
        private int id;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}