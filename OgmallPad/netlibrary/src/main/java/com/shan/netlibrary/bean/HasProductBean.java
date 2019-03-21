package com.shan.netlibrary.bean;

import com.shan.netlibrary.net.BaseBean;

import java.util.List;

/**
 * Created by root on 18-8-8.
 */

public class HasProductBean extends BaseBean{

    /**
     * data : [{"image":null,"name":"床","id":383},{"image":null,"name":"床垫","id":384},{"image":null,"name":"衣柜","id":385},{"image":null,"name":"床尾凳","id":386},{"image":null,"name":"穿衣镜","id":387},{"image":null,"name":"休闲椅","id":388},{"image":null,"name":"衣帽架","id":389},{"image":null,"name":"梳妆台/妆镜/妆凳","id":390},{"image":null,"name":"斗柜","id":391},{"image":null,"name":"脚凳","id":392},{"image":null,"name":"床头柜","id":534},{"image":null,"name":"其他","id":542}]
     * message : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * image : null
         * name : 床
         * id : 383
         */

        private Object image;
        private String name;
        private int id;
        public int parentId;

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
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
