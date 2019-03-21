package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.List;

/**
 * 获取收藏的商品
 * Created by chenjunshan on 2018-09-27.
 */

public class CentergetcollectsBean extends BaseBean {

    /**
     * data : {"list":[{"image":"http://images.ogmall.com/ODIA3930070.jpg","images":"http://oi5m9wa7v.bkt.clouddn.com/ODIA3930070.jpg","spec_size":"1045*740*40","price":"533.39","spec_id":33366,"name":"米娜 花卉挂画 美式 实木 蓝色+白色 实木框 印刷","isCollect":true,"id":21768,"spec_color":"蓝色+白色"},{"image":"http://images.ogmall.com/ODIA3930151.jpg","images":"","spec_size":"389*500*31","price":"99.00","spec_id":33459,"name":"米娜 动物挂画 北欧 实木 粉色+白色+黄色 实木框 印刷","isCollect":true,"id":21849,"spec_color":"粉色+白色+黄色"},{"image":"http://images.ogmall.com/ODIA3930152.jpg","images":"","spec_size":"389*500*31","price":"99.00","spec_id":33464,"name":"米娜 动物挂画 北欧 实木 粉色+白色+黑色 实木框 印刷","isCollect":true,"id":21850,"spec_color":"粉色+白色+黑色"},{"image":"http://images.ogmall.com/ODIA3930182.jpg","images":"","spec_size":"389*500*40","price":"105.60","spec_id":33598,"name":"米娜 植物挂画 北欧 实木框 绿色 实木框 印刷","isCollect":true,"id":21880,"spec_color":"绿色"},{"image":"http://images.ogmall.com/ODIA3930200.jpg","images":"","spec_size":"389*389*40","price":"85.80","spec_id":33659,"name":"米娜 花卉挂画 美式 实木框 蓝色 实木框 印刷","isCollect":true,"id":21898,"spec_color":"蓝色"},{"image":"http://images.ogmall.com/ODIA3930282.jpg","images":"","spec_size":"389*500*40","price":"105.60","spec_id":33988,"name":"米娜 抽象挂画 中式 实木 灰色 实木框 印刷","isCollect":true,"id":21980,"spec_color":"灰色"},{"image":"http://images.ogmall.com/07791d99-2287-4f42-8971-e974bc9bbe03","images":"http://images.ogmall.com/07791d99-2287-4f42-8971-e974bc9bbe03","spec_size":"389*500*30","price":"130.90","spec_id":65724,"name":"米娜 建筑挂画 美式 实木 彩色 实木框 印刷","isCollect":true,"id":39675,"spec_color":"彩色"},{"image":"http://images.ogmall.com/552f8390-3ccd-45d1-811c-008778b3dcd4","images":"http://images.ogmall.com/552f8390-3ccd-45d1-811c-008778b3dcd4","spec_size":"389*389*32","price":"111.10","spec_id":65756,"name":"米娜 抽象挂画 欧式 实木 彩色 实木框 印刷","isCollect":true,"id":39683,"spec_color":"彩色"},{"image":"http://images.ogmall.com/224637ae-9078-4a04-9399-86dae2fccdaa","images":"http://images.ogmall.com/224637ae-9078-4a04-9399-86dae2fccdaa","spec_size":"389*389*32","price":"111.10","spec_id":65797,"name":"米娜 抽象挂画 美式 实木 彩色 实木框 印刷","isCollect":true,"id":39692,"spec_color":"彩色"},{"image":"http://images.ogmall.com/dfe0943d-5652-43a5-a2e5-f7b2072f3e8f","images":"http://images.ogmall.com/dfe0943d-5652-43a5-a2e5-f7b2072f3e8f","spec_size":"389*389*32","price":"111.10","spec_id":65802,"name":"米娜 花鸟挂画 美式 实木 彩色 实木框 印刷","isCollect":true,"id":39693,"spec_color":"彩色"},{"image":"http://images.ogmall.com/2e4dc92e-d8a8-4e2c-9c34-79722e665750","images":"http://images.ogmall.com/2e4dc92e-d8a8-4e2c-9c34-79722e665750","spec_size":"389*389*32","price":"111.10","spec_id":65812,"name":"米娜 抽象挂画 美式 实木 彩色 实木框 印刷","isCollect":true,"id":39695,"spec_color":"彩色"},{"image":"http://images.ogmall.com/e1cb3122-83ab-43ca-968d-7cc90d7f960e","images":"http://images.ogmall.com/e1cb3122-83ab-43ca-968d-7cc90d7f960e","spec_size":"389*389*40","price":"100.10","spec_id":66272,"name":"米娜 抽象挂画 现代轻奢 实木 彩色 实木框 印刷","isCollect":true,"id":39793,"spec_color":"彩色"},{"image":"http://images.ogmall.com/92173aa8-dfe0-402a-b837-c0cb13d3c5d8","images":"http://images.ogmall.com/92173aa8-dfe0-402a-b837-c0cb13d3c5d8","spec_size":"389*500*40","price":"122.10","spec_id":66327,"name":"米娜 抽象挂画 现代轻奢 实木 彩色 实木框 印刷","isCollect":true,"id":39804,"spec_color":"彩色"},{"image":"http://images.ogmall.com/75e90fd3-c9c4-44dc-bb42-b9ea6a3f8cec","images":"http://images.ogmall.com/75e90fd3-c9c4-44dc-bb42-b9ea6a3f8cec","spec_size":"389*500*40","price":"122.10","spec_id":66387,"name":"米娜 抽象挂画 现代轻奢 实木 彩色 实木框 印刷","isCollect":true,"id":39816,"spec_color":"彩色"},{"image":"http://images.ogmall.com/ca96404d-a070-47aa-817b-309d799c73bb","images":"http://images.ogmall.com/ca96404d-a070-47aa-817b-309d799c73bb","spec_size":"389*500*34","price":"133.10","spec_id":66685,"name":"米娜 抽象挂画 新中式 实木 彩色 实木框 印刷","isCollect":true,"id":39879,"spec_color":"彩色"},{"image":"http://images.ogmall.com/669161c1-06a9-4a5b-8990-47487b8169c3","images":"http://images.ogmall.com/669161c1-06a9-4a5b-8990-47487b8169c3","spec_size":"750*500*34","price":"201.30","spec_id":66700,"name":"米娜 抽象挂画 新中式 实木 彩色 实木框 印刷","isCollect":true,"id":39882,"spec_color":"彩色"},{"image":"http://images.ogmall.com/f5d55604-dde6-4ff2-90a6-58605fde4078","images":"http://images.ogmall.com/f5d55604-dde6-4ff2-90a6-58605fde4078","spec_size":"750*500*34","price":"201.30","spec_id":66704,"name":"米娜 抽象挂画 新中式 实木 彩色 实木框 印刷","isCollect":true,"id":39883,"spec_color":"彩色"},{"image":"http://images.ogmall.com/01b668a6-3846-4700-8883-a4cb4950c3c8","images":"http://images.ogmall.com/01b668a6-3846-4700-8883-a4cb4950c3c8","spec_size":"500*750*34","price":"201.30","spec_id":66740,"name":"米娜 抽象挂画 新中式 实木 彩色 实木框 印刷","isCollect":true,"id":39892,"spec_color":"彩色"}],"pageResult":{"pageNum":1,"pageSize":18,"size":18,"total":22,"pageList":[],"totalPage":2}}
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
         * list : [{"image":"http://images.ogmall.com/ODIA3930070.jpg","images":"http://oi5m9wa7v.bkt.clouddn.com/ODIA3930070.jpg","spec_size":"1045*740*40","price":"533.39","spec_id":33366,"name":"米娜 花卉挂画 美式 实木 蓝色+白色 实木框 印刷","isCollect":true,"id":21768,"spec_color":"蓝色+白色"},{"image":"http://images.ogmall.com/ODIA3930151.jpg","images":"","spec_size":"389*500*31","price":"99.00","spec_id":33459,"name":"米娜 动物挂画 北欧 实木 粉色+白色+黄色 实木框 印刷","isCollect":true,"id":21849,"spec_color":"粉色+白色+黄色"},{"image":"http://images.ogmall.com/ODIA3930152.jpg","images":"","spec_size":"389*500*31","price":"99.00","spec_id":33464,"name":"米娜 动物挂画 北欧 实木 粉色+白色+黑色 实木框 印刷","isCollect":true,"id":21850,"spec_color":"粉色+白色+黑色"},{"image":"http://images.ogmall.com/ODIA3930182.jpg","images":"","spec_size":"389*500*40","price":"105.60","spec_id":33598,"name":"米娜 植物挂画 北欧 实木框 绿色 实木框 印刷","isCollect":true,"id":21880,"spec_color":"绿色"},{"image":"http://images.ogmall.com/ODIA3930200.jpg","images":"","spec_size":"389*389*40","price":"85.80","spec_id":33659,"name":"米娜 花卉挂画 美式 实木框 蓝色 实木框 印刷","isCollect":true,"id":21898,"spec_color":"蓝色"},{"image":"http://images.ogmall.com/ODIA3930282.jpg","images":"","spec_size":"389*500*40","price":"105.60","spec_id":33988,"name":"米娜 抽象挂画 中式 实木 灰色 实木框 印刷","isCollect":true,"id":21980,"spec_color":"灰色"},{"image":"http://images.ogmall.com/07791d99-2287-4f42-8971-e974bc9bbe03","images":"http://images.ogmall.com/07791d99-2287-4f42-8971-e974bc9bbe03","spec_size":"389*500*30","price":"130.90","spec_id":65724,"name":"米娜 建筑挂画 美式 实木 彩色 实木框 印刷","isCollect":true,"id":39675,"spec_color":"彩色"},{"image":"http://images.ogmall.com/552f8390-3ccd-45d1-811c-008778b3dcd4","images":"http://images.ogmall.com/552f8390-3ccd-45d1-811c-008778b3dcd4","spec_size":"389*389*32","price":"111.10","spec_id":65756,"name":"米娜 抽象挂画 欧式 实木 彩色 实木框 印刷","isCollect":true,"id":39683,"spec_color":"彩色"},{"image":"http://images.ogmall.com/224637ae-9078-4a04-9399-86dae2fccdaa","images":"http://images.ogmall.com/224637ae-9078-4a04-9399-86dae2fccdaa","spec_size":"389*389*32","price":"111.10","spec_id":65797,"name":"米娜 抽象挂画 美式 实木 彩色 实木框 印刷","isCollect":true,"id":39692,"spec_color":"彩色"},{"image":"http://images.ogmall.com/dfe0943d-5652-43a5-a2e5-f7b2072f3e8f","images":"http://images.ogmall.com/dfe0943d-5652-43a5-a2e5-f7b2072f3e8f","spec_size":"389*389*32","price":"111.10","spec_id":65802,"name":"米娜 花鸟挂画 美式 实木 彩色 实木框 印刷","isCollect":true,"id":39693,"spec_color":"彩色"},{"image":"http://images.ogmall.com/2e4dc92e-d8a8-4e2c-9c34-79722e665750","images":"http://images.ogmall.com/2e4dc92e-d8a8-4e2c-9c34-79722e665750","spec_size":"389*389*32","price":"111.10","spec_id":65812,"name":"米娜 抽象挂画 美式 实木 彩色 实木框 印刷","isCollect":true,"id":39695,"spec_color":"彩色"},{"image":"http://images.ogmall.com/e1cb3122-83ab-43ca-968d-7cc90d7f960e","images":"http://images.ogmall.com/e1cb3122-83ab-43ca-968d-7cc90d7f960e","spec_size":"389*389*40","price":"100.10","spec_id":66272,"name":"米娜 抽象挂画 现代轻奢 实木 彩色 实木框 印刷","isCollect":true,"id":39793,"spec_color":"彩色"},{"image":"http://images.ogmall.com/92173aa8-dfe0-402a-b837-c0cb13d3c5d8","images":"http://images.ogmall.com/92173aa8-dfe0-402a-b837-c0cb13d3c5d8","spec_size":"389*500*40","price":"122.10","spec_id":66327,"name":"米娜 抽象挂画 现代轻奢 实木 彩色 实木框 印刷","isCollect":true,"id":39804,"spec_color":"彩色"},{"image":"http://images.ogmall.com/75e90fd3-c9c4-44dc-bb42-b9ea6a3f8cec","images":"http://images.ogmall.com/75e90fd3-c9c4-44dc-bb42-b9ea6a3f8cec","spec_size":"389*500*40","price":"122.10","spec_id":66387,"name":"米娜 抽象挂画 现代轻奢 实木 彩色 实木框 印刷","isCollect":true,"id":39816,"spec_color":"彩色"},{"image":"http://images.ogmall.com/ca96404d-a070-47aa-817b-309d799c73bb","images":"http://images.ogmall.com/ca96404d-a070-47aa-817b-309d799c73bb","spec_size":"389*500*34","price":"133.10","spec_id":66685,"name":"米娜 抽象挂画 新中式 实木 彩色 实木框 印刷","isCollect":true,"id":39879,"spec_color":"彩色"},{"image":"http://images.ogmall.com/669161c1-06a9-4a5b-8990-47487b8169c3","images":"http://images.ogmall.com/669161c1-06a9-4a5b-8990-47487b8169c3","spec_size":"750*500*34","price":"201.30","spec_id":66700,"name":"米娜 抽象挂画 新中式 实木 彩色 实木框 印刷","isCollect":true,"id":39882,"spec_color":"彩色"},{"image":"http://images.ogmall.com/f5d55604-dde6-4ff2-90a6-58605fde4078","images":"http://images.ogmall.com/f5d55604-dde6-4ff2-90a6-58605fde4078","spec_size":"750*500*34","price":"201.30","spec_id":66704,"name":"米娜 抽象挂画 新中式 实木 彩色 实木框 印刷","isCollect":true,"id":39883,"spec_color":"彩色"},{"image":"http://images.ogmall.com/01b668a6-3846-4700-8883-a4cb4950c3c8","images":"http://images.ogmall.com/01b668a6-3846-4700-8883-a4cb4950c3c8","spec_size":"500*750*34","price":"201.30","spec_id":66740,"name":"米娜 抽象挂画 新中式 实木 彩色 实木框 印刷","isCollect":true,"id":39892,"spec_color":"彩色"}]
         * pageResult : {"pageNum":1,"pageSize":18,"size":18,"total":22,"pageList":[],"totalPage":2}
         */

