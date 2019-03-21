package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取魔镜对应的风格展示列表
 * Created by chenjunshan on 2018-05-29.
 */

public class StylesmirrorBean extends BaseBean {

    /**
     * data : [{"name":"欧式风格","id":45},{"name":"新中式风格","id":46},{"name":"田园风格","id":47},{"name":"地中海风格","id":48},{"name":"美式风格","id":49},{"name":"欧法式风格","id":50},{"name":"北欧风格","id":51},{"name":"新古典风格","id":52},{"name":"儿童家具","id":53},{"name":"现代风格","id":54},{"name":"青花瓷","id":55},{"name":"轻奢简美","id":56},{"name":"轻奢中式","id":57},{"name":"东南亚风格","id":58},{"name":"户外家具","id":59},{"name":"工业风","id":60},{"name":"轻奢现代","id":61},{"name":"法式风格","id":62},{"name":"办公家具","id":63},{"name":"软体类","id":64},{"name":"其他","id":65}]
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
         * name : 欧式风格
         * id : 45
         */

        private String name;
        private int id;
        private boolean isSelect;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
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