package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询所有户型
 * Created by 陈俊山 on 2018-11-17.
 */

public class PlanselectAllHouseTypeBean extends BaseBean {

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
         * hallCount : 1
         * id : 1
         * roomCount : 1
         * typeName : 一室一厅
         */

        private String hallCount;
        private int id;
        private String roomCount;
        private String typeName;

        public String getHallCount() {
            return hallCount;
        }

        public void setHallCount(String hallCount) {
            this.hallCount = hallCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRoomCount() {
            return roomCount;
        }

        public void setRoomCount(String roomCount) {
            this.roomCount = roomCount;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}