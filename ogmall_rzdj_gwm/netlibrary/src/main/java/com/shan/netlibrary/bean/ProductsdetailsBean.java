package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.List;

/**
 * 根据商品ID查询商品详情（白名单接口）
 * Created by 陈俊山 on 2018-11-18.
 */

public class ProductsdetailsBean extends BaseBean {

    /**
     * data : {"brandId":163,"brandName":"新源灯饰","collect":false,"headImage":"http://images.ogmall.com/OBYA396A0011.jpg","id":19587,"images":"http://images.ogmall.com/OBYA396A0011.jpg","name":"新源灯饰 吊灯 枪黑色,沙金色,银色,咖啡色 铝合金,五金 现代 6头吊灯/10头吊灯/14头吊灯/20头吊灯","specArr":[{"headImage":"http://images.ogmall.com/OBYA396A0011.jpg","images":"","price":662.4,"priceRiginal":2304,"properties":[{"name":"欧工编码","value":"OBYA396A001"},{"name":"工厂编码","value":"MD808-A-6"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"吊灯"},{"name":"产品名称","value":"吊灯"},{"name":"风格","value":"[\"现代风格\"]"},{"name":"材质","value":"铝合金,五金"},{"name":"颜色","value":"枪黑色,沙金色,银色,咖啡色"},{"name":"产地","value":"中国中山"},{"name":"灯头数","value":"6头"},{"name":"订货周期","value":"15天"},{"name":"尺寸","value":"宽1000*高500"}],"specColor":"枪黑色,沙金色,银色,咖啡色","specId":26999,"specSize":"宽1000*高500(6头)"},{"headImage":"http://oi5m9wa7v.bkt.clouddn.com/OBYA396A0011.jpg","images":"http://oi5m9wa7v.bkt.clouddn.com/OBYA396A0011.jpg","price":1104,"priceRiginal":3840,"properties":[{"name":"欧工编码","value":"OBYA396A0011"},{"name":"工厂编码","value":"MD808-A-10"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"吊灯"},{"name":"产品名称","value":"吊灯"},{"name":"风格","value":"[\"现代风格\"]"},{"name":"材质","value":"铝合金,五金"},{"name":"颜色","value":"枪黑色,沙金色,银色,咖啡色"},{"name":"产地","value":"中国中山"},{"name":"灯头数","value":"10头"},{"name":"订货周期","value":"15天"},{"name":"尺寸","value":"宽1070*高940"}],"specColor":"枪黑色,沙金色,银色,咖啡色","specId":27000,"specSize":"宽1070*高940(10头)"},{"headImage":"http://images.ogmall.com/OBYA396A0011.jpg","images":"","price":1545.6,"priceRiginal":5376,"properties":[{"name":"欧工编码","value":"OBYA396A0012"},{"name":"工厂编码","value":"MD808-A-14"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"吊灯"},{"name":"产品名称","value":"吊灯"},{"name":"风格","value":"[\"现代风格\"]"},{"name":"材质","value":"铝合金,五金"},{"name":"颜色","value":"枪黑色,沙金色,银色,咖啡色"},{"name":"产地","value":"中国中山"},{"name":"灯头数","value":"14头"},{"name":"订货周期","value":"15天"},{"name":"尺寸","value":"宽1740*高780"}],"specColor":"枪黑色,沙金色,银色,咖啡色","specId":27001,"specSize":"宽1740*高780(14头)"},{"headImage":"http://images.ogmall.com/OBYA396A0011.jpg","images":"","price":2208,"priceRiginal":7680,"properties":[{"name":"欧工编码","value":"OBYA396A0013"},{"name":"工厂编码","value":"MD808-A-20"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"吊灯"},{"name":"产品名称","value":"吊灯"},{"name":"风格","value":"[\"现代风格\"]"},{"name":"材质","value":"铝合金,五金"},{"name":"颜色","value":"枪黑色,沙金色,银色,咖啡色"},{"name":"产地","value":"中国中山"},{"name":"灯头数","value":"20头"},{"name":"订货周期","value":"15天"},{"name":"尺寸","value":"宽1900*高1200"}],"specColor":"枪黑色,沙金色,银色,咖啡色","specId":27002,"specSize":"宽1900*高1200(20头)"}],"subTitle":"吊灯(不含光源)"}
     */

    private DataBean data;

