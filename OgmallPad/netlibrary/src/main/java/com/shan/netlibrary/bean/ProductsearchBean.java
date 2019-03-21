package com.shan.netlibrary.bean;


import android.text.TextUtils;

import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品搜索
 * Created by chenjunshan on 2018-08-22.
 */

public class ProductsearchBean extends BaseBean {

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
        private PageResultBean pageResult;
        private List<ListBean> list;

        public PageResultBean getPageResult() {
            if (pageResult == null)
                pageResult = new PageResultBean();
            return pageResult;
        }

        public void setPageResult(PageResultBean pageResult) {
            this.pageResult = pageResult;
        }

        public List<ListBean> getList() {
            if (list == null)
                list = new ArrayList<>();
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageResultBean {
            /**
             * pageNum : 0
             * pageSize : 20
             * size : 20
             * total : 520
             * pageList : []
             * totalPage : 26
             */

            private int pageNum;
            private int pageSize;
            private int size;
            private int total;
            private int totalPage;
            private List<?> pageList;

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public List<?> getPageList() {
                return pageList;
            }

            public void setPageList(List<?> pageList) {
                this.pageList = pageList;
            }
        }

        public static class ListBean {
            /**
             * name : 深爱克家具 L型沙发 现代北欧 棉麻布+五金脚 灰橡木纹色
             * id : 47893
             * specArr : [{"images":"http://images.ogmall.com/501f7a48-2953-4aef-9dc3-ad0ab509d7c7,http://images.ogmall.com/eaf672aa-26d0-4941-9baf-7a9c1a4c7d0d","spec_size":"2650*1700*750","spec_id":84774,"price":"3168.00","headImage":"http://images.ogmall.com/501f7a48-2953-4aef-9dc3-ad0ab509d7c7","spec_color":"灰橡木纹色"}]
             */

            private String name;
            private int id;
            private List<SpecArrBean> specArr;
            private String image;
            private String price;
            private int spec_id;
            private String spec_size;
            private String spec_color;

            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
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

            public String getImage() {
                if (TextUtils.isEmpty(image) && specArr != null && specArr.size() > 0) {
                    image = specArr.get(0).getHeadImage();
                }
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getPrice() {
                if (TextUtils.isEmpty(price) && specArr != null && specArr.size() > 0) {
                    price = specArr.get(0).getPrice();
                }
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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
                if (specArr == null) {
                    specArr = new ArrayList<>();
                }
                return specArr;
            }

            public void setSpecArr(List<SpecArrBean> specArr) {
                this.specArr = specArr;
            }

            public static class SpecArrBean {
                /**
                 * images : http://images.ogmall.com/501f7a48-2953-4aef-9dc3-ad0ab509d7c7,http://images.ogmall.com/eaf672aa-26d0-4941-9baf-7a9c1a4c7d0d
                 * spec_size : 2650*1700*750
                 * spec_id : 84774
                 * price : 3168.00
                 * headImage : http://images.ogmall.com/501f7a48-2953-4aef-9dc3-ad0ab509d7c7
                 * spec_color : 灰橡木纹色
                 */

                private String images;
                private String spec_size;
                private int spec_id;
                private String price;
                private String headImage;
                private String spec_color;

                public String getImages() {
                    return images;
                }

                public void setImages(String images) {
                    this.images = images;
                }

                public String getSpec_size() {
                    if (spec_size == null)
                        spec_size = "";
                    return spec_size;
                }

                public void setSpec_size(String spec_size) {
                    this.spec_size = spec_size;
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
                    if (spec_color == null)
                        spec_color = "";
                    return spec_color;
                }

                public void setSpec_color(String spec_color) {
                    this.spec_color = spec_color;
                }
            }
        }
    }
}