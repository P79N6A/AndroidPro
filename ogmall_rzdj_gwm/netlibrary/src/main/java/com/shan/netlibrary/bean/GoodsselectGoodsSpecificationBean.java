package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询商品规格（仅管理员/工厂/店铺拥有该权限）
 * Created by 陈俊山 on 2018-12-13.
 */

public class GoodsselectGoodsSpecificationBean extends BaseBean {

    /**
     * count : 3
     * data : [{"color":"工程工程","goodsId":19,"id":19,"imagesList":["http://images.ogmall.com/f43d3dde-7e6c-4345-a117-0b58ccb843a4"],"name":"VC郭德纲","originalPrice":245,"price":42,"spec":"很长长的"},{"color":"死贵死贵","goodsId":19,"id":20,"imagesList":["http://images.ogmall.com/e067b548-00b0-4160-a021-178dd52f8d74"],"name":"出租车存在","originalPrice":1245,"price":256,"spec":"改善公司"},{"color":"活生生","goodsId":19,"id":21,"imagesList":["http://images.ogmall.com/f67d7a32-2522-475e-8d5a-23d0f6129396"],"name":"出租车粉碎","originalPrice":1452,"price":245,"spec":"甘肃省"}]
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

    public static class DataBean {
        /**
         * color : 工程工程
         * goodsId : 19
         * id : 19
         * imagesList : ["http://images.ogmall.com/f43d3dde-7e6c-4345-a117-0b58ccb843a4"]
         * name : VC郭德纲
         * originalPrice : 245.0
         * price : 42.0
         * spec : 很长长的
         */

        private String color;
        private int goodsId;
        private int id;
        private String name;
        private String originalPrice;
        private String price;
        private String spec;
        private List<String> imagesList;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
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

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public List<String> getImagesList() {
            if (imagesList == null)
                imagesList = new ArrayList<>();
            return imagesList;
        }

        public void setImagesList(List<String> imagesList) {
            this.imagesList = imagesList;
        }
    }
}