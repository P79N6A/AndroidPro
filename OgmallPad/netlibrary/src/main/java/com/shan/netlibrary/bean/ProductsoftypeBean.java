package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据类型id 获取产品数据
 * Created by chenjunshan on 2018-07-05.
 */

public class ProductsoftypeBean extends BaseBean {
    /**
     * data : {"list":[{"image":"http://images.ogmall.com/089f900b-5039-45db-ac7a-c2eb6883bb6c","spec_size":"700*700*620(不含大理石)","price":"3674.00","spec_id":55668,"name":"B07 芙森堡家具 法式新古典 方几 俄罗斯桦木,大理石(冰花玉) 香槟金/珍珠白/香槟银/黑檀色","count":6,"id":3637,"spec_color":"香槟银"},{"image":"http://images.ogmall.com/OABA353A042.jpg","spec_size":"1650*2200*1550建议床垫尺寸:1500*2000","price":"12005.40","spec_id":55687,"name":"B07 芙森堡家具 法式新古典 1.8米/1.5米床 俄罗斯桦木/按图皮板为：829皮 香槟金/珍珠白/香槟银/黑檀色","count":1,"id":3643,"spec_color":"珍珠白"}],"pageResult":{"pageNum":1,"pageSize":200,"size":2,"total":2,"pageList":[],"totalPage":1}}
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
         * list : [{"image":"http://images.ogmall.com/089f900b-5039-45db-ac7a-c2eb6883bb6c","spec_size":"700*700*620(不含大理石)","price":"3674.00","spec_id":55668,"name":"B07 芙森堡家具 法式新古典 方几 俄罗斯桦木,大理石(冰花玉) 香槟金/珍珠白/香槟银/黑檀色","count":6,"id":3637,"spec_color":"香槟银"},{"image":"http://images.ogmall.com/OABA353A042.jpg","spec_size":"1650*2200*1550建议床垫尺寸:1500*2000","price":"12005.40","spec_id":55687,"name":"B07 芙森堡家具 法式新古典 1.8米/1.5米床 俄罗斯桦木/按图皮板为：829皮 香槟金/珍珠白/香槟银/黑檀色","count":1,"id":3643,"spec_color":"珍珠白"}]
         * pageResult : {"pageNum":1,"pageSize":200,"size":2,"total":2,"pageList":[],"totalPage":1}
         */

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
             * pageNum : 1
             * pageSize : 200
             * size : 2
             * total : 2
             * pageList : []
             * totalPage : 1
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
             * image : http://images.ogmall.com/089f900b-5039-45db-ac7a-c2eb6883bb6c
             * spec_size : 700*700*620(不含大理石)
             * price : 3674.00
             * spec_id : 55668
             * name : B07 芙森堡家具 法式新古典 方几 俄罗斯桦木,大理石(冰花玉) 香槟金/珍珠白/香槟银/黑檀色
             * count : 6
             * id : 3637
             * spec_color : 香槟银
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