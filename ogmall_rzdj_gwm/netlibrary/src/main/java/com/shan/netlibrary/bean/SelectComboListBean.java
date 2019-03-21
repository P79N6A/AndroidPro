package com.shan.netlibrary.bean;

import com.shan.netlibrary.net.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2019/2/25.
 *
 * {
 "comboArea": "10",
 "comboName": "test2",
 "headImage": "http://images.ogmall.com/0f1236bd-f3f6-4519-a54d-b938a6928c58",
 "id": 23,
 "lastTime": "2019-02-25 21:23:54",
 "maxSellPrice": 218,
 "minSellPrice": 104,
 "priceScope": "￥104.00~￥218.00",
 "roomType": "space1、space2",
 "sellStatus": 1,
 "sellStatusOfBoolean": true
 }
 */

public class SelectComboListBean extends BaseBean{

    private List<DataBean> data;
    private int count;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class DataBean{
        private String comboArea;
        private String comboName;
        private String headImage;
        private int id;
        private String lastTime;
        private int maxSellPrice;
        private int minSellPrice;
        private String priceScope;
        private String roomType;
        private int sellStatus;
        private  boolean sellStatusOfBoolean;

        public String getComboArea() {
            return comboArea;
        }

        public void setComboArea(String comboArea) {
            this.comboArea = comboArea;
        }

        public String getComboName() {
            return comboName;
        }

        public void setComboName(String comboName) {
            this.comboName = comboName;
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

        public String getLastTime() {
            return lastTime;
        }

        public void setLastTime(String lastTime) {
            this.lastTime = lastTime;
        }

        public int getMaxSellPrice() {
            return maxSellPrice;
        }

        public void setMaxSellPrice(int maxSellPrice) {
            this.maxSellPrice = maxSellPrice;
        }

        public int getMinSellPrice() {
            return minSellPrice;
        }

        public void setMinSellPrice(int minSellPrice) {
            this.minSellPrice = minSellPrice;
        }

        public String getPriceScope() {
            return priceScope;
        }

        public void setPriceScope(String priceScope) {
            this.priceScope = priceScope;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public int getSellStatus() {
            return sellStatus;
        }

        public void setSellStatus(int sellStatus) {
            this.sellStatus = sellStatus;
        }

        public boolean isSellStatusOfBoolean() {
            return sellStatusOfBoolean;
        }

        public void setSellStatusOfBoolean(boolean sellStatusOfBoolean) {
            this.sellStatusOfBoolean = sellStatusOfBoolean;
        }
    }
}
