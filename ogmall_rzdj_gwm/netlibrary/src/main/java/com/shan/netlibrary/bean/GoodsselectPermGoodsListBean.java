package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询商品规格
 * Created by 陈俊山 on 2018-12-11.
 */

public class GoodsselectPermGoodsListBean extends BaseBean {

    /**
     * count : 4
     * data : [{"attributeList":[{"name":"认真冯绍峰","value":"好像很喜欢"}],"auditInfo":"","categoryName":"三级分类1","createTime":"2018-12-12 13:55:17","goodsCategoryId":40,"headImage":"http://images.ogmall.com/243890fe-23cb-42ce-b8d2-be1bdbfa5f5b","id":15,"name":"第三方算法","sellStatus":0,"sellStatusName":"下架","specNameList":[],"status":3,"statusName":"待提交","subTitle":"就是近十年","unit":"盏","updateTime":"2018-12-12 13:55:17"},{"attributeList":[{"name":"反对冯绍峰","value":"VC BBC"}],"auditInfo":"","categoryName":"三级分类1","createTime":"2018-12-12 13:53:55","goodsCategoryId":40,"headImage":"http://images.ogmall.com/10a0e114-02e4-4531-8b5c-5136af2390c8","id":14,"name":"咕嘟咕嘟个","sellStatus":0,"sellStatusName":"下架","specNameList":[],"status":3,"statusName":"待提交","subTitle":"附加费解放军","unit":"套","updateTime":"2018-12-12 13:53:55"},{"attributeList":[{"name":"V嘘嘘VV恤","value":"V嘘嘘"}],"auditInfo":"","categoryName":"三级分类4","createTime":"2018-12-12 13:49:33","goodsCategoryId":50,"headImage":"http://images.ogmall.com/036871fc-90dd-4b31-8604-9e1733afde47","id":13,"name":"咕嘟咕嘟个个","sellStatus":0,"sellStatusName":"下架","specNameList":[],"status":3,"statusName":"待提交","subTitle":"好多好多好多话","unit":"件","updateTime":"2018-12-12 13:49:33"},{"attribute":"","auditInfo":"","categoryName":"三级分类1","createTime":"2018-12-11 19:36:13","goodsCategoryId":40,"headImage":"http://images.ogmall.com/6015018b-eeac-4dcb-87b6-77d622c7aead","id":10,"name":"古驰-1","sellStatus":0,"sellStatusName":"下架","specNameList":[],"status":1,"statusName":"审核通过","subTitle":"古驰-1-sub","unit":"个","updateTime":"2018-12-11 19:36:13"}]
     */

    private int count;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * attributeList : [{"name":"认真冯绍峰","value":"好像很喜欢"}]
         * auditInfo :
         * categoryName : 三级分类1
         * createTime : 2018-12-12 13:55:17
         * goodsCategoryId : 40
         * headImage : http://images.ogmall.com/243890fe-23cb-42ce-b8d2-be1bdbfa5f5b
         * id : 15
         * name : 第三方算法
         * sellStatus : 0
         * sellStatusName : 下架
         * specNameList : []
         * status : 3
         * statusName : 待提交
         * subTitle : 就是近十年
         * unit : 盏
         * updateTime : 2018-12-12 13:55:17
         * attribute :
         */

        private String auditInfo;
        private String categoryName;
        private String createTime;
        private int goodsCategoryId;
        private String headImage;
        private int id;
        private String name;
        private int sellStatus;
        private String sellStatusName;
        private int status;
        private String statusName;
        private String subTitle;
        private String unit;
        private String updateTime;
        private String attribute;
        private List<AttributeListBean> attributeList;
        private List<String> specNameList;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getAuditInfo() {
            return auditInfo;
        }

        public void setAuditInfo(String auditInfo) {
            this.auditInfo = auditInfo;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getGoodsCategoryId() {
            return goodsCategoryId;
        }

        public void setGoodsCategoryId(int goodsCategoryId) {
            this.goodsCategoryId = goodsCategoryId;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
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

        public int getSellStatus() {
            return sellStatus;
        }

        public void setSellStatus(int sellStatus) {
            this.sellStatus = sellStatus;
        }

        public String getSellStatusName() {
            return sellStatusName;
        }

        public void setSellStatusName(String sellStatusName) {
            this.sellStatusName = sellStatusName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getUnit() {
            if (unit == null)
                unit = "";
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public List<AttributeListBean> getAttributeList() {
            if (attributeList == null)
                attributeList = new ArrayList<>();
            return attributeList;
        }

        public void setAttributeList(List<AttributeListBean> attributeList) {
            this.attributeList = attributeList;
        }

        public List<String> getSpecNameList() {
            if (specNameList == null)
                specNameList = new ArrayList<>();
            return specNameList;
        }

        public void setSpecNameList(List<String> specNameList) {
            this.specNameList = specNameList;
        }

        public static class AttributeListBean implements Serializable{
            /**
             * name : 认真冯绍峰
             * value : 好像很喜欢
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}