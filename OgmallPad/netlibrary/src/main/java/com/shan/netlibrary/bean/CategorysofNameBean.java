package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据分类名称搜索分类信息
 * Created by chenjunshan on 2018-07-05.
 */

public class CategorysofNameBean extends BaseBean {

    /**
     * data : [{"image":null,"name":"爆款专区","id":487},{"image":null,"name":"爆款专区","id":590}]
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
         * image : null
         * name : 爆款专区
         * id : 487
         */

        private Object image;
        private String name;
        private int id;

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