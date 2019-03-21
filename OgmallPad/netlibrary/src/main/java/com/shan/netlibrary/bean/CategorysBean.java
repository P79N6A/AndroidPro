package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取产品分类列表 categoryId 不传时，获取所有的一级分类
 * Created by chenjunshan on 2018-07-05.
 */

public class CategorysBean extends BaseBean {

    /**
     * data : [{"image":"","name":"品牌家具","id":381},{"image":null,"name":"软装家居","id":443},{"image":null,"name":"个性生活","id":486},{"image":"","name":"爆款专区","id":487},{"image":null,"name":"全屋定制","id":504}]
     * message : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        if (data == null)
            data = new ArrayList<>();
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * image :
         * name : 品牌家具
         * id : 381
         */

        private String image;
        private String name;
        private int id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
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