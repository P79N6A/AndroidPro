package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据户型id 获取布局数据
 * Created by chenjunshan on 2018-08-22.
 */

public class RoomTypespecsBean extends BaseBean implements Cloneable,Serializable {

    /**
     * data : [{"specName":"客厅","categoryList":[{"categoryName":"茶几","categoryId":394},{"categoryName":"沙发","categoryId":395},{"categoryName":"酒柜","categoryId":396},{"categoryName":"电视柜","categoryId":397},{"categoryName":"屏风","categoryId":398},{"categoryName":"玄关","categoryId":399},{"categoryName":"装饰柜","categoryId":400},{"categoryName":"鞋柜","categoryId":401},{"categoryName":"吊柜","categoryId":402},{"categoryName":"方几","categoryId":403},{"categoryName":"花架","categoryId":404},{"categoryName":"角几","categoryId":405},{"categoryName":"组合柜","categoryId":406},{"categoryName":"边柜","categoryId":407},{"categoryName":"背几","categoryId":408},{"categoryName":"装饰柜/装饰架","categoryId":533},{"categoryName":"其他","categoryId":536},{"categoryName":"休闲椅","categoryId":541},{"categoryName":"斗柜","categoryId":578},{"categoryName":"圆几","categoryId":604}]},{"specName":"客厅一","categoryList":[{"categoryName":"茶几","categoryId":394},{"categoryName":"沙发","categoryId":395},{"categoryName":"酒柜","categoryId":396},{"categoryName":"电视柜","categoryId":397},{"categoryName":"屏风","categoryId":398},{"categoryName":"玄关","categoryId":399},{"categoryName":"装饰柜","categoryId":400},{"categoryName":"鞋柜","categoryId":401},{"categoryName":"吊柜","categoryId":402},{"categoryName":"方几","categoryId":403},{"categoryName":"花架","categoryId":404},{"categoryName":"角几","categoryId":405},{"categoryName":"组合柜","categoryId":406},{"categoryName":"边柜","categoryId":407},{"categoryName":"背几","categoryId":408},{"categoryName":"装饰柜/装饰架","categoryId":533},{"categoryName":"其他","categoryId":536},{"categoryName":"休闲椅","categoryId":541},{"categoryName":"斗柜","categoryId":578},{"categoryName":"圆几","categoryId":604}]},{"specName":"客厅二","categoryList":[{"categoryName":"茶几","categoryId":394},{"categoryName":"沙发","categoryId":395},{"categoryName":"酒柜","categoryId":396},{"categoryName":"电视柜","categoryId":397},{"categoryName":"屏风","categoryId":398},{"categoryName":"玄关","categoryId":399},{"categoryName":"装饰柜","categoryId":400},{"categoryName":"鞋柜","categoryId":401},{"categoryName":"吊柜","categoryId":402},{"categoryName":"方几","categoryId":403},{"categoryName":"花架","categoryId":404},{"categoryName":"角几","categoryId":405},{"categoryName":"组合柜","categoryId":406},{"categoryName":"边柜","categoryId":407},{"categoryName":"背几","categoryId":408},{"categoryName":"装饰柜/装饰架","categoryId":533},{"categoryName":"其他","categoryId":536},{"categoryName":"休闲椅","categoryId":541},{"categoryName":"斗柜","categoryId":578},{"categoryName":"圆几","categoryId":604}]},{"specName":"客厅三","categoryList":[{"categoryName":"茶几","categoryId":394},{"categoryName":"沙发","categoryId":395},{"categoryName":"酒柜","categoryId":396},{"categoryName":"电视柜","categoryId":397},{"categoryName":"屏风","categoryId":398},{"categoryName":"玄关","categoryId":399},{"categoryName":"装饰柜","categoryId":400},{"categoryName":"鞋柜","categoryId":401},{"categoryName":"吊柜","categoryId":402},{"categoryName":"方几","categoryId":403},{"categoryName":"花架","categoryId":404},{"categoryName":"角几","categoryId":405},{"categoryName":"组合柜","categoryId":406},{"categoryName":"边柜","categoryId":407},{"categoryName":"背几","categoryId":408},{"categoryName":"装饰柜/装饰架","categoryId":533},{"categoryName":"其他","categoryId":536},{"categoryName":"休闲椅","categoryId":541},{"categoryName":"斗柜","categoryId":578},{"categoryName":"圆几","categoryId":604}]},{"specName":"卧室","categoryList":[{"categoryName":"床","categoryId":383},{"categoryName":"床垫","categoryId":384},{"categoryName":"衣柜","categoryId":385},{"categoryName":"床尾凳","categoryId":386},{"categoryName":"穿衣镜","categoryId":387},{"categoryName":"休闲椅","categoryId":388},{"categoryName":"衣帽架","categoryId":389},{"categoryName":"梳妆台/妆镜/妆凳","categoryId":390},{"categoryName":"斗柜","categoryId":391},{"categoryName":"脚凳","categoryId":392},{"categoryName":"床头柜","categoryId":534},{"categoryName":"其他","categoryId":542}]}]
     * message : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        if (data == null)
            data = new ArrayList<>();
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Cloneable,Serializable {
        /**
         * specName : 客厅
         * categoryList : [{"categoryName":"茶几","categoryId":394},{"categoryName":"沙发","categoryId":395},{"categoryName":"酒柜","categoryId":396},{"categoryName":"电视柜","categoryId":397},{"categoryName":"屏风","categoryId":398},{"categoryName":"玄关","categoryId":399},{"categoryName":"装饰柜","categoryId":400},{"categoryName":"鞋柜","categoryId":401},{"categoryName":"吊柜","categoryId":402},{"categoryName":"方几","categoryId":403},{"categoryName":"花架","categoryId":404},{"categoryName":"角几","categoryId":405},{"categoryName":"组合柜","categoryId":406},{"categoryName":"边柜","categoryId":407},{"categoryName":"背几","categoryId":408},{"categoryName":"装饰柜/装饰架","categoryId":533},{"categoryName":"其他","categoryId":536},{"categoryName":"休闲椅","categoryId":541},{"categoryName":"斗柜","categoryId":578},{"categoryName":"圆几","categoryId":604}]
         */

