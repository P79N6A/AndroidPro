package com.shan.netlibrary.bean;


import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.shan.netlibrary.net.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取方案的详细信息
 * Created by chenjunshan on 2018-05-29.
 */

public class DesigndetailsBean extends BaseBean {

    /**
     * data : {"heandImage":"124.11","area":"124.11","3DUrl":null,"images":["124.11"],"createTime":null,"houseType":null,"company":null,"id":96,"designer":"苏莉斯","title":"厦门","normalUserName":null,"products":[{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OAAA357A001.jpg","spec_size":"2022*1300*1300建议床垫尺寸：1200*1900*150","price":"3374.80","spec_id":11975,"name":"【删】B11 宝贝家 儿童床 简美 100%进口新西兰松木 咖色","count":1,"id":9455,"spec_color":"咖色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OAAA357A034_1.jpg","spec_size":"960*590*1998","price":"5035.80","spec_id":12009,"name":"【删】B11 宝贝家 衣柜 简美 100%进口新西兰松木 白色","count":1,"id":9474,"spec_color":"咖色"},{"image":"http://images.ogmall.com/OAYA351B189 _1.jpg","spec_size":"648*460*650","price":"2431.00","spec_id":24015,"name":"【删】格美居家具 床头柜 榉木+实木多层板 美式 深樱桃色","count":2,"id":17986,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/OAYA351B1931 _1.jpg","spec_size":"780*840*820(小)","price":"2970.00","spec_id":24019,"name":"【删】格美居家具 单人沙发 榉木+夹板 美式 深樱桃色","count":1,"id":17990,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/OAYA351B194 _1.jpg","spec_size":"780*520*430","price":"1467.40","spec_id":24021,"name":"【删】格美居家具 脚凳 榉木+夹板 美式 深樱桃色","count":1,"id":17991,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/OAYA351B229 _1.jpg","spec_size":"803*873*920(单人沙发)","price":"5291.00","spec_id":24062,"name":"【删】格美居家具 单人沙发/双人沙发/三人沙发 榉木+实木多层板 美式 深樱桃色","count":1,"id":18026,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/OAYA351B229 _1.jpg","spec_size":"1404*873*920(双人沙发)","price":"7889.20","spec_id":24063,"name":"【删】格美居家具 单人沙发/双人沙发/三人沙发 榉木+实木多层板 美式 深樱桃色","count":1,"id":18026,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/OAYA351B206 _1.jpg","spec_size":"1400*800*450","price":"3935.80","spec_id":24035,"name":"【删】格美居家具 茶几 榉木+实木多层板 美式 深樱桃色","count":1,"id":18003,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/112d61a9-3e4e-429f-a024-3a5873e77017","spec_size":"1200*500*750","price":"3616.80","spec_id":2631,"name":"【删】B12 幸福家家具 妆台 新中式 木件 三分光 黑金色+咖色","count":1,"id":1945,"spec_color":"黑金色+咖色"},{"image":"http://images.ogmall.com/d2f2a0b1-0713-4cd4-b662-fff6d0b9ac3e","spec_size":"1600*410*400","price":"4191.00","spec_id":2672,"name":"【删】B12 幸福家家具 电视柜 新中式 白蜡木,板木结合,五金脚 黑金色","count":1,"id":1979,"spec_color":"黑金色"},{"image":"http://images.ogmall.com/OAIA358A170.jpg","spec_size":"415*320*1500","price":"2035.00","spec_id":4437,"name":"【删】B12 幸福家家具 吊柜 新中式 白蜡木,板木结合 黑金色","count":2,"id":3307,"spec_color":"黑金色"},{"image":"http://images.ogmall.com/112d61a9-3e4e-429f-a024-3a5873e77017","spec_size":"1200*500*750","price":"3616.80","spec_id":2631,"name":"【删】B12 幸福家家具 妆台 新中式 木件 三分光 黑金色+咖色","count":1,"id":1945,"spec_color":"黑金色+咖色"},{"image":"http://images.ogmall.com/OAIA358A045_1.jpg","spec_size":"495*560*850(PU)","price":"1265.00","spec_id":2578,"name":"【删】B12 幸福家家具 餐椅(全真皮/PU) 新中式 中纤板白蜡木 白色黑色","count":6,"id":1901,"spec_color":"白色,黑色"},{"image":"http://images.ogmall.com/2ca3fe5e-21ea-4b8e-b5c4-cfffce2c4a51","spec_size":"1680*280*680","price":"2061.40","spec_id":2673,"name":"【删】B12 幸福家家具 吊柜 新中式 白蜡木,板木结合 暖白色","count":2,"id":1980,"spec_color":"暖白色"},{"image":"http://images.ogmall.com/e069e9d6-4f8f-4ac3-be52-a56df95abfaa","spec_size":"650*400*580","price":"1425.60","spec_id":12049,"name":"【删】B11 宝贝家 装饰柜 简美 100%进口新西兰松木 白色","count":1,"id":9509,"spec_color":"白色"},{"image":"http://images.ogmall.com/OAIA358A193.jpg","spec_size":"500*500*480","price":"1680.80","spec_id":2704,"name":"【删】B12 幸福家家具 床头柜 新中式 白蜡木,板木结合 黑金色","count":2,"id":2000,"spec_color":"黑金色"},{"image":"http://images.ogmall.com/fef7de00-8107-431c-bd77-e2db589257b6","spec_size":"580*480*460","price":"1729.20","spec_id":2609,"name":"【删】B12 幸福家家具 床头柜 新中式 木件 三分光 黑金色+暖白色","count":1,"id":1925,"spec_color":"黑金色+暖白色"},{"image":"http://images.ogmall.com/OAIA358A164_1.jpg","spec_size":"1800*450*400（白电视柜）","price":"3260.40","spec_id":2681,"name":"【删】B12 幸福家家具 吊柜/电视柜/装饰柜 新中式 白蜡木,板木结合 黑金色","count":1,"id":1984,"spec_color":"暖白色"},{"image":"http://images.ogmall.com/OAIA358A209.jpg","spec_size":"1200*1200*740","price":"3724.60","spec_id":2724,"name":"【删】B12 幸福家家具 餐桌 新中式 白蜡木,板木结合 黑金色","count":1,"id":2015,"spec_color":"黑金色"}]}
     * message : null
     */

    private DataBean data;

    public DataBean getData() {
        if (data == null)
            data = new DataBean();
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * heandImage : 124.11
         * area : 124.11
         * 3DUrl : null
         * images : ["124.11"]
         * createTime : null
         * houseType : null
         * company : null
         * id : 96
         * designer : 苏莉斯
         * title : 厦门
         * normalUserName : null
         * products : [{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OAAA357A001.jpg","spec_size":"2022*1300*1300建议床垫尺寸：1200*1900*150","price":"3374.80","spec_id":11975,"name":"【删】B11 宝贝家 儿童床 简美 100%进口新西兰松木 咖色","count":1,"id":9455,"spec_color":"咖色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OAAA357A034_1.jpg","spec_size":"960*590*1998","price":"5035.80","spec_id":12009,"name":"【删】B11 宝贝家 衣柜 简美 100%进口新西兰松木 白色","count":1,"id":9474,"spec_color":"咖色"},{"image":"http://images.ogmall.com/OAYA351B189 _1.jpg","spec_size":"648*460*650","price":"2431.00","spec_id":24015,"name":"【删】格美居家具 床头柜 榉木+实木多层板 美式 深樱桃色","count":2,"id":17986,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/OAYA351B1931 _1.jpg","spec_size":"780*840*820(小)","price":"2970.00","spec_id":24019,"name":"【删】格美居家具 单人沙发 榉木+夹板 美式 深樱桃色","count":1,"id":17990,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/OAYA351B194 _1.jpg","spec_size":"780*520*430","price":"1467.40","spec_id":24021,"name":"【删】格美居家具 脚凳 榉木+夹板 美式 深樱桃色","count":1,"id":17991,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/OAYA351B229 _1.jpg","spec_size":"803*873*920(单人沙发)","price":"5291.00","spec_id":24062,"name":"【删】格美居家具 单人沙发/双人沙发/三人沙发 榉木+实木多层板 美式 深樱桃色","count":1,"id":18026,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/OAYA351B229 _1.jpg","spec_size":"1404*873*920(双人沙发)","price":"7889.20","spec_id":24063,"name":"【删】格美居家具 单人沙发/双人沙发/三人沙发 榉木+实木多层板 美式 深樱桃色","count":1,"id":18026,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/OAYA351B206 _1.jpg","spec_size":"1400*800*450","price":"3935.80","spec_id":24035,"name":"【删】格美居家具 茶几 榉木+实木多层板 美式 深樱桃色","count":1,"id":18003,"spec_color":"深樱桃色"},{"image":"http://images.ogmall.com/112d61a9-3e4e-429f-a024-3a5873e77017","spec_size":"1200*500*750","price":"3616.80","spec_id":2631,"name":"【删】B12 幸福家家具 妆台 新中式 木件 三分光 黑金色+咖色","count":1,"id":1945,"spec_color":"黑金色+咖色"},{"image":"http://images.ogmall.com/d2f2a0b1-0713-4cd4-b662-fff6d0b9ac3e","spec_size":"1600*410*400","price":"4191.00","spec_id":2672,"name":"【删】B12 幸福家家具 电视柜 新中式 白蜡木,板木结合,五金脚 黑金色","count":1,"id":1979,"spec_color":"黑金色"},{"image":"http://images.ogmall.com/OAIA358A170.jpg","spec_size":"415*320*1500","price":"2035.00","spec_id":4437,"name":"【删】B12 幸福家家具 吊柜 新中式 白蜡木,板木结合 黑金色","count":2,"id":3307,"spec_color":"黑金色"},{"image":"http://images.ogmall.com/112d61a9-3e4e-429f-a024-3a5873e77017","spec_size":"1200*500*750","price":"3616.80","spec_id":2631,"name":"【删】B12 幸福家家具 妆台 新中式 木件 三分光 黑金色+咖色","count":1,"id":1945,"spec_color":"黑金色+咖色"},{"image":"http://images.ogmall.com/OAIA358A045_1.jpg","spec_size":"495*560*850(PU)","price":"1265.00","spec_id":2578,"name":"【删】B12 幸福家家具 餐椅(全真皮/PU) 新中式 中纤板白蜡木 白色黑色","count":6,"id":1901,"spec_color":"白色,黑色"},{"image":"http://images.ogmall.com/2ca3fe5e-21ea-4b8e-b5c4-cfffce2c4a51","spec_size":"1680*280*680","price":"2061.40","spec_id":2673,"name":"【删】B12 幸福家家具 吊柜 新中式 白蜡木,板木结合 暖白色","count":2,"id":1980,"spec_color":"暖白色"},{"image":"http://images.ogmall.com/e069e9d6-4f8f-4ac3-be52-a56df95abfaa","spec_size":"650*400*580","price":"1425.60","spec_id":12049,"name":"【删】B11 宝贝家 装饰柜 简美 100%进口新西兰松木 白色","count":1,"id":9509,"spec_color":"白色"},{"image":"http://images.ogmall.com/OAIA358A193.jpg","spec_size":"500*500*480","price":"1680.80","spec_id":2704,"name":"【删】B12 幸福家家具 床头柜 新中式 白蜡木,板木结合 黑金色","count":2,"id":2000,"spec_color":"黑金色"},{"image":"http://images.ogmall.com/fef7de00-8107-431c-bd77-e2db589257b6","spec_size":"580*480*460","price":"1729.20","spec_id":2609,"name":"【删】B12 幸福家家具 床头柜 新中式 木件 三分光 黑金色+暖白色","count":1,"id":1925,"spec_color":"黑金色+暖白色"},{"image":"http://images.ogmall.com/OAIA358A164_1.jpg","spec_size":"1800*450*400（白电视柜）","price":"3260.40","spec_id":2681,"name":"【删】B12 幸福家家具 吊柜/电视柜/装饰柜 新中式 白蜡木,板木结合 黑金色","count":1,"id":1984,"spec_color":"暖白色"},{"image":"http://images.ogmall.com/OAIA358A209.jpg","spec_size":"1200*1200*740","price":"3724.60","spec_id":2724,"name":"【删】B12 幸福家家具 餐桌 新中式 白蜡木,板木结合 黑金色","count":1,"id":2015,"spec_color":"黑金色"}]
         */

        private String heandImage;
        private String area;
        @SerializedName("3DUrl")
        private String _$3DUrl;
        private String createTime;
        private String houseType;
        private String company;
        private int id;
        private String designer;
        private String title;
        private String normalUserName;
        private String allMoney;
        private String districtName;
        private String cityName;
        private String details;
        private List<String> images;
        private List<ProductsBean> products;

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAllMoney() {
            return allMoney;
        }

        public void setAllMoney(String allMoney) {
            this.allMoney = allMoney;
        }

        public String getHeandImage() {
            return heandImage;
        }

        public void setHeandImage(String heandImage) {
            this.heandImage = heandImage;
        }

        public String getArea() {
            if (TextUtils.isEmpty(area))
                area = "";
            if (!area.contains("m") && !area.contains("平方") && !area.contains("㎡")) {
                area = area + "㎡";
            }
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String get_$3DUrl() {
            if (TextUtils.isEmpty(_$3DUrl))
                _$3DUrl = "";
            return _$3DUrl;
        }

        public void set_$3DUrl(String _$3DUrl) {
            this._$3DUrl = _$3DUrl;
        }

        public String getCreateTime() {
            if (createTime == null)
                createTime = "";
            if (createTime.length() > 10) {
                createTime = createTime.substring(0, 10);
            }
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHouseType() {
            if (houseType == null)
                houseType = "";
            return houseType;
        }

        public void setHouseType(String houseType) {
            this.houseType = houseType;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDesigner() {
            return designer;
        }

        public void setDesigner(String designer) {
            this.designer = designer;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNormalUserName() {
            return normalUserName;
        }

        public void setNormalUserName(String normalUserName) {
            this.normalUserName = normalUserName;
        }

        public List<String> getImages() {
            if (images == null)
                images = new ArrayList<>();
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<ProductsBean> getProducts() {
            if (products == null)
                products = new ArrayList<>();
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean implements Serializable {
            /**
             * image : http://7xjd68.com1.z0.glb.clouddn.com/OAAA357A001.jpg
             * spec_size : 2022*1300*1300建议床垫尺寸：1200*1900*150
             * price : 3374.80
             * spec_id : 11975
             * name : 【删】B11 宝贝家 儿童床 简美 100%进口新西兰松木 咖色
             * count : 1
             * id : 9455
             * spec_color : 咖色
             */

            private String image;
            private String spec_size;
            private String price;
            private int spec_id;
            private String name;
            private int count;
            private int id;
            private String spec_color;
            private boolean isCollect;

            public boolean isCollect() {
                return isCollect;
            }

            public void setCollect(boolean collect) {
                isCollect = collect;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getSpec_size() {
                return spec_size;
            }

            public void setSpec_size(String spec_size) {
                this.spec_size = spec_size;
            }

            public String getPrice() {
                if (TextUtils.isEmpty(price))
                    price = "0";
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSpec_color() {
                return spec_color;
            }

            public void setSpec_color(String spec_color) {
                this.spec_color = spec_color;
            }
        }
    }
}