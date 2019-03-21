package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.List;

/**
 * 获取设计师方案风格和方案列表
 * Created by chenjunshan on 2018-08-20.
 */

public class DesignstylesOfdesignerBean extends BaseBean {

    /**
     * data : [{"styleId":54,"styleName":"现代风格"},{"styleId":51,"styleName":"北欧风格"},{"styleId":56,"styleName":"轻奢简美"},{"styleId":46,"styleName":"新中式风格"},{"styleId":49,"styleName":"美式风格"},{"styleId":61,"styleName":"轻奢现代"},{"styleId":45,"styleName":"欧式风格"}]
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
         * styleId : 54
         * styleName : 现代风格
         */

        private int styleId;
        private String styleName;

        public int getStyleId() {
            return styleId;
        }

        public void setStyleId(int styleId) {
            this.styleId = styleId;
        }

        public String getStyleName() {
            return styleName;
        }

        public void setStyleName(String styleName) {
            this.styleName = styleName;
        }
    }
}