package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据(一/二/三级)分类和老系统的风格查询品牌列表
 * Created by 陈俊山 on 2018-11-23.
 */

public class BrandselectBrandByCategoryAndOldHouseStyleBean extends BaseBean {

    /**
     * count : 116
     * data : [{"headimage":"http://images.ogmall.com/5f35c12d-ba80-4787-96bd-1e2f1d06a992","id":38,"image":"http://images.ogmall.com/9d5adbeb-7921-47cf-b0e8-8545d3de25d7","name":"幸福家家具（有模）"},{"headimage":"http://images.ogmall.com/0ee162c8-4652-4283-bc89-637e31437533","id":45,"image":"http://images.ogmall.com/2f212c74-2b7a-4c0d-a82f-b26e0b03f422","name":"美莱"},{"headimage":"http://images.ogmall.com/9193b9f0-750c-4ab7-94a9-1ced8bc1a0c1","id":46,"image":"http://images.ogmall.com/e06fbe92-182d-4240-a57e-d3a88e4fab02","name":"宜木构思（摩卡）"},{"headimage":"http://images.ogmall.com/9b83ed81-453e-4e93-b67e-b96e5d2b597f","id":48,"image":"http://images.ogmall.com/ccf1a326-1c5a-40ae-85a6-bc4b7a339489","name":"出彩生活家具"},{"headimage":"http://images.ogmall.com/9b46c044-231e-4eaa-9df1-42f69607e4bd","id":56,"image":"http://images.ogmall.com/f4a13b4b-bfbe-4cdb-95c8-3281aad893dc","name":"辉煌地毯"},{"headimage":"http://images.ogmall.com/0c1f5418-96e7-4a9b-a7a6-bf4c31c4bd5a","id":71,"image":"http://images.ogmall.com/f0c9b24b-3195-4451-9c23-0bb9e1960215","name":"安铂尔家具 （特价）"},{"headimage":"http://images.ogmall.com/1e2febdf-84d1-4ccd-80e6-ccf864ac8d6d","id":78,"image":"http://images.ogmall.com/1fe32392-e810-4175-bd09-0bdfc56dad88","name":"金碧辉煌灯饰"},{"headimage":"http://images.ogmall.com/aec77542-8794-497d-a845-ef04f9892ea5","id":80,"image":"http://images.ogmall.com/78deb857-f801-462c-a271-3f5e896bd734","name":"凤鸣饰品"},{"headimage":"http://images.ogmall.com/abc1f091-6e48-4d07-952d-05299ba7b55b","id":90,"image":"http://images.ogmall.com/c28621df-5e15-4c23-b4c9-095d2bf890cc","name":"利鼎灯饰"},{"headimage":"http://images.ogmall.com/ee7f3f46-6d45-4f1b-b079-76260aa0e258","id":99,"image":"http://images.ogmall.com/6a8eae80-ad9b-41b7-9b99-e31ffba1a729","name":"香港简欧现代北欧"},{"headimage":"http://images.ogmall.com/e10f21a9-a417-4450-aed6-be5f2fa2694c","id":100,"image":"http://images.ogmall.com/d74ee4e6-ef23-4d3f-97ba-345143ad2944","name":"欧莱玛家具"},{"headimage":"http://images.ogmall.com/adae9404-c605-4e52-a73d-15512e9d7986","id":106,"image":"http://images.ogmall.com/b8eb1f4d-d3d9-4f3b-b804-bd1c89968d56","name":"大千饰品"},{"headimage":"http://images.ogmall.com/12b417f2-b21b-4b2b-a2ac-3aac8ca08e2b","id":112,"image":"http://images.ogmall.com/2a175ac2-fd41-4e34-973f-c24ad6c5ead0","name":"朵朵贝儿灯具"},{"headimage":"http://images.ogmall.com/cba080e7-ead5-43dc-800e-dab184fdfd13","id":114,"image":"http://images.ogmall.com/4b84acf4-b1ad-4419-948c-a3449571c653","name":"简（有模）"},{"headimage":"http://images.ogmall.com/a8b435c4-37ed-432d-9bbe-696b019624e6","id":157,"image":"http://images.ogmall.com/59fb70c9-8b36-4976-a5e1-2a49183d1b2c","name":"健威家具 家百悦"},{"headimage":"http://images.ogmall.com/77f3926e-3099-4b6b-acd5-779ca7946b52","id":159,"image":"http://images.ogmall.com/5995555f-5711-408d-91de-c7d16fb67e7b","name":"夜色曙光灯饰"},{"headimage":"http://images.ogmall.com/97a6614c-7f09-411b-8d52-6373f2d16811","id":160,"image":"http://images.ogmall.com/d2d5f3ce-1607-4052-b37c-7e36ceeea518","name":"科梵饰品"},{"headimage":"http://images.ogmall.com/57d141f4-238a-4b34-9b7c-861b515867a4","id":162,"image":"http://images.ogmall.com/423f6a85-a5f4-40b4-8495-28c3ffe170fd","name":"七彩"}]
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
         * name : 幸福家家具（有模）
         */

        private String headimage;
        private int id;
        private String image;
        private String name;

        @Override
        public String toString() {
            return "DataBean{" +
                    "headimage='" + headimage + '\'' +
                    ", id=" + id +
                    ", image='" + image + '\'' +
                    ", name='" + name + '\'' +
                    ", isChick=" + isChick +
                    '}';
        }

        public boolean isChick() {
            return isChick;
        }

        public void setChick(boolean chick) {
            isChick = chick;
        }

        private boolean isChick;

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