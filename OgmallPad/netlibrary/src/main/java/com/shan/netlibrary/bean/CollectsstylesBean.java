package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.List;

/**
 * 获取风格列表
 * Created by chenjunshan on 2018-09-27.
 */

public class CollectsstylesBean extends BaseBean {

    /**
     * data : [{"name":"现代风格","id":54},{"name":"北欧风格","id":51},{"name":"欧法式风格","id":50},{"name":"美式风格","id":49},{"name":"新中式风格","id":46}]
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
         * name : 现代风格
         * id : 54
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