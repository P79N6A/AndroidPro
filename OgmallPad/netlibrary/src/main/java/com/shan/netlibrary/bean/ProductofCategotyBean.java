package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据类型id 获取产品数据
 * Created by chenjunshan on 2018-07-06.
 */

public class ProductofCategotyBean extends BaseBean {

    /**
     * data : {"list":[{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390506.png","spec_size":"470*470*100","price":"261.80","spec_id":7916,"name":"【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色","count":null,"id":6303,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390507.png","spec_size":"470*470*100","price":"261.80","spec_id":7917,"name":"【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色","count":null,"id":6304,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390527.png","spec_size":"350*350*80","price":"99.00","spec_id":7937,"name":"【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色","count":null,"id":6314,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390530.png","spec_size":"300*300*75","price":"77.00","spec_id":7940,"name":"【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色","count":null,"id":6315,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390532.png","spec_size":"300*300*75","price":"99.00","spec_id":7942,"name":"【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色","count":null,"id":6316,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390804.png","spec_size":"690*690*110","price":"605.00","spec_id":7947,"name":"【删】文联灯具 吸顶灯 现代极简 铁艺亚克力 白色","count":null,"id":6321,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390114.png","spec_size":"420*420*85","price":"440.00","spec_id":8085,"name":"【删】文联灯具 吸顶灯 现代极简 铁艺亚克力 白色","count":null,"id":6453,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390119.png","spec_size":"520*520*100","price":"589.60","spec_id":8090,"name":"【删】文联灯具 吸顶灯 现代极简 铁艺亚克力 深咖啡色","count":null,"id":6458,"spec_color":"深咖啡色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390128.png","spec_size":"520*520*140","price":"715.00","spec_id":8099,"name":"【删】文联灯具 吸顶灯 现代极简 铁艺亚克力 墨绿色","count":null,"id":6465,"spec_color":"墨绿色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390262.png","spec_size":"300*300*140","price":"433.40","spec_id":8244,"name":"【删】文联灯具 吸顶灯 简欧 铜材 铜色","count":null,"id":6570,"spec_color":"铜色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBKA1680075.jpg","spec_size":"620*620*210","price":"545.60","spec_id":13877,"name":"【删】朵朵贝儿灯具 1头吸顶灯 卡通儿童 木玻璃 蓝色","count":null,"id":10488,"spec_color":"蓝色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBKA16800811.jpg","spec_size":"600*600*280","price":"578.60","spec_id":13885,"name":"【删】朵朵贝儿灯具 3头吸顶灯 卡通儿童 木玻璃 蓝色","count":null,"id":10494,"spec_color":"蓝色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810001_1.jpg","spec_size":"450*450*250（1头）","price":"2558.60","spec_id":14481,"name":"四季家园灯具吸顶灯1头新中式古铜色铁艺烤漆手工布艺","count":null,"id":10979,"spec_color":"古铜色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810002_1.jpg","spec_size":"680*680*175（4头）","price":"1931.60","spec_id":14485,"name":"四季家园灯具吸顶灯4头6头新中式黑色铁艺烤漆手工布艺","count":null,"id":10980,"spec_color":"黑色"},{"image":"http://images.ogmall.com/OBIA3810003 _1.jpg","spec_size":"450*450*250（1头）","price":"1111.00","spec_id":14489,"name":"四季家园灯具吸顶 灯1头新中式咖啡色铁艺烤漆手工布艺","count":null,"id":10981,"spec_color":"咖啡色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810010_1.jpg","spec_size":"500*500*500（4头）","price":"1161.60","spec_id":14533,"name":"四季家园灯具吸顶灯4头新中式黑色铁艺烤漆手工布艺","count":null,"id":10988,"spec_color":"黑色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810011_1.jpg","spec_size":"180*180*200（1头）","price":"171.60","spec_id":14539,"name":"四季家园灯具吸顶灯1头新中式黑色铁艺烤漆手工布艺","count":null,"id":10989,"spec_color":"黑色（方形）"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810017_1.jpg","spec_size":"700*700*300（4头）","price":"1595.00","spec_id":14570,"name":"四季家园灯具吸顶灯4头6头新中式黑色铁艺烤漆手工布艺","count":null,"id":10995,"spec_color":"黑色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810018_1.jpg","spec_size":"550*550*550（4头）","price":"1427.80","spec_id":14578,"name":"四季家园灯具吸顶灯4头8头新中式黑色铁艺烤漆手工布艺","count":null,"id":10996,"spec_color":"黑色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810023_1.jpg","spec_size":"400*400*150(1头）","price":"869.00","spec_id":14593,"name":"四季家园灯具吸顶灯1头新中式黑色铁艺烤漆手工布艺","count":null,"id":11001,"spec_color":"黑色"}],"pageResult":{"pageNum":1,"pageSize":20,"size":20,"total":247,"pageList":[],"totalPage":13}}
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
         * list : [{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390506.png","spec_size":"470*470*100","price":"261.80","spec_id":7916,"name":"【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色","count":null,"id":6303,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390507.png","spec_size":"470*470*100","price":"261.80","spec_id":7917,"name":"【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色","count":null,"id":6304,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390527.png","spec_size":"350*350*80","price":"99.00","spec_id":7937,"name":"【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色","count":null,"id":6314,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390530.png","spec_size":"300*300*75","price":"77.00","spec_id":7940,"name":"【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色","count":null,"id":6315,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390532.png","spec_size":"300*300*75","price":"99.00","spec_id":7942,"name":"【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色","count":null,"id":6316,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390804.png","spec_size":"690*690*110","price":"605.00","spec_id":7947,"name":"【删】文联灯具 吸顶灯 现代极简 铁艺亚克力 白色","count":null,"id":6321,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390114.png","spec_size":"420*420*85","price":"440.00","spec_id":8085,"name":"【删】文联灯具 吸顶灯 现代极简 铁艺亚克力 白色","count":null,"id":6453,"spec_color":"白色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390119.png","spec_size":"520*520*100","price":"589.60","spec_id":8090,"name":"【删】文联灯具 吸顶灯 现代极简 铁艺亚克力 深咖啡色","count":null,"id":6458,"spec_color":"深咖啡色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390128.png","spec_size":"520*520*140","price":"715.00","spec_id":8099,"name":"【删】文联灯具 吸顶灯 现代极简 铁艺亚克力 墨绿色","count":null,"id":6465,"spec_color":"墨绿色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390262.png","spec_size":"300*300*140","price":"433.40","spec_id":8244,"name":"【删】文联灯具 吸顶灯 简欧 铜材 铜色","count":null,"id":6570,"spec_color":"铜色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBKA1680075.jpg","spec_size":"620*620*210","price":"545.60","spec_id":13877,"name":"【删】朵朵贝儿灯具 1头吸顶灯 卡通儿童 木玻璃 蓝色","count":null,"id":10488,"spec_color":"蓝色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBKA16800811.jpg","spec_size":"600*600*280","price":"578.60","spec_id":13885,"name":"【删】朵朵贝儿灯具 3头吸顶灯 卡通儿童 木玻璃 蓝色","count":null,"id":10494,"spec_color":"蓝色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810001_1.jpg","spec_size":"450*450*250（1头）","price":"2558.60","spec_id":14481,"name":"四季家园灯具吸顶灯1头新中式古铜色铁艺烤漆手工布艺","count":null,"id":10979,"spec_color":"古铜色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810002_1.jpg","spec_size":"680*680*175（4头）","price":"1931.60","spec_id":14485,"name":"四季家园灯具吸顶灯4头6头新中式黑色铁艺烤漆手工布艺","count":null,"id":10980,"spec_color":"黑色"},{"image":"http://images.ogmall.com/OBIA3810003 _1.jpg","spec_size":"450*450*250（1头）","price":"1111.00","spec_id":14489,"name":"四季家园灯具吸顶 灯1头新中式咖啡色铁艺烤漆手工布艺","count":null,"id":10981,"spec_color":"咖啡色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810010_1.jpg","spec_size":"500*500*500（4头）","price":"1161.60","spec_id":14533,"name":"四季家园灯具吸顶灯4头新中式黑色铁艺烤漆手工布艺","count":null,"id":10988,"spec_color":"黑色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810011_1.jpg","spec_size":"180*180*200（1头）","price":"171.60","spec_id":14539,"name":"四季家园灯具吸顶灯1头新中式黑色铁艺烤漆手工布艺","count":null,"id":10989,"spec_color":"黑色（方形）"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810017_1.jpg","spec_size":"700*700*300（4头）","price":"1595.00","spec_id":14570,"name":"四季家园灯具吸顶灯4头6头新中式黑色铁艺烤漆手工布艺","count":null,"id":10995,"spec_color":"黑色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810018_1.jpg","spec_size":"550*550*550（4头）","price":"1427.80","spec_id":14578,"name":"四季家园灯具吸顶灯4头8头新中式黑色铁艺烤漆手工布艺","count":null,"id":10996,"spec_color":"黑色"},{"image":"http://7xjd68.com1.z0.glb.clouddn.com/OBIA3810023_1.jpg","spec_size":"400*400*150(1头）","price":"869.00","spec_id":14593,"name":"四季家园灯具吸顶灯1头新中式黑色铁艺烤漆手工布艺","count":null,"id":11001,"spec_color":"黑色"}]
         * pageResult : {"pageNum":1,"pageSize":20,"size":20,"total":247,"pageList":[],"totalPage":13}
         */

        private PageResultBean pageResult;
        private List<ListBean> list;

        public PageResultBean getPageResult() {
            if (pageResult == null){
                pageResult = new PageResultBean();
            }
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
             * pageSize : 20
             * size : 20
             * total : 247
             * pageList : []
             * totalPage : 13
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
             * image : http://7xjd68.com1.z0.glb.clouddn.com/OBAA3390506.png
             * spec_size : 470*470*100
             * price : 261.80
             * spec_id : 7916
             * name : 【删】文联灯具 吸顶灯 现代简约 铁艺亚克力 白色
             * count : null
             * id : 6303
             * spec_color : 白色
             */

            private String image;
            private String spec_size;
            private String price;
            private int spec_id;
            private String name;
            private Object count;
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

            public Object getCount() {
                return count;
            }

            public void setCount(Object count) {
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