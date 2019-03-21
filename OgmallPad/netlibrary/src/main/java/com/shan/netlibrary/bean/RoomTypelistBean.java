package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有的户型数据
 * Created by chenjunshan on 2018-08-22.
 */

public class RoomTypelistBean extends BaseBean {

    /**
     * data : [{"id":10,"name":"一室一厅"},{"id":11,"name":"两室一厅"},{"id":12,"name":"两室两厅"},{"id":13,"name":"三室一厅"},{"id":14,"name":"三室两厅"},{"id":15,"name":"四室一厅"},{"id":16,"name":"四室两厅"},{"id":17,"name":"五室一厅"},{"id":18,"name":"五室两厅"}]
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
         * id : 10
         * name : 一室一厅
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