package com.shan.netlibrary.bean;


import com.google.gson.annotations.SerializedName;
import com.shan.netlibrary.net.BaseBean;

import java.io.Serializable;

/**
 * 根据品牌id查询品牌详情
 * Created by chenjunshan on 2018-05-29.
 */

public class BranddetailsBean extends BaseBean {

    /**
     * data : {"image":"http://images.ogmall.com/8c621f1f-7586-43e6-8e81-680f34766c74","3dUrl":"https://pano.80ck.cn/ogznzt2/show/index.php?m=ogznzt2","headimage":"http://images.ogmall.com/4d19db48-b2ce-4b8f-b36d-3d06467752ef","videoUrl":"c438a65f0f43418bb4cd8a5dc1dfc771","name":"丝克诺德灯饰","id":89,"inSellCount":192,"content":"暂未描述详细信息"}
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

    public static class DataBean implements Serializable{
        /**
         * image : http://images.ogmall.com/8c621f1f-7586-43e6-8e81-680f34766c74
         * 3dUrl : https://pano.80ck.cn/ogznzt2/show/index.php?m=ogznzt2
         * headimage : http://images.ogmall.com/4d19db48-b2ce-4b8f-b36d-3d06467752ef
         * videoUrl : c438a65f0f43418bb4cd8a5dc1dfc771
         * name : 丝克诺德灯饰
         * id : 89
         * inSellCount : 192
         * content : 暂未描述详细信息
         */

        private String image;
        @SerializedName("3dUrl")
        private String _$3dUrl;
        private String headimage;
        private String videoUrl;
        private String name;
        private int id;
        private int inSellCount;
        private String content;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String get_$3dUrl() {
            return _$3dUrl;
        }

        public void set_$3dUrl(String _$3dUrl) {
            this._$3dUrl = _$3dUrl;
        }

        public String getHeadimage() {
            return headimage;
        }

        public void setHeadimage(String headimage) {
            this.headimage = headimage;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
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

        public int getInSellCount() {
            return inSellCount;
        }

        public void setInSellCount(int inSellCount) {
            this.inSellCount = inSellCount;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}