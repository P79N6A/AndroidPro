package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.List;

/**
 * 商品详情
 * Created by chenjunshan on 2018-10-11.
 */

public class ProductsdetailsBean extends BaseBean {

    /**
     * data : {"images":"http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b","brandName":"简美家具","subTitle":"二人沙发","headImage":"http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b","brandId":238,"name":"简美家具 二人沙发/三人沙发 简美 米白 松木、高密度海绵、接触面为头层牛皮非接触面为pvc","id":44521,"specArr":[{"images":"","spec_size":"1820*920*870（双人位）","priceRiginal":"18920.00","spec_id":75852,"price":"10406.00","headImage":"http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b","spec_color":"米白","properties":[{"name":"欧工编码","value":"OAYA448A233"},{"name":"工厂编码","value":"1737-2"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"沙发"},{"name":"产品名称","value":"二人沙发"},{"name":"风格","value":"[\"美式风格\"]"},{"name":"材质","value":"松木、高密度海绵、接触面为头层牛皮非接触面为pvc"},{"name":"颜色","value":"米白"},{"name":"类型","value":"软体"},{"name":"家具漆色","value":"封闭漆、三分光"},{"name":"产地","value":"中国佛山"},{"name":"订货周期","value":"38天"},{"name":"尺寸","value":"1820*920*870（双人位）"},{"name":"空间位置","value":"客厅"},{"name":"是否可拆洗","value":"不可拆洗"},{"name":"填充物","value":"高密度海绵+蛇形弹簧+橡筋带+喷胶棉"},{"name":"是否可定制","value":"颜色可定制"},{"name":"是否包含附件","value":"包含抱枕"},{"name":"是否有组合","value":"2+3"}]},{"images":"http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b","spec_size":"2310*920*870（三人位）","priceRiginal":"23672.00","spec_id":75853,"price":"13019.60","headImage":"http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b","spec_color":"米白","properties":[{"name":"欧工编码","value":"OAYA448A234"},{"name":"工厂编码","value":"1737-3"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"沙发"},{"name":"产品名称","value":"三人沙发"},{"name":"风格","value":"[\"美式风格\"]"},{"name":"材质","value":"松木、高密度海绵、接触面为头层牛皮非接触面为pvc"},{"name":"颜色","value":"米白"},{"name":"类型","value":"软体"},{"name":"家具漆色","value":"封闭漆、三分光"},{"name":"产地","value":"中国佛山"},{"name":"订货周期","value":"38天"},{"name":"尺寸","value":"2310*920*870（三人位）"},{"name":"空间位置","value":"客厅"},{"name":"是否可拆洗","value":"不可拆洗"},{"name":"填充物","value":"高密度海绵+蛇形弹簧+橡筋带+喷胶棉"},{"name":"是否可定制","value":"颜色可定制"},{"name":"是否包含附件","value":"包含抱枕"},{"name":"是否有组合","value":"2+3"}]}]}
     * message : null
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * images : http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b
         * brandName : 简美家具
         * subTitle : 二人沙发
         * headImage : http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b
         * brandId : 238
         * name : 简美家具 二人沙发/三人沙发 简美 米白 松木、高密度海绵、接触面为头层牛皮非接触面为pvc
         * id : 44521
         * specArr : [{"images":"","spec_size":"1820*920*870（双人位）","priceRiginal":"18920.00","spec_id":75852,"price":"10406.00","headImage":"http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b","spec_color":"米白","properties":[{"name":"欧工编码","value":"OAYA448A233"},{"name":"工厂编码","value":"1737-2"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"沙发"},{"name":"产品名称","value":"二人沙发"},{"name":"风格","value":"[\"美式风格\"]"},{"name":"材质","value":"松木、高密度海绵、接触面为头层牛皮非接触面为pvc"},{"name":"颜色","value":"米白"},{"name":"类型","value":"软体"},{"name":"家具漆色","value":"封闭漆、三分光"},{"name":"产地","value":"中国佛山"},{"name":"订货周期","value":"38天"},{"name":"尺寸","value":"1820*920*870（双人位）"},{"name":"空间位置","value":"客厅"},{"name":"是否可拆洗","value":"不可拆洗"},{"name":"填充物","value":"高密度海绵+蛇形弹簧+橡筋带+喷胶棉"},{"name":"是否可定制","value":"颜色可定制"},{"name":"是否包含附件","value":"包含抱枕"},{"name":"是否有组合","value":"2+3"}]},{"images":"http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b","spec_size":"2310*920*870（三人位）","priceRiginal":"23672.00","spec_id":75853,"price":"13019.60","headImage":"http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b","spec_color":"米白","properties":[{"name":"欧工编码","value":"OAYA448A234"},{"name":"工厂编码","value":"1737-3"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"沙发"},{"name":"产品名称","value":"三人沙发"},{"name":"风格","value":"[\"美式风格\"]"},{"name":"材质","value":"松木、高密度海绵、接触面为头层牛皮非接触面为pvc"},{"name":"颜色","value":"米白"},{"name":"类型","value":"软体"},{"name":"家具漆色","value":"封闭漆、三分光"},{"name":"产地","value":"中国佛山"},{"name":"订货周期","value":"38天"},{"name":"尺寸","value":"2310*920*870（三人位）"},{"name":"空间位置","value":"客厅"},{"name":"是否可拆洗","value":"不可拆洗"},{"name":"填充物","value":"高密度海绵+蛇形弹簧+橡筋带+喷胶棉"},{"name":"是否可定制","value":"颜色可定制"},{"name":"是否包含附件","value":"包含抱枕"},{"name":"是否有组合","value":"2+3"}]}]
         */

        private String images;
        private String brandName;
        private String subTitle;
        private String headImage;
        private int brandId;
        private String name;
        private int id;
        private List<SpecArrBean> specArr;
        private String spec_size;
        private String spec_color;
        private boolean isCollect;

        public boolean isCollect() {
            return isCollect;
        }

        public void setCollect(boolean collect) {
            isCollect = collect;
        }

        public String getSpec_size() {
            if (spec_size == null)
                spec_size = "";
            return spec_size;
        }

        public void setSpec_size(String spec_size) {
            this.spec_size = spec_size;
        }

        public String getSpec_color() {
            if (spec_color == null)
                spec_color = "";
            return spec_color;
        }

        public void setSpec_color(String spec_color) {
            this.spec_color = spec_color;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
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
             * images :
             * spec_size : 1820*920*870（双人位）
             * priceRiginal : 18920.00
             * spec_id : 75852
             * price : 10406.00
             * headImage : http://images.ogmall.com/bb0c34a1-3148-4ce4-805a-e9b9c5eb7b1b
             * spec_color : 米白
             * properties : [{"name":"欧工编码","value":"OAYA448A233"},{"name":"工厂编码","value":"1737-2"},{"name":"品馆","value":"标准馆"},{"name":"品类","value":"沙发"},{"name":"产品名称","value":"二人沙发"},{"name":"风格","value":"[\"美式风格\"]"},{"name":"材质","value":"松木、高密度海绵、接触面为头层牛皮非接触面为pvc"},{"name":"颜色","value":"米白"},{"name":"类型","value":"软体"},{"name":"家具漆色","value":"封闭漆、三分光"},{"name":"产地","value":"中国佛山"},{"name":"订货周期","value":"38天"},{"name":"尺寸","value":"1820*920*870（双人位）"},{"name":"空间位置","value":"客厅"},{"name":"是否可拆洗","value":"不可拆洗"},{"name":"填充物","value":"高密度海绵+蛇形弹簧+橡筋带+喷胶棉"},{"name":"是否可定制","value":"颜色可定制"},{"name":"是否包含附件","value":"包含抱枕"},{"name":"是否有组合","value":"2+3"}]
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
                 * value : OAYA448A233
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