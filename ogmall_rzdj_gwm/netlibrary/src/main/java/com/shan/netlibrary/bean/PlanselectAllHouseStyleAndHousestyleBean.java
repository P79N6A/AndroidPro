package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询方案列表（包含平台方案列表和工厂的方案列表）
 * Created by 陈俊山 on 2018-11-30.
 */

public class PlanselectAllHouseStyleAndHousestyleBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {        if (data == null) {            data = new ArrayList<>();        }        return data;    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 平台方案
         * value : [{"id":45,"styleName":"欧式风格"},{"id":46,"styleName":"新中式风格"},{"id":47,"styleName":"田园风格"},{"id":48,"styleName":"地中海风格"},{"id":49,"styleName":"美式风格"},{"id":50,"styleName":"欧法式风格"},{"id":51,"styleName":"北欧风格"},{"id":52,"styleName":"新古典风格"},{"id":54,"styleName":"现代风格"},{"id":55,"styleName":"青花瓷"},{"id":56,"styleName":"轻奢简美"},{"id":57,"styleName":"轻奢中式"},{"id":58,"styleName":"东南亚风格"},{"id":60,"styleName":"工业风"},{"id":61,"styleName":"轻奢现代"},{"id":62,"styleName":"法式风格"},{"id":65,"styleName":"其他"}]
         */

        private String name;
        private List<ValueBean> value;
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

        public List<ValueBean> getValue() {
            if (value ==null)
                value = new ArrayList<>();
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public static class ValueBean {
            /**
             * id : 45
             * styleName : 欧式风格
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
}