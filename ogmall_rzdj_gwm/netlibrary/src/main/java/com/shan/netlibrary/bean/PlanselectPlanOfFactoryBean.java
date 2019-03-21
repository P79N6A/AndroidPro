package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 平板底部的方案里查询对应工厂下级的方案
 * Created by 陈俊山 on 2018-11-17.
 */

public class PlanselectPlanOfFactoryBean extends BaseBean {

    /**
     * count : 3
     * data : [{"accountId":65,"auditState":1,"createAccountName":"市代理1_1_1","createAccountType":"30","createTime":"2018-11-17 13:45:47","detailsImageList":["http://images.ogmall.com/f88c1158-4a73-45a9-b049-c4be77198436","http://images.ogmall.com/b87afe80-4174-4c58-9d3f-87a7b6f2ed2c","http://images.ogmall.com/6d00f240-6a2b-4311-baa8-0d2e1800e6ac"],"headImage":"http://images.ogmall.com/143061d1-3c19-483b-adae-01eef14f9c87","houseStyleId":1,"houseStyleIdName":"新中式风格","houseTypeId":1,"houseTypeIdName":"一室一厅","id":32,"panoramic":false,"planName":"sdfs","quality":false},{"accountId":67,"area":"面积","auditState":1,"createAccountName":"陈俊山","createAccountType":"30","createTime":"2018-11-17 09:56:03","detailsImage":"","headImage":"http://images.ogmall.com/61a01f02-d8eb-4670-ace6-34a311482746","houseStyleId":1,"houseStyleIdName":"新中式风格","houseTypeId":1,"houseTypeIdName":"一室一厅","id":2,"panoramic":true,"planName":"方案名称2","quality":true,"roomType":"房间类型","vrurl":"http://vrurl"},{"accountId":65,"area":"面积","auditState":1,"createAccountName":"市代理1_1_1","createAccountType":"30","createTime":"2018-11-15 10:05:05","detailsImageList":["http://images.ogmall.com/62912819-7cc6-4603-aedc-97c81a21b82b","http://images.ogmall.com/62912819-7cc6-4603-aedc-97c81a21b82b","http://images.ogmall.com/62912819-7cc6-4603-aedc-97c81a21b82b","http://images.ogmall.com/62912819-7cc6-4603-aedc-97c81a21b82b","http://images.ogmall.com/62912819-7cc6-4603-aedc-97c81a21b82b"],"headImage":"http://images.ogmall.com/61a01f02-d8eb-4670-ace6-34a311482746","houseStyleId":1,"houseStyleIdName":"新中式风格","houseTypeId":1,"houseTypeIdName":"一室一厅","id":1,"panoramic":false,"planName":"方案名称1","quality":true,"roomType":"房间类型","vrurl":"http://vrurl"}]
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
         * accountId : 65
         * auditState : 1
         * createAccountName : 市代理1_1_1
         * createAccountType : 30
         * createTime : 2018-11-17 13:45:47
         * detailsImageList : ["http://images.ogmall.com/f88c1158-4a73-45a9-b049-c4be77198436","http://images.ogmall.com/b87afe80-4174-4c58-9d3f-87a7b6f2ed2c","http://images.ogmall.com/6d00f240-6a2b-4311-baa8-0d2e1800e6ac"]
         * headImage : http://images.ogmall.com/143061d1-3c19-483b-adae-01eef14f9c87
         * houseStyleId : 1
         * houseStyleIdName : 新中式风格
         * houseTypeId : 1
         * houseTypeIdName : 一室一厅
         * id : 32
         * panoramic : false
         * planName : sdfs
         * quality : false
         * area : 面积
         * detailsImage :
         * roomType : 房间类型
         * vrurl : http://vrurl
         */

        private int accountId;
        private int auditState;
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
        private String area;
        private String detailsImage;
        private String roomType;
        private String vrurl;
        private List<String> detailsImageList;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }

        public int getAuditState() {
            return auditState;
        }

        public void setAuditState(int auditState) {
            this.auditState = auditState;
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

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDetailsImage() {
            return detailsImage;
        }

        public void setDetailsImage(String detailsImage) {
            this.detailsImage = detailsImage;
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
            return detailsImageList;
        }

        public void setDetailsImageList(List<String> detailsImageList) {
            this.detailsImageList = detailsImageList;
        }
    }
}