        private String specName;
        private List<CategoryListBean> categoryList;
        private boolean isCheck;//是否选中
        private boolean isDefult;//是否为默认

        public DataBean(String specName) {
            this.specName = specName;
        }

        public DataBean(String specName, boolean isCheck) {
            this.specName = specName;
            this.isCheck = isCheck;
        }

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

        public String getSpecName() {
            return specName;
        }

        public void setSpecName(String specName) {
            this.specName = specName;
        }

        public List<CategoryListBean> getCategoryList() {
            if (categoryList == null)
                categoryList = new ArrayList<>();
            return categoryList;
        }

        public void setCategoryList(List<CategoryListBean> categoryList) {
            this.categoryList = categoryList;
        }

        public static class CategoryListBean implements Cloneable,Serializable {
            /**
             * categoryName : 茶几
             * categoryId : 394
             */

            private String categoryName;
            private int categoryId;
            private boolean isShow;

            public CategoryListBean(String categoryName, int categoryId) {
                this.categoryName = categoryName;
                this.categoryId = categoryId;
            }

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public Object clone() {
                CategoryListBean cloned = null;
                try {
                    cloned = (CategoryListBean) super.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                return cloned;
            }

            private List<ProductBean> product;

            public List<ProductBean> getProduct() {
                if (product == null)
                    product = new ArrayList<>();
                return product;
            }

            public void setProduct(List<ProductBean> product) {
                this.product = product;
            }

            public static class ProductBean {
                private String image;
                private String price;
                private int spec_id;
                private String spec_size;
                private String spec_color;
                private int count;
                private String name;
                private int productId;

                @Override
                public String toString() {
                    return "ProductBean{" +
                            "image='" + image + '\'' +
                            ", price='" + price + '\'' +
                            ", spec_id=" + spec_id +
                            ", spec_size='" + spec_size + '\'' +
                            ", spec_color='" + spec_color + '\'' +
                            ", count=" + count +
                            ", name='" + name + '\'' +
                            ", productId=" + productId +
                            '}';
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

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getPrice() {
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

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }
            }
        }

        public Object clone() {
            DataBean cloned = null;
            try {
                cloned = (DataBean) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return cloned;
        }
    }

    public Object clone() {
        RoomTypespecsBean cloned = null;
        try {
            cloned = (RoomTypespecsBean) super.clone();
            cloned.data = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                cloned.data.add((DataBean) data.get(i).clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloned;
    }
}