    public DataBean getData() {        if (data == null) {            data = new DataBean();        }        return data;    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * brandId : 163
         * brandName : 新源灯饰
         * collect : false
         * headImage : http://images.ogmall.com/OBYA396A0011.jpg
         * id : 19587
         * images : http://images.ogmall.com/OBYA396A0011.jpg
         * name : 新源灯饰 吊灯 枪黑色,沙金色,银色,咖啡色 铝合金,五金 现代 6头吊灯/10头吊灯/14头吊灯/20头吊灯
         * specArr : [{"headImage":"http://images.ogmall.com/OBYA396A0011.jpg","images":"","price":662.4,"priceRiginal":2304,"properties":[{"name":"欧工编码","value":"OBYA396A001"},{"name":"工厂编码","value":"MD808-A-6"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"吊灯"},{"name":"产品名称","value":"吊灯"},{"name":"风格","value":"[\"现代风格\"]"},{"name":"材质","value":"铝合金,五金"},{"name":"颜色","value":"枪黑色,沙金色,银色,咖啡色"},{"name":"产地","value":"中国中山"},{"name":"灯头数","value":"6头"},{"name":"订货周期","value":"15天"},{"name":"尺寸","value":"宽1000*高500"}],"specColor":"枪黑色,沙金色,银色,咖啡色","specId":26999,"specSize":"宽1000*高500(6头)"},{"headImage":"http://oi5m9wa7v.bkt.clouddn.com/OBYA396A0011.jpg","images":"http://oi5m9wa7v.bkt.clouddn.com/OBYA396A0011.jpg","price":1104,"priceRiginal":3840,"properties":[{"name":"欧工编码","value":"OBYA396A0011"},{"name":"工厂编码","value":"MD808-A-10"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"吊灯"},{"name":"产品名称","value":"吊灯"},{"name":"风格","value":"[\"现代风格\"]"},{"name":"材质","value":"铝合金,五金"},{"name":"颜色","value":"枪黑色,沙金色,银色,咖啡色"},{"name":"产地","value":"中国中山"},{"name":"灯头数","value":"10头"},{"name":"订货周期","value":"15天"},{"name":"尺寸","value":"宽1070*高940"}],"specColor":"枪黑色,沙金色,银色,咖啡色","specId":27000,"specSize":"宽1070*高940(10头)"},{"headImage":"http://images.ogmall.com/OBYA396A0011.jpg","images":"","price":1545.6,"priceRiginal":5376,"properties":[{"name":"欧工编码","value":"OBYA396A0012"},{"name":"工厂编码","value":"MD808-A-14"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"吊灯"},{"name":"产品名称","value":"吊灯"},{"name":"风格","value":"[\"现代风格\"]"},{"name":"材质","value":"铝合金,五金"},{"name":"颜色","value":"枪黑色,沙金色,银色,咖啡色"},{"name":"产地","value":"中国中山"},{"name":"灯头数","value":"14头"},{"name":"订货周期","value":"15天"},{"name":"尺寸","value":"宽1740*高780"}],"specColor":"枪黑色,沙金色,银色,咖啡色","specId":27001,"specSize":"宽1740*高780(14头)"},{"headImage":"http://images.ogmall.com/OBYA396A0011.jpg","images":"","price":2208,"priceRiginal":7680,"properties":[{"name":"欧工编码","value":"OBYA396A0013"},{"name":"工厂编码","value":"MD808-A-20"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"吊灯"},{"name":"产品名称","value":"吊灯"},{"name":"风格","value":"[\"现代风格\"]"},{"name":"材质","value":"铝合金,五金"},{"name":"颜色","value":"枪黑色,沙金色,银色,咖啡色"},{"name":"产地","value":"中国中山"},{"name":"灯头数","value":"20头"},{"name":"订货周期","value":"15天"},{"name":"尺寸","value":"宽1900*高1200"}],"specColor":"枪黑色,沙金色,银色,咖啡色","specId":27002,"specSize":"宽1900*高1200(20头)"}]
         * subTitle : 吊灯(不含光源)
         */

        private int brandId;
        private String brandName;
        private boolean collect;
        private String headImage;
        private int id;
        private String images;
        private String name;
        private String subTitle;
        private List<SpecArrBean> specArr;
        private String spec_size;
        private String spec_color;
        private int dataType;

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public String getSpec_size() {
            return spec_size;
        }

        public void setSpec_size(String spec_size) {
            this.spec_size = spec_size;
        }

        public String getSpec_color() {
            return spec_color;
        }

        public void setSpec_color(String spec_color) {
            this.spec_color = spec_color;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
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

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public List<SpecArrBean> getSpecArr() {
            return specArr;
        }

        public void setSpecArr(List<SpecArrBean> specArr) {
            this.specArr = specArr;
        }

        public static class SpecArrBean {
            /**
             * headImage : http://images.ogmall.com/OBYA396A0011.jpg
             * images :
             * price : 662.4
             * priceRiginal : 2304.0
             * properties : [{"name":"欧工编码","value":"OBYA396A001"},{"name":"工厂编码","value":"MD808-A-6"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"吊灯"},{"name":"产品名称","value":"吊灯"},{"name":"风格","value":"[\"现代风格\"]"},{"name":"材质","value":"铝合金,五金"},{"name":"颜色","value":"枪黑色,沙金色,银色,咖啡色"},{"name":"产地","value":"中国中山"},{"name":"灯头数","value":"6头"},{"name":"订货周期","value":"15天"},{"name":"尺寸","value":"宽1000*高500"}]
             * specColor : 枪黑色,沙金色,银色,咖啡色
             * specId : 26999
             * specSize : 宽1000*高500(6头)
             */

            private String headImage;
            private String images;
            private double price;
            private double priceRiginal;
            private String specColor;
            private int specId;
            private String specSize;
            private List<PropertiesBean> properties;

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getPriceRiginal() {
                return priceRiginal;
            }

            public void setPriceRiginal(double priceRiginal) {
                this.priceRiginal = priceRiginal;
            }

            public String getSpecColor() {
                return specColor;
            }

            public void setSpecColor(String specColor) {
                this.specColor = specColor;
            }

            public int getSpecId() {
                return specId;
            }

            public void setSpecId(int specId) {
                this.specId = specId;
            }

            public String getSpecSize() {
                return specSize;
            }

            public void setSpecSize(String specSize) {
                this.specSize = specSize;
            }

            public List<PropertiesBean> getProperties() {
                return properties;
            }

            public void setProperties(List<PropertiesBean> properties) {
                this.properties = properties;
            }

            public static class PropertiesBean {
                /**
                 * name : 欧工编码
                 * value : OBYA396A001
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
}