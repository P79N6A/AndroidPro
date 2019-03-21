package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询我的收藏商品或者客户的收藏商品
 * Created by 陈俊山 on 2018-11-18.
 */

public class ProductselectProductOfCollectBean extends BaseBean {

    /**
     * count : 17
     * data : [{"collectId":38,"headImage":"http://images.ogmall.com/OAFA111A001.JPG","id":16791,"name":"希尔家具 1.8m床/1.5m床 现代极简 胡桃木,真皮 原木色+深咖色","price":5539.55,"subTitle":"1.8m床 经典北欧风，美国黑胡桃，防止变形和开裂。欧洲SGS检测标准的高级环保油漆，高档真皮，产品稳重，有品味，高层人士的私享生活。"},{"collectId":37,"headImage":"http://images.ogmall.com/OABA353A042.jpg","id":3643,"name":"B07 芙森堡家具 法式新古典 1.8米/1.5米床 俄罗斯桦木/按图皮板为：829皮 香槟金/珍珠白/香槟银/黑檀色","price":6275.55,"subTitle":"1.8m床 以柔美的弧线勾勒出轮廓，清新写意的雕花修饰床身，按中轴对称均匀分布，法式独有的涡纹设计。"},{"collectId":36,"headImage":"http://images.ogmall.com/d74d6aad-8b23-49ae-8bf4-31ad8af1957d","id":40322,"name":"宜木构思 矮/高饰品柜 简美 白蜡木 咖古色","price":1787.1,"subTitle":"饰品柜"},{"collectId":35,"headImage":"http://images.ogmall.com/43d3d25c-ec92-4bc6-afb2-c22c3ecc4ad8","id":40324,"name":"宜木构思 厅柜2 简美 白蜡木 咖古色","price":2400.05,"subTitle":"厅柜2"},{"collectId":34,"headImage":"http://images.ogmall.com/24741133-3704-4402-ac06-a619a2dd4a4d","id":40278,"name":"宜木构思 妆台+妆镜 简美 白蜡木 咖古色","price":363.4,"subTitle":"妆台+妆镜"},{"collectId":33,"headImage":"http://images.ogmall.com/93cb751b-7b35-4d1c-8a65-d66084be6a46","id":40275,"name":"宜木构思 衣帽架 简美 白蜡木 咖古色","price":867.1,"subTitle":"衣帽架"},{"collectId":32,"headImage":"http://images.ogmall.com/e92476b8-115f-4aba-8391-f84b7991eded","id":40272,"name":"宜木构思 书椅 简美 白蜡木，仿皮 咖古色","price":570.4,"subTitle":"书椅"},{"collectId":31,"headImage":"http://images.ogmall.com/7071e908-87f7-4182-b4e5-ad0b1070fd87","id":40271,"name":"宜木构思 书台 简美 白蜡木 咖古色","price":2398.9,"subTitle":"书台"},{"collectId":30,"headImage":"http://images.ogmall.com/OAYA405B023.jpg","id":21146,"name":"出彩生活灯具 台灯 树脂 图片色 美式","price":502.55,"subTitle":"台灯"},{"collectId":29,"headImage":"http://images.ogmall.com/OAYA405B022.jpg","id":21145,"name":"出彩生活灯具 台灯 陶瓷 图片色 美式","price":800.4,"subTitle":"台灯"},{"collectId":28,"headImage":"http://images.ogmall.com/OBYA396A016.jpg","id":19601,"name":"新源灯饰 台灯 白色+原木色 五金,实木 现代 1头台灯/3头台灯","price":350.75,"subTitle":"台灯(不含光源)"},{"collectId":27,"headImage":"http://images.ogmall.com/OBYA396A0094.jpg","id":19594,"name":"新源灯饰 吊灯 黑+金,白+金 五金 现代 6头吊灯/8头吊灯/9头吊灯/10头吊灯/2头吊灯","price":993.6,"subTitle":"吊灯(不含光源)"},{"collectId":26,"headImage":"http://images.ogmall.com/OBYA396A0041.jpg","id":19590,"name":"新源灯饰 吊灯 枪黑色,沙金色 铝合金,五金 现代 16头吊灯/24头吊灯","price":1545.6,"subTitle":"吊灯(不含光源)"},{"collectId":25,"headImage":"http://images.ogmall.com/OBYA396A010.jpg","id":19595,"name":"新源灯饰 吊灯 黑+金,白+金 五金 现代 6头吊灯","price":1291.45,"subTitle":"吊灯(不含光源)"},{"collectId":24,"headImage":"http://images.ogmall.com/OBYA396A0111.jpg","id":19596,"name":"新源灯饰 吊灯 白色,黑色/铬色,金色 铝合金 现代 6头吊灯/10头吊灯/12头吊灯","price":690,"subTitle":"吊灯(不含光源)"},{"collectId":23,"headImage":"http://images.ogmall.com/OBYA396A0082.jpg","id":19593,"name":"新源灯饰 吊灯 电泳金 五金,玻璃 现代 12头吊灯/16头吊灯/24头吊灯","price":1228.2,"subTitle":"吊灯(不含光源)"},{"collectId":21,"headImage":"http://images.ogmall.com/OBYA396A003.jpg","id":19589,"name":"新源灯饰 吊灯 枪黑色,沙金色 铝合金,五金 现代 8头吊灯/12头吊灯","price":662.4,"subTitle":"吊灯(不含光源)"}]
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
         * collectId : 38
         * headImage : http://images.ogmall.com/OAFA111A001.JPG
         * id : 16791
         * name : 希尔家具 1.8m床/1.5m床 现代极简 胡桃木,真皮 原木色+深咖色
         * price : 5539.55
         * subTitle : 1.8m床 经典北欧风，美国黑胡桃，防止变形和开裂。欧洲SGS检测标准的高级环保油漆，高档真皮，产品稳重，有品味，高层人士的私享生活。
         */

        private int collectId;
        private String headImage;
        private int id;
        private String name;
        private double price;
        private String subTitle;
        private int dataType;

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public int getCollectId() {
            return collectId;
        }

        public void setCollectId(int collectId) {
            this.collectId = collectId;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }
    }
}