package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 平板底部的方案里的风格列表查询
 * Created by 陈俊山 on 2018-11-17.
 */

public class PlanselectAllHouseStyleBean extends BaseBean {

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
         * id : 1
         * styleName : 新中式风格
         */

        private int id;
        private String styleName;
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

        public String getStyleName() {
            return styleName;
        }

        public void setStyleName(String styleName) {
            this.styleName = styleName;
        }
    }
}