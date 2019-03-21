package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.List;

/**
 * 批量收藏
 * Created by chenjunshan on 2018-09-27.
 */

public class CentersavecollectsBean extends BaseBean {

    /**
     * data : {"images":null,"brandName":"90后家家具（有模）","subTitle":"清雅衣帽架 简约线条，装点居室的每个角落","headImage":"http://images.ogmall.com/cfb0456e-b8d1-4d70-bfc5-105125643f6d","brandId":275,"name":"90后家家具 清雅衣帽架 北欧 白蜡实木+免漆板 木纹色","id":48249,"specArr":[{"images":"http://images.ogmall.com/cfb0456e-b8d1-4d70-bfc5-105125643f6d,http://images.ogmall.com/264b0128-c9da-41b3-b1a0-686f7dfa4e3e,http://images.ogmall.com/47ca95a1-4366-4878-ab37-fdc0128459ee,http://images.ogmall.com/59436e03-52b9-42d7-ba9c-630a37b12e7c,http://images.ogmall.com/edb2a791-3c81-4b25-bd4e-8f40e91f677a","spec_size":"600*400*1600","priceRiginal":"3544.00","spec_id":85382,"price":"1949.20","headImage":"http://images.ogmall.com/cfb0456e-b8d1-4d70-bfc5-105125643f6d","spec_color":"木纹色","properties":[{"name":"欧工编码","value":"OAYA475A098"},{"name":"工厂编码","value":"QY-YMJ"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"衣帽架"},{"name":"产品名称","value":"清雅衣帽架"},{"name":"风格","value":"[\"北欧风格\"]"},{"name":"材质","value":"白蜡实木+免漆板"},{"name":"颜色","value":"木纹色"},{"name":"类型","value":"板木类"},{"name":"产地","value":"广东东莞"},{"name":"订货周期","value":"10-48天"},{"name":"尺寸","value":"600*400*1600"},{"name":"空间位置","value":"客厅、卧室"},{"name":"是否可拆洗","value":"否"},{"name":"家具油漆","value":"清漆"},{"name":"填充物","value":"无"},{"name":"是否可定制","value":"否"},{"name":"包装","value":"1件包"},{"name":"是否包含附件","value":"否"},{"name":"体积","value":"0.12"},{"name":"台面材质","value":"木面"}]}]}
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

    public static class DataBean {
        /**
         * images : null
         * brandName : 90后家家具（有模）
         * subTitle : 清雅衣帽架 简约线条，装点居室的每个角落
         * headImage : http://images.ogmall.com/cfb0456e-b8d1-4d70-bfc5-105125643f6d
         * brandId : 275
         * name : 90后家家具 清雅衣帽架 北欧 白蜡实木+免漆板 木纹色
         * id : 48249
         * specArr : [{"images":"http://images.ogmall.com/cfb0456e-b8d1-4d70-bfc5-105125643f6d,http://images.ogmall.com/264b0128-c9da-41b3-b1a0-686f7dfa4e3e,http://images.ogmall.com/47ca95a1-4366-4878-ab37-fdc0128459ee,http://images.ogmall.com/59436e03-52b9-42d7-ba9c-630a37b12e7c,http://images.ogmall.com/edb2a791-3c81-4b25-bd4e-8f40e91f677a","spec_size":"600*400*1600","priceRiginal":"3544.00","spec_id":85382,"price":"1949.20","headImage":"http://images.ogmall.com/cfb0456e-b8d1-4d70-bfc5-105125643f6d","spec_color":"木纹色","properties":[{"name":"欧工编码","value":"OAYA475A098"},{"name":"工厂编码","value":"QY-YMJ"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"衣帽架"},{"name":"产品名称","value":"清雅衣帽架"},{"name":"风格","value":"[\"北欧风格\"]"},{"name":"材质","value":"白蜡实木+免漆板"},{"name":"颜色","value":"木纹色"},{"name":"类型","value":"板木类"},{"name":"产地","value":"广东东莞"},{"name":"订货周期","value":"10-48天"},{"name":"尺寸","value":"600*400*1600"},{"name":"空间位置","value":"客厅、卧室"},{"name":"是否可拆洗","value":"否"},{"name":"家具油漆","value":"清漆"},{"name":"填充物","value":"无"},{"name":"是否可定制","value":"否"},{"name":"包装","value":"1件包"},{"name":"是否包含附件","value":"否"},{"name":"体积","value":"0.12"},{"name":"台面材质","value":"木面"}]}]
         */

        private Object images;
        private String brandName;
        private String subTitle;
        private String headImage;
        private int brandId;
        private String name;
        private int id;
        private List<SpecArrBean> specArr;

        public Object getImages() {
            return images;
        }

        public void setImages(Object images) {
            this.images = images;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<SpecArrBean> getSpecArr() {
            return specArr;
        }

        public void setSpecArr(List<SpecArrBean> specArr) {
            this.specArr = specArr;
        }

        public static class SpecArrBean {
            /**
             * images : http://images.ogmall.com/cfb0456e-b8d1-4d70-bfc5-105125643f6d,http://images.ogmall.com/264b0128-c9da-41b3-b1a0-686f7dfa4e3e,http://images.ogmall.com/47ca95a1-4366-4878-ab37-fdc0128459ee,http://images.ogmall.com/59436e03-52b9-42d7-ba9c-630a37b12e7c,http://images.ogmall.com/edb2a791-3c81-4b25-bd4e-8f40e91f677a
             * spec_size : 600*400*1600
             * priceRiginal : 3544.00
             * spec_id : 85382
             * price : 1949.20
             * headImage : http://images.ogmall.com/cfb0456e-b8d1-4d70-bfc5-105125643f6d
             * spec_color : 木纹色
             * properties : [{"name":"欧工编码","value":"OAYA475A098"},{"name":"工厂编码","value":"QY-YMJ"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"衣帽架"},{"name":"产品名称","value":"清雅衣帽架"},{"name":"风格","value":"[\"北欧风格\"]"},{"name":"材质","value":"白蜡实木+免漆板"},{"name":"颜色","value":"木纹色"},{"name":"类型","value":"板木类"},{"name":"产地","value":"广东东莞"},{"name":"订货周期","value":"10-48天"},{"name":"尺寸","value":"600*400*1600"},{"name":"空间位置","value":"客厅、卧室"},{"name":"是否可拆洗","value":"否"},{"name":"家具油漆","value":"清漆"},{"name":"填充物","value":"无"},{"name":"是否可定制","value":"否"},{"name":"包装","value":"1件包"},{"name":"是否包含附件","value":"否"},{"name":"体积","value":"0.12"},{"name":"台面材质","value":"木面"}]
             */

            private String images;
            private String spec_size;
            private String priceRiginal;
            private int spec_id;
            private String price;
            private String headImage;
            private String spec_color;
            private List<PropertiesBean> properties;

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getSpec_size() {
                return spec_size;
            }

            public void setSpec_size(String spec_size) {
                this.spec_size = spec_size;
            }

            public String getPriceRiginal() {
                return priceRiginal;
            }

            public void setPriceRiginal(String priceRiginal) {
                this.priceRiginal = priceRiginal;
            }

            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getSpec_color() {
                return spec_color;
            }

            public void setSpec_color(String spec_color) {
                this.spec_color = spec_color;
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
                 * value : OAYA475A098
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