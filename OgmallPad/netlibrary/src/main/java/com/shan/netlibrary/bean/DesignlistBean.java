package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据风格Id所有审核通过的方案
 * Created by chenjunshan on 2018-05-29.
 */

public class DesignlistBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        if (data == null){
            data = new DataBean();
        }
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
             * pageNum : 1
             * pageSize : 300
             * size : 63
             * total : 63
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
             * images : ["http://images.ogmall.com/65fb1b2d-7084-4a98-a48f-0793750e2994","http://images.ogmall.com/fe316abf-b47f-471e-b1a1-8ef06959124d","http://images.ogmall.com/c8f9de13-3376-4ac2-83aa-cde36003b4c4","http://images.ogmall.com/0158bccc-8730-4f76-b259-3a6f4b3d2bde","http://images.ogmall.com/ad107d98-81f9-4aed-ba7b-2d178ae6a090"]
             * headImage : http://images.ogmall.com/39722da9-6e38-4272-86a2-87b295ed6c49
             * id : 4567
             * title : 常德碧桂园8栋a户型
             */

            private String headImage;
            private int id;
            private String title;
            private List<String> images;

            public String getHeadImage() {
                if (images!=null&&images.size()>0){
                    return images.get(0);
                }
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }
        }
    }
}