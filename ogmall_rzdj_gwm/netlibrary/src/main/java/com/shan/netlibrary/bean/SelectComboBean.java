package com.shan.netlibrary.bean;

import com.shan.netlibrary.net.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2019/2/25.
 * {"code":0,"data":{"comboArea":"10","comboName":"test2","detailsImage":["http://images.ogmall.com/e57d9db7-5fb6-478b-b1a6-df09d6fc828b","http://images.ogmall.com/c2b2bf56-2281-49e4-bf6d-f774cddd227f","http://images.ogmall.com/63b0ea5b-9c60-4abd-8164-d721a0203b93"],
 * "headImage":"http://images.ogmall.com/0f1236bd-f3f6-4519-a54d-b938a6928c58",
 * "introduce":"dafasdfsadf",
 * "rich":[
 * {"color":"香槟金","headImage":"http://images.ogmall.com/e5780a05-339c-4fef-a73f-24052ec085f2","id":46270,"number":13,"productCategory":"梳妆台/妆镜/妆凳","productName":"B07 芙森堡家具 法式新古典 梳妆台 俄罗斯桦木 香香槟金/珍珠白/香槟银/黑檀色","productOgCode":"OABA353A0681","sellPrice":12.0,"spec":"1300*490*1620(含镜子)"}],"room":[{"mainCombo":[{"color":"香槟金","headImage":"http://images.ogmall.com/089f900b-5039-45db-ac7a-c2eb6883bb6c","id":4767,"number":2,"productCategory":"方几","productName":"B07 芙森堡家具 法式新古典 方几 俄罗斯桦木,大理石(冰花玉) 香槟金/珍珠白/香槟银/黑檀色","productOgCode":"OABA353A037","sellPrice":1.0,"spec":"700*700*620(不含大理石)"},{"color":"珍珠白","headImage":"http://images.ogmall.com/089f900b-5039-45db-ac7a-c2eb6883bb6c","id":55667,"number":4,"productCategory":"方几","productName":"B07 芙森堡家具 法式新古典 方几 俄罗斯桦木,大理石(冰花玉) 香槟金/珍珠白/香槟银/黑檀色","productOgCode":"OABA353A0371","sellPrice":3.0,"spec":"700*700*620(不含大理石)"}],"roomName":"space1","viceCombo":[{"color":"珍珠白","headImage":"http://images.ogmall.com/089f900b-5039-45db-ac7a-c2eb6883bb6c","id":55667,"number":6,"productCategory":"方几","productName":"B07 芙森堡家具 法式新古典 方几 俄罗斯桦木,大理石(冰花玉) 香槟金/珍珠白/香槟银/黑檀色","productOgCode":"OABA353A0371","sellPrice":5.0,"spec":"700*700*620(不含大理石)"},{"color":"香槟银","headImage":"http://images.ogmall.com/089f900b-5039-45db-ac7a-c2eb6883bb6c","id":55668,"number":8,"productCategory":"方几","productName":"B07 芙森堡家具 法式新古典 方几 俄罗斯桦木,大理石(冰花玉) 香槟金/珍珠白/香槟银/黑檀色","productOgCode":"OABA353A0372","sellPrice":7.0,"spec":"700*700*620(不含大理石)"}]},{"mainCombo":[{"color":"香槟银","headImage":"http://images.ogmall.com/fa13ed88-99da-43c1-bbb4-eddd324bc842","id":55710,"number":10,"productCategory":"床","productName":"B07 芙森堡家具 法式新古典 2米/1.8米床 俄罗斯桦木/皮 香槟金/珍珠白/香槟银/黑檀色","productOgCode":"OABA353A0526","sellPrice":9.0,"spec":"2400*2550*1830建议床垫尺寸:2000*2200"}],"roomName":"space2","viceCombo":[{"color":"珍珠白","headImage":"http://images.ogmall.com/e8079bed-bd3d-4f87-8568-e83b49df7360","id":55724,"number":12,"productCategory":"床头柜","productName":"B07 芙森堡家具 法式新古典 床头柜 俄罗斯桦木 香槟金/珍珠白/香槟银/黑檀色","productOgCode":"OABA353A0571","sellPrice":11.0,"spec":"680*460*620"}]}],"vrurl":"vrurl"},"message":"成功"}

 */

public class SelectComboBean extends BaseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private String comboArea;
        private String comboName;
        private List<String> detailsImage;
        private String headImage;
        private String introduce;
        private List<ListBean> rich;   //丰富区信息
        private List<RoomBean> room;   //其他区信息
        private String vrurl;

        public String getComboArea() {
            return comboArea;
        }

        public void setComboArea(String comboArea) {
            this.comboArea = comboArea;
        }

        public String getComboName() {
            return comboName;
        }

        public void setComboName(String comboName) {
            this.comboName = comboName;
        }

        public List<String> getDetailsImage() {
            return detailsImage;
        }

        public void setDetailsImage(List<String> detailsImage) {
            this.detailsImage = detailsImage;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public List<ListBean> getRich() {
            return rich;
        }

        public void setRich(List<ListBean> rich) {
            this.rich = rich;
        }

        public List<RoomBean> getRoom() {
            return room;
        }

        public void setRoom(List<RoomBean> room) {
            this.room = room;
        }

        public String getVrurl() {
            return vrurl;
        }

        public void setVrurl(String vrurl) {
            this.vrurl = vrurl;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "comboArea='" + comboArea + '\'' +
                    ", comboName='" + comboName + '\'' +
                    ", detailsImage=" + detailsImage +
                    ", headImage='" + headImage + '\'' +
                    ", introduce='" + introduce + '\'' +
                    ", rich=" + rich +
                    ", room=" + room +
                    ", vrurl='" + vrurl + '\'' +
                    '}';
        }
    }
    public static class ListBean{
        private String color;
        private String headImage;
        private int id;
        private int productId;
        private int number;
        private String productCategory;
        private String productName;
        private String productOgCode;
        private double sellPrice;
        private String spec;
        private boolean checked;

        private boolean isChecked;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getHeadImage() {
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

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getProductCategory() {
            return productCategory;
        }

        public void setProductCategory(String productCategory) {
            this.productCategory = productCategory;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductOgCode() {
            return productOgCode;
        }

        public void setProductOgCode(String productOgCode) {
            this.productOgCode = productOgCode;
        }

        public double getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(double sellPrice) {
            this.sellPrice = sellPrice;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "color='" + color + '\'' +
                    ", headImage='" + headImage + '\'' +
                    ", id=" + id +
                    ", number=" + number +
                    ", productCategory='" + productCategory + '\'' +
                    ", productName='" + productName + '\'' +
                    ", productOgCode='" + productOgCode + '\'' +
                    ", sellPrice=" + sellPrice +
                    ", spec='" + spec + '\'' +
                    '}';
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean isChecked() {
            return checked;
        }
    }
    public static class RoomBean{
        private String roomName;   //空间名称
        private List<ListBean> mainCombo;     //主信息
        private List<ListBean> viceCombo;     //副信息

        public boolean isMainCombo() {
            return isMainCombo;
        }

        public void setMainCombo(boolean mainCombo) {
            isMainCombo = mainCombo;
        }

        public boolean isViceCombo() {
            return isViceCombo;
        }

        public void setViceCombo(boolean viceCombo) {
            isViceCombo = viceCombo;
        }

        private boolean isMainCombo;
        private boolean isViceCombo;

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public List<ListBean> getMainCombo() {
            return mainCombo;
        }

        public void setMainCombo(List<ListBean> mainCombo) {
            this.mainCombo = mainCombo;
        }

        public List<ListBean> getViceCombo() {
            return viceCombo;
        }

        public void setViceCombo(List<ListBean> viceCombo) {
            this.viceCombo = viceCombo;
        }

        @Override
        public String toString() {
            return "RoomBean{" +
                    "roomName='" + roomName + '\'' +
                    ", mainCombo=" + mainCombo +
                    ", viceCombo=" + viceCombo +
                    '}';
        }
    }
}
