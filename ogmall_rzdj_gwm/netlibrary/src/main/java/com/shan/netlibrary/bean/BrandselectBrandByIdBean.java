package com.shan.netlibrary.bean;


import com.google.gson.annotations.SerializedName;
import com.shan.netlibrary.net.BaseBean;

/**
 * 查询品牌详情（白名单接口）
 * Created by 陈俊山 on 2018-11-18.
 */

public class BrandselectBrandByIdBean extends BaseBean {

    /**
     * data : {"adsubtitle":"中式情怀的现代时尚气息","adtitle":"中西融合的新中式原创设计","code":"A","commend":true,"companyId":314,"content":"幸福家是佛山市家具实业有限公司旗下的一个即深厚又年轻有活力的品牌，他创建于 2014 年，是一家集设计、生产、销售、及家居环境设计服务于一体的规范化家具集团。公司现有中高级管理，研发，销售人员 100 多人，员工 500 多人。在多年稳健的发展过程中，始终以传播家具文化、发展中国家具为己任，在\u201c差异化品牌\u201d战略的带动下、一直是市场竞争中的领先者。公司引进德国、意大利等先进的生产流水线设备从而对产品品质的保障形成了先天的条件。一直以来，公司都以优质的进口原材料为主，并经过精挑细选，对工艺精益求精。公司始终秉承\u201c互利共赢，永远创新\u201d的经营理念，并一直坚持以人体工学，健康与环保为研发主导思想，以中西融合的原创设计，缔造着简约、舒适的现代生活。以不断创新的企业精神，引领中国家居的发展潮流。以绿色环保的家居理念，在世界的舞台上演绎缤纷的生活之美。","durl":"https://pano.80ck.cn/ogznzt2/show/index.php?m=ogznzt2","factoryinfoId":29,"headimage":"http://images.ogmall.com/5f35c12d-ba80-4787-96bd-1e2f1d06a992","id":38,"image":"http://images.ogmall.com/9d5adbeb-7921-47cf-b0e8-8545d3de25d7","name":"幸福家家具","online":true,"pdfUrl":"http://oi5m9wa7v.bkt.clouddn.com/4932d7a8-f2e0-4c34-a34c-7b27765cc569","price":642,"recommend":1,"videoUrl":"c6278aa9752141fe8b7522f05b143ee6","woodenPriceFactor":0}
     */

    private DataBean data;

    public DataBean getData() {        if (data == null) {            data = new DataBean();        }        return data;    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * adsubtitle : 中式情怀的现代时尚气息
         * adtitle : 中西融合的新中式原创设计
         * code : A
         * commend : true
         * companyId : 314
         * content : 幸福家是佛山市家具实业有限公司旗下的一个即深厚又年轻有活力的品牌，他创建于 2014 年，是一家集设计、生产、销售、及家居环境设计服务于一体的规范化家具集团。公司现有中高级管理，研发，销售人员 100 多人，员工 500 多人。在多年稳健的发展过程中，始终以传播家具文化、发展中国家具为己任，在“差异化品牌”战略的带动下、一直是市场竞争中的领先者。公司引进德国、意大利等先进的生产流水线设备从而对产品品质的保障形成了先天的条件。一直以来，公司都以优质的进口原材料为主，并经过精挑细选，对工艺精益求精。公司始终秉承“互利共赢，永远创新”的经营理念，并一直坚持以人体工学，健康与环保为研发主导思想，以中西融合的原创设计，缔造着简约、舒适的现代生活。以不断创新的企业精神，引领中国家居的发展潮流。以绿色环保的家居理念，在世界的舞台上演绎缤纷的生活之美。
         * durl : https://pano.80ck.cn/ogznzt2/show/index.php?m=ogznzt2
         * factoryinfoId : 29
         * headimage : http://images.ogmall.com/5f35c12d-ba80-4787-96bd-1e2f1d06a992
         * id : 38
         * image : http://images.ogmall.com/9d5adbeb-7921-47cf-b0e8-8545d3de25d7
         * name : 幸福家家具
         * online : true
         * pdfUrl : http://oi5m9wa7v.bkt.clouddn.com/4932d7a8-f2e0-4c34-a34c-7b27765cc569
         * price : 642
         * recommend : 1
         * videoUrl : c6278aa9752141fe8b7522f05b143ee6
         * woodenPriceFactor : 0.0
         */

        private String adsubtitle;
        private String adtitle;
        @SerializedName("code")
        private String codeX;
        private boolean commend;
        private int companyId;
        private String content;
        private String durl;
        private int factoryinfoId;
        private String headimage;
        private int id;
        private String image;
        private String name;
        private boolean online;
        private String pdfUrl;
        private int price;
        private int recommend;
        private String videoUrl;
        private double woodenPriceFactor;

        public String getAdsubtitle() {
            return adsubtitle;
        }

        public void setAdsubtitle(String adsubtitle) {
            this.adsubtitle = adsubtitle;
        }

        public String getAdtitle() {
            return adtitle;
        }

        public void setAdtitle(String adtitle) {
            this.adtitle = adtitle;
        }

        public String getCodeX() {
            return codeX;
        }

        public void setCodeX(String codeX) {
            this.codeX = codeX;
        }

        public boolean isCommend() {
            return commend;
        }

        public void setCommend(boolean commend) {
            this.commend = commend;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDurl() {
            return durl;
        }

        public void setDurl(String durl) {
            this.durl = durl;
        }

        public int getFactoryinfoId() {
            return factoryinfoId;
        }

        public void setFactoryinfoId(int factoryinfoId) {
            this.factoryinfoId = factoryinfoId;
        }

        public String getHeadimage() {
            return headimage;
        }

        public void setHeadimage(String headimage) {
            this.headimage = headimage;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isOnline() {
            return online;
        }

        public void setOnline(boolean online) {
            this.online = online;
        }

        public String getPdfUrl() {
            return pdfUrl;
        }

        public void setPdfUrl(String pdfUrl) {
            this.pdfUrl = pdfUrl;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getRecommend() {
            return recommend;
        }

        public void setRecommend(int recommend) {
            this.recommend = recommend;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public double getWoodenPriceFactor() {
            return woodenPriceFactor;
        }

        public void setWoodenPriceFactor(double woodenPriceFactor) {
            this.woodenPriceFactor = woodenPriceFactor;
        }
    }
}