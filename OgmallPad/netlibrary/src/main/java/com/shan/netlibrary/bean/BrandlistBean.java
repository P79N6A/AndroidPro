package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据风格id返回品牌列表 当风格id为空时查询所有的品牌信息
 * Created by chenjunshan on 2018-05-29.
 */

public class BrandlistBean extends BaseBean {

    /**
     * data : {"list":[{"image":"http://images.ogmall.com/b2f4d6da-f965-4ec7-ae4c-69ebb1732c48","headImage":"http://images.ogmall.com/f1d20e4c-5a98-4f0f-9f20-3a793a5effbb","name":"出彩生活布艺","id":167},{"image":"http://images.ogmall.com/ba7d4678-724e-46c6-bd53-a152e16be4bc","headImage":"http://images.ogmall.com/ad2c3594-b0d7-49b8-8122-de3cf66e6575","name":"大千花艺","id":193},{"image":"http://images.ogmall.com/ac6437c0-2764-4a26-a5ad-894b258bea11","headImage":"http://images.ogmall.com/b4dd6e26-8189-4cfc-ac91-0c27b5de9949","name":"南辉灯饰","id":236}],"pageResult":{"pageNum":1,"pageSize":300,"size":3,"total":3,"pageList":[],"totalPage":1}}
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
         * list : [{"image":"http://images.ogmall.com/b2f4d6da-f965-4ec7-ae4c-69ebb1732c48","headImage":"http://images.ogmall.com/f1d20e4c-5a98-4f0f-9f20-3a793a5effbb","name":"出彩生活布艺","id":167},{"image":"http://images.ogmall.com/ba7d4678-724e-46c6-bd53-a152e16be4bc","headImage":"http://images.ogmall.com/ad2c3594-b0d7-49b8-8122-de3cf66e6575","name":"大千花艺","id":193},{"image":"http://images.ogmall.com/ac6437c0-2764-4a26-a5ad-894b258bea11","headImage":"http://images.ogmall.com/b4dd6e26-8189-4cfc-ac91-0c27b5de9949","name":"南辉灯饰","id":236}]
         * pageResult : {"pageNum":1,"pageSize":300,"size":3,"total":3,"pageList":[],"totalPage":1}
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
             * pageSize : 300
             * size : 3
             * total : 3
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
             * image : http://images.ogmall.com/b2f4d6da-f965-4ec7-ae4c-69ebb1732c48
             * headImage : http://images.ogmall.com/f1d20e4c-5a98-4f0f-9f20-3a793a5effbb
             * name : 出彩生活布艺
             * id : 167
             */

            private String image;
            private String headImage;
            private String name;
            private int id;
            private boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
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
        }
    }
}