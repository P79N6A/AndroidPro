package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据一级分类查询品牌列表（白名单接口）
 * Created by 陈俊山 on 2018-11-18.
 */

public class BrandselectBrandByCategory1Bean extends BaseBean {

    /**
     * count : 13
     * data : [{"headimage":"http://images.ogmall.com/5f35c12d-ba80-4787-96bd-1e2f1d06a992","id":38,"image":"http://images.ogmall.com/9d5adbeb-7921-47cf-b0e8-8545d3de25d7","name":"幸福家家具"},{"headimage":"http://images.ogmall.com/9b83ed81-453e-4e93-b67e-b96e5d2b597f","id":48,"image":"http://images.ogmall.com/ccf1a326-1c5a-40ae-85a6-bc4b7a339489","name":"出彩生活家具"},{"headimage":"http://images.ogmall.com/9244b8ad-15ed-4550-8770-436e669e7042","id":178,"image":"http://images.ogmall.com/e657fec7-c0a0-40bf-8c6a-3128ad9b36ce","name":"卡萨家具"},{"headimage":"http://images.ogmall.com/55f235e4-ab15-4589-ba8b-6b7348ea310a","id":180,"image":"http://images.ogmall.com/5f51e705-5770-4bc7-92d4-058e4825ed60","name":"东韵家具"},{"headimage":"http://images.ogmall.com/3317941f-8df5-4a7c-bc00-95febbe24229","id":181,"image":"http://images.ogmall.com/b402f9e4-8835-40ef-8d3e-ab30e9969df4","name":"品筑生活"},{"headimage":"http://images.ogmall.com/c1b0dae9-fa63-495b-9888-43984808bb72","id":188,"image":"http://images.ogmall.com/196ee699-2119-4f50-bc45-b83edfc91bfc","name":"优殿(优佳）家具"},{"headimage":"http://images.ogmall.com/3325fd91-d322-47db-83b6-a6a37f2be45e","id":189,"image":"http://images.ogmall.com/6ba5aea2-134c-47f3-a6b7-ff59a4f01cda","name":"梵"},{"headimage":"http://images.ogmall.com/0e62d732-08bb-4cc1-ae61-bb727462765d","id":194,"image":"http://images.ogmall.com/efa612f4-a959-4462-b692-5db1140c5c6f","name":"高宝沙发"},{"headimage":"http://images.ogmall.com/68ae1df8-86ad-4d7e-9943-53b1975304de","id":200,"image":"http://images.ogmall.com/dfe2b07e-e892-4aeb-8bf6-157047a8cf69","name":"苔丝贵族"},{"headimage":"http://images.ogmall.com/dc86cac4-eab2-434a-a434-4e895213adde","id":206,"image":"http://images.ogmall.com/1337fb83-ceb7-4cd0-8d4d-5fb1f824e5d4","name":"雷森那 新中式"},{"headimage":"http://images.ogmall.com/41283dd2-614d-44bd-9feb-565456717321","id":221,"image":"http://images.ogmall.com/43df919a-f839-41bf-a5b0-3c0162128d0f","name":"森杰家具"},{"headimage":"http://images.ogmall.com/9ed6f15a-9abc-423a-98e2-bb6375142542","id":240,"image":"http://images.ogmall.com/13a8409d-661d-4ed0-b986-22c42888c993","name":"左右沙发"},{"headimage":"http://images.ogmall.com/6f2a5495-8c2d-46bc-81b6-6d4f33863879","id":254,"image":"http://images.ogmall.com/8ef445d8-460c-42e3-9d71-69617b2ed2a3","name":"举目印象(青睦系列)"}]
     */

    private int count;
    private List<DataBean> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * headimage : http://images.ogmall.com/5f35c12d-ba80-4787-96bd-1e2f1d06a992
         * id : 38
         * image : http://images.ogmall.com/9d5adbeb-7921-47cf-b0e8-8545d3de25d7
         * name : 幸福家家具
         */

        private String headimage;
        private int id;
        private String image;
        private String name;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
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
    }
}