        private PageResultBean pageResult;
        private List<ListBean> list;

        public PageResultBean getPageResult() {
            return pageResult;
        }

        public void setPageResult(PageResultBean pageResult) {
            this.pageResult = pageResult;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageResultBean {
            /**
             * pageNum : 1
             * pageSize : 18
             * size : 18
             * total : 22
             * pageList : []
             * totalPage : 2
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
             * image : http://images.ogmall.com/ODIA3930070.jpg
             * images : http://oi5m9wa7v.bkt.clouddn.com/ODIA3930070.jpg
             * spec_size : 1045*740*40
             * price : 533.39
             * spec_id : 33366
             * name : 米娜 花卉挂画 美式 实木 蓝色+白色 实木框 印刷
             * isCollect : true
             * id : 21768
             * spec_color : 蓝色+白色
             */

            private String image;
            private String images;
            private String spec_size;
            private String price;
            private int spec_id;
            private String name;
            private boolean isCollect;
            private int id;
            private String spec_color;
            private boolean isCheckDelete;

            public boolean isCheckDelete() {
                return isCheckDelete;
            }

            public void setCheckDelete(boolean checkDelete) {
                isCheckDelete = checkDelete;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

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

            public boolean isIsCollect() {
                return isCollect;
            }

            public void setIsCollect(boolean isCollect) {
                this.isCollect = isCollect;
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