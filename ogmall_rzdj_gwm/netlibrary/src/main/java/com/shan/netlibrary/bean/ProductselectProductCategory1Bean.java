package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询商品一级分类（白名单接口）
 * Created by 陈俊山 on 2018-11-18.
 */

public class ProductselectProductCategory1Bean extends BaseBean {

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
         * category1 : 443
         * name : 软装家居
         */

        private int category1;
        private String name;
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getCategory1() {
            return category1;
        }

        public void setCategory1(int category1) {
            this.category1 = category1;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "category1=" + category1 +
                    ", name='" + name + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProductselectProductCategory1Bean{" +
                "data=" + data +
                '}';
    }
}