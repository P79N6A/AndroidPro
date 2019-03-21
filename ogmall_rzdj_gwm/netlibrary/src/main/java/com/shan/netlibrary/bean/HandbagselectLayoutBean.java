package com.shan.netlibrary.bean;


import android.text.TextUtils;

import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询布局
 * Created by 陈俊山 on 2018-11-22.
 */

public class HandbagselectLayoutBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {        if (data == null) {            data = new ArrayList<>();        }        return data;    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 客厅
         * value : [{"id":394,"name":"茶几"},{"id":395,"name":"沙发"},{"id":396,"name":"酒柜"},{"id":397,"name":"电视柜"},{"id":398,"name":"屏风"},{"id":399,"name":"玄关"},{"id":400,"name":"装饰柜"},{"id":401,"name":"鞋柜"},{"id":402,"name":"吊柜"},{"id":403,"name":"方几"},{"id":404,"name":"花架"},{"id":405,"name":"角几"},{"id":406,"name":"组合柜"},{"id":407,"name":"边柜"},{"id":408,"name":"背几"},{"id":533,"name":"装饰柜/装饰架"},{"id":536,"name":"其他"},{"id":541,"name":"休闲椅"},{"id":578,"name":"斗柜"},{"id":604,"name":"圆几"}]
         */

        private String name;
        private List<ValueBean> value;
        private boolean isCheck;
        private boolean isDefult;

        public boolean isDefult() {
            return isDefult;
        }

        public void setDefult(boolean defult) {
            isDefult = defult;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ValueBean> getValue() {
            if (value == null)
                value = new ArrayList<>();
            return value;
        }

        public void setValue(List<ValueBean> value) {
            if (value == null)
                value = new ArrayList<>();
            this.value = value;
        }

        public static class ValueBean {
            /**
             * id : 394
             * name : 茶几
             */

            private int id;
            private String name;
            private List<ProductBean> product;
            private boolean isCheck;

            public ValueBean(int id, String name) {
                this.id = id;
                this.name = name;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
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

            public List<ProductBean> getProduct() {
                if (product == null)
                    product = new ArrayList<>();
                return product;
            }

            public void setProduct(List<ProductBean> product) {
                this.product = product;
            }

            public static class ProductBean {
                private int specId;//规格id
                private int productId;//产品id
                private String name;
                private String color;
                private String size;
                private String price;
                private int num;
                private String image;
                private int dataType;

                public int getDataType() {
                    return dataType;
                }

                public void setDataType(int dataType) {
                    this.dataType = dataType;
                }

                public int getSpecId() {
                    return specId;
                }

                public void setSpecId(int specId) {
                    this.specId = specId;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getPrice() {
                    if (TextUtils.isEmpty(price))
                        price = "0";
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                @Override
                public String toString() {
                    return "ProductBean{" +
                            "specId=" + specId +
                            ", productId=" + productId +
                            ", name='" + name + '\'' +
                            ", color='" + color + '\'' +
                            ", size='" + size + '\'' +
                            ", price='" + price + '\'' +
                            ", num=" + num +
                            ", image='" + image + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "ValueBean{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        ", product=" + product +
                        ", isCheck=" + isCheck +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "HandbagselectLayoutBean{" +
                "data=" + data +
                '}';
    }
}