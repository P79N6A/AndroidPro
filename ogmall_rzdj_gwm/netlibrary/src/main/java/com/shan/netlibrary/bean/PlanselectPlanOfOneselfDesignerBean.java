package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询自己的所有设计师下的所有方案
 * Created by 陈俊山 on 2018-11-17.
 */

public class PlanselectPlanOfOneselfDesignerBean extends BaseBean {

    /**
     * count : 2
     * data : [{"accountId":169,"area":"345","auditState":1,"auditStateName":"审核通过","createAccountName":"陈俊山","createAccountType":"20","createTime":"2018-11-17 14:12:51","detailsImageList":["http://images.ogmall.com/44b7a647-2a72-43ab-9dbb-f441dcba2ed8","http://images.ogmall.com/1a57976a-89c1-4680-8496-2d99c1d53ef3","http://images.ogmall.com/70d6e68f-c07b-4986-87a0-f6d47a3715f2"],"headImage":"http://images.ogmall.com/cdc69ae6-abf4-473d-b02a-66c34a768a1f","houseStyleId":4,"houseStyleIdName":"地中海风格","houseTypeId":2,"houseTypeIdName":"二室一厅","id":34,"panoramic":false,"planName":"更多更高的","quality":false,"roomType":"第三方算法","vrurl":"www.baidu.com"},{"accountId":169,"area":"345","auditState":1,"auditStateName":"审核通过","createAccountName":"陈俊山","createAccountType":"20","createTime":"2018-11-17 13:52:09","detailsImageList":["http://images.ogmall.com/9efec32b-f37b-42d4-aff2-a3c4212d4cf3","http://images.ogmall.com/1b4d2cc8-bf76-4951-9375-6277cc8fd468"],"headImage":"http://images.ogmall.com/16058bf1-efc1-45d9-8403-6c5de73d49da","houseStyleId":3,"houseStyleIdName":"欧式风格","houseTypeId":3,"houseTypeIdName":"二室二厅","id":33,"panoramic":false,"planName":"好像很喜欢","quality":false,"roomType":"V嘘嘘","vrurl":"www.baidu.com"}]
     */

    private int count;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {        if (data == null) {            data = new ArrayList<>();        }        return data;    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * accountId : 169
         * area : 345
         * auditState : 1
         * auditStateName : 审核通过
         * createAccountName : 陈俊山
         * createAccountType : 20
         * createTime : 2018-11-17 14:12:51
         * detailsImageList : ["http://images.ogmall.com/44b7a647-2a72-43ab-9dbb-f441dcba2ed8","http://images.ogmall.com/1a57976a-89c1-4680-8496-2d99c1d53ef3","http://images.ogmall.com/70d6e68f-c07b-4986-87a0-f6d47a3715f2"]
         * headImage : http://images.ogmall.com/cdc69ae6-abf4-473d-b02a-66c34a768a1f
         * houseStyleId : 4
         * houseStyleIdName : 地中海风格
         * houseTypeId : 2
         * houseTypeIdName : 二室一厅
         * id : 34
         * panoramic : false
         * planName : 更多更高的
         * quality : false
         * roomType : 第三方算法
         * vrurl : www.baidu.com
         */

        private int accountId;
        private String area;
        private int auditState;
        private String auditStateName;
        private String createAccountName;
        private String createAccountType;
        private String createTime;
        private String headImage;
        private int houseStyleId;
        private String houseStyleIdName;
        private int houseTypeId;
        private String houseTypeIdName;
        private int id;
        private boolean panoramic;
        private String planName;
        private boolean quality;
        private String roomType;
        private String vrurl;
        private List<String> detailsImageList;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getAuditState() {
            return auditState;
        }

        public void setAuditState(int auditState) {
            this.auditState = auditState;
        }

        public String getAuditStateName() {
            return auditStateName;
        }

        public void setAuditStateName(String auditStateName) {
            this.auditStateName = auditStateName;
        }

        public String getCreateAccountName() {
            return createAccountName;
        }

        public void setCreateAccountName(String createAccountName) {
            this.createAccountName = createAccountName;
        }

        public String getCreateAccountType() {
            return createAccountType;
        }

        public void setCreateAccountType(String createAccountType) {
            this.createAccountType = createAccountType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public int getHouseStyleId() {
            return houseStyleId;
        }

        public void setHouseStyleId(int houseStyleId) {
            this.houseStyleId = houseStyleId;
        }

        public String getHouseStyleIdName() {
            return houseStyleIdName;
        }

        public void setHouseStyleIdName(String houseStyleIdName) {
            this.houseStyleIdName = houseStyleIdName;
        }

        public int getHouseTypeId() {
            return houseTypeId;
        }

        public void setHouseTypeId(int houseTypeId) {
            this.houseTypeId = houseTypeId;
        }

        public String getHouseTypeIdName() {
            return houseTypeIdName;
        }

        public void setHouseTypeIdName(String houseTypeIdName) {
            this.houseTypeIdName = houseTypeIdName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isPanoramic() {
            return panoramic;
        }

        public void setPanoramic(boolean panoramic) {
            this.panoramic = panoramic;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public boolean isQuality() {
            return quality;
        }

        public void setQuality(boolean quality) {
            this.quality = quality;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public String getVrurl() {
            return vrurl;
        }

        public void setVrurl(String vrurl) {
            this.vrurl = vrurl;
        }

        public List<String> getDetailsImageList() {
            if (detailsImageList == null)
                detailsImageList = new ArrayList<>();
            return detailsImageList;
        }

        public void setDetailsImageList(List<String> detailsImageList) {
            this.detailsImageList = detailsImageList;
        }
    }
}