package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取首页数据（白名单接口）
 * Created by 陈俊山 on 2018-11-19.
 */

public class AppselectHomeDataBean extends BaseBean {

    /**
     * data : {"banner":[{"describe":"描述","url":"http://www.baidu.com","picture":"http://images.ogmall.com/9e7f4a4c-1da6-4aae-9ced-db225efcf22c"},{"describe":"描述","url":"http://www.baidu.com","picture":"http://images.ogmall.com/b77b8f92-d148-444b-8f97-cd36d31c83ed"},{"describe":"描述","url":"http://www.baidu.com","picture":"http://images.ogmall.com/38811ee2-8fe8-45fc-86ce-31aa547ab6cb"}],"moduleHeadImage":"http://images.ogmall.com/69edcf55-f47d-4aac-8120-cd86648b7bd5","category":[{"picture":"http://images.ogmall.com/9d7c368b-7ff8-409c-bfe1-fe1f1400f953","category1Id":381,"category2Id":382,"category3Id":383,"name":"床"},{"picture":"http://images.ogmall.com/9fa998f1-ec34-420a-9ca7-ce7d1ba6e930","category1Id":381,"category2Id":393,"category3Id":397,"name":"电视柜"},{"picture":"http://images.ogmall.com/a639b067-c550-40af-a2e1-7cca867a8131","category1Id":381,"category2Id":393,"category3Id":395,"name":"沙发"},{"picture":"http://images.ogmall.com/e80f939b-1efc-40b3-8825-af0cc7db89f5","category1Id":381,"category2Id":393,"category3Id":401,"name":"鞋柜"},{"picture":"http://images.ogmall.com/48486e0d-eb9f-4f9d-a5c2-5aefc4cf1958","category1Id":381,"category2Id":409,"category3Id":410,"name":"书桌"},{"picture":"http://images.ogmall.com/ccbce599-346f-4900-b101-a2795559a2c1","category1Id":381,"category2Id":409,"category3Id":412,"name":"书柜"},{"picture":"http://images.ogmall.com/088f737c-d960-4f73-a6f0-e11a5753458b","category1Id":381,"category2Id":382,"category3Id":390,"name":"梳妆台"},{"picture":"http://images.ogmall.com/2453aec6-c2d8-4186-adf5-8f7f0955bd14","category1Id":443,"category2Id":444,"category3Id":449,"name":"台灯"},{"picture":"http://images.ogmall.com/05434c31-5c31-4db5-8bff-86ed3d361359","category1Id":381,"category2Id":414,"category3Id":415,"name":"餐椅"},{"picture":"http://images.ogmall.com/1c06a2bb-3733-4bd9-a407-4df7493e4fe9","category1Id":381,"category2Id":382,"category3Id":385,"name":"衣柜"}],"hotProduct":[{"headImage":"http://images.ogmall.com/5073920e-9a5d-4094-9d31-7682b351b74c","id":48368,"name":"90后家家具 萌沙发+脚踏 北欧 白蜡木实木+布艺软包+实木框架 紫色/橙黄/深灰","price":6578,"subTitle":"萌沙发+脚踏 简约线条，装点居室的每个角落"},{"headImage":"http://images.ogmall.com/7fa0c284-80d9-4a56-8e97-ac1b6b80b9e3","id":48243,"name":"90后家家具 清雅spring床头柜 北欧 白蜡木实木+免漆板+E1级中纤板 杏白色+原木色","price":1075.8,"subTitle":"清雅spring床头柜 简约线条，装点居室的每个角落"},{"headImage":"http://images.ogmall.com/9f087bab-fe64-482c-8086-0375cd029592","id":47095,"name":"90后家家具 斗柜 现代北欧 白蜡木，E1级中纤板","price":3517.8,"subTitle":"斗柜 外观圆弧，无尖角利边，更有力地保障每位使用者的安全。嵌入式的拉手设计，能完全避免传统突出式拉手容易导致的磕碰受伤。"},{"headImage":"http://images.ogmall.com/8bdf2ee3-e011-4597-8bf1-b00559152192","id":47101,"name":"90后家家具 餐椅/书桌椅 现代北欧 白蜡木，E1级中纤板","price":987.8,"subTitle":"书桌椅 外观圆弧，无尖角利边，更有力地保障每位使用者的安全。进口白腊实木料为底座，气密度高，年轮柔美，稳固耐用。"},{"headImage":"http://images.ogmall.com/cec6e373-c81f-4858-a332-5e2891ec67e8","id":47096,"name":"90后家家具 斗柜挂衣柜 现代北欧 白蜡木，E1级中纤板","price":4074.4,"subTitle":"斗柜挂衣柜 外观圆弧，无尖角利边，更有力地保障每位使用者的安全。嵌入式的拉手设计，能完全避免传统突出式拉手容易导致的磕碰受伤。"},{"headImage":"http://images.ogmall.com/e8632a31-2b89-4927-bf01-2189dfa40efe","id":47585,"name":"90后家家具 沙发 现代北欧 白蜡木，PU皮 卡其色/铁灰色","price":8223.6,"subTitle":"沙发 100%自主原创设计，让每位使用者买1，得1+N"},{"headImage":"http://images.ogmall.com/905d64c1-ef13-4dd3-94db-ae2f329f35a8","id":47102,"name":"90后家家具 书柜 现代北欧 白蜡木，E1级中纤板","price":3795,"subTitle":"书柜 外观圆弧，无尖角利边，更有力地保障每位使用者的安全。嵌入式的拉手设计，能完全避免传统突出式拉手容易导致的磕碰受伤。"},{"headImage":"http://images.ogmall.com/80261197-8835-4043-bf07-9db10f4494db","id":48367,"name":"90后家家具 大/小转角沙发 北欧 白蜡木实木脚+布艺软包+实木框架 灰色","price":9108,"subTitle":"大转角沙发 简约线条，装点居室的每个角落"}],"plan":[{"id":1,"headImage":"http://images.ogmall.com/0829339c-d96b-4538-9b62-209049d0bf9a","planName":"轻奢美式风格方案"},{"id":2,"headImage":"http://images.ogmall.com/d4e1b4ab-1519-46f5-9404-f8eaa8aa3146","planName":"现代风格方案"},{"id":3,"headImage":"http://images.ogmall.com/22d32b08-6fc8-44bc-a31a-cbd093e704be","planName":"北欧风格方案"}],"panoramic":[{"id":1,"headImage":"http://images.ogmall.com/20734ae5-9392-4a8b-be9b-c1ccb5e55c5a"},{"id":2,"headImage":"http://images.ogmall.com/47dc3c7e-4797-4db9-80b3-4c7d043fd563"},{"id":3,"headImage":"http://images.ogmall.com/2afeee75-4a39-4186-b739-4bba4681b55a"}]}
     */

    private DataBean data;

    public DataBean getData() {        if (data == null) {            data = new DataBean();        }        return data;    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * banner : [{"describe":"描述","url":"http://www.baidu.com","picture":"http://images.ogmall.com/9e7f4a4c-1da6-4aae-9ced-db225efcf22c"},{"describe":"描述","url":"http://www.baidu.com","picture":"http://images.ogmall.com/b77b8f92-d148-444b-8f97-cd36d31c83ed"},{"describe":"描述","url":"http://www.baidu.com","picture":"http://images.ogmall.com/38811ee2-8fe8-45fc-86ce-31aa547ab6cb"}]
         * moduleHeadImage : http://images.ogmall.com/69edcf55-f47d-4aac-8120-cd86648b7bd5
         * category : [{"picture":"http://images.ogmall.com/9d7c368b-7ff8-409c-bfe1-fe1f1400f953","category1Id":381,"category2Id":382,"category3Id":383,"name":"床"},{"picture":"http://images.ogmall.com/9fa998f1-ec34-420a-9ca7-ce7d1ba6e930","category1Id":381,"category2Id":393,"category3Id":397,"name":"电视柜"},{"picture":"http://images.ogmall.com/a639b067-c550-40af-a2e1-7cca867a8131","category1Id":381,"category2Id":393,"category3Id":395,"name":"沙发"},{"picture":"http://images.ogmall.com/e80f939b-1efc-40b3-8825-af0cc7db89f5","category1Id":381,"category2Id":393,"category3Id":401,"name":"鞋柜"},{"picture":"http://images.ogmall.com/48486e0d-eb9f-4f9d-a5c2-5aefc4cf1958","category1Id":381,"category2Id":409,"category3Id":410,"name":"书桌"},{"picture":"http://images.ogmall.com/ccbce599-346f-4900-b101-a2795559a2c1","category1Id":381,"category2Id":409,"category3Id":412,"name":"书柜"},{"picture":"http://images.ogmall.com/088f737c-d960-4f73-a6f0-e11a5753458b","category1Id":381,"category2Id":382,"category3Id":390,"name":"梳妆台"},{"picture":"http://images.ogmall.com/2453aec6-c2d8-4186-adf5-8f7f0955bd14","category1Id":443,"category2Id":444,"category3Id":449,"name":"台灯"},{"picture":"http://images.ogmall.com/05434c31-5c31-4db5-8bff-86ed3d361359","category1Id":381,"category2Id":414,"category3Id":415,"name":"餐椅"},{"picture":"http://images.ogmall.com/1c06a2bb-3733-4bd9-a407-4df7493e4fe9","category1Id":381,"category2Id":382,"category3Id":385,"name":"衣柜"}]
         * hotProduct : [{"headImage":"http://images.ogmall.com/5073920e-9a5d-4094-9d31-7682b351b74c","id":48368,"name":"90后家家具 萌沙发+脚踏 北欧 白蜡木实木+布艺软包+实木框架 紫色/橙黄/深灰","price":6578,"subTitle":"萌沙发+脚踏 简约线条，装点居室的每个角落"},{"headImage":"http://images.ogmall.com/7fa0c284-80d9-4a56-8e97-ac1b6b80b9e3","id":48243,"name":"90后家家具 清雅spring床头柜 北欧 白蜡木实木+免漆板+E1级中纤板 杏白色+原木色","price":1075.8,"subTitle":"清雅spring床头柜 简约线条，装点居室的每个角落"},{"headImage":"http://images.ogmall.com/9f087bab-fe64-482c-8086-0375cd029592","id":47095,"name":"90后家家具 斗柜 现代北欧 白蜡木，E1级中纤板","price":3517.8,"subTitle":"斗柜 外观圆弧，无尖角利边，更有力地保障每位使用者的安全。嵌入式的拉手设计，能完全避免传统突出式拉手容易导致的磕碰受伤。"},{"headImage":"http://images.ogmall.com/8bdf2ee3-e011-4597-8bf1-b00559152192","id":47101,"name":"90后家家具 餐椅/书桌椅 现代北欧 白蜡木，E1级中纤板","price":987.8,"subTitle":"书桌椅 外观圆弧，无尖角利边，更有力地保障每位使用者的安全。进口白腊实木料为底座，气密度高，年轮柔美，稳固耐用。"},{"headImage":"http://images.ogmall.com/cec6e373-c81f-4858-a332-5e2891ec67e8","id":47096,"name":"90后家家具 斗柜挂衣柜 现代北欧 白蜡木，E1级中纤板","price":4074.4,"subTitle":"斗柜挂衣柜 外观圆弧，无尖角利边，更有力地保障每位使用者的安全。嵌入式的拉手设计，能完全避免传统突出式拉手容易导致的磕碰受伤。"},{"headImage":"http://images.ogmall.com/e8632a31-2b89-4927-bf01-2189dfa40efe","id":47585,"name":"90后家家具 沙发 现代北欧 白蜡木，PU皮 卡其色/铁灰色","price":8223.6,"subTitle":"沙发 100%自主原创设计，让每位使用者买1，得1+N"},{"headImage":"http://images.ogmall.com/905d64c1-ef13-4dd3-94db-ae2f329f35a8","id":47102,"name":"90后家家具 书柜 现代北欧 白蜡木，E1级中纤板","price":3795,"subTitle":"书柜 外观圆弧，无尖角利边，更有力地保障每位使用者的安全。嵌入式的拉手设计，能完全避免传统突出式拉手容易导致的磕碰受伤。"},{"headImage":"http://images.ogmall.com/80261197-8835-4043-bf07-9db10f4494db","id":48367,"name":"90后家家具 大/小转角沙发 北欧 白蜡木实木脚+布艺软包+实木框架 灰色","price":9108,"subTitle":"大转角沙发 简约线条，装点居室的每个角落"}]
         * plan : [{"id":1,"headImage":"http://images.ogmall.com/0829339c-d96b-4538-9b62-209049d0bf9a","planName":"轻奢美式风格方案"},{"id":2,"headImage":"http://images.ogmall.com/d4e1b4ab-1519-46f5-9404-f8eaa8aa3146","planName":"现代风格方案"},{"id":3,"headImage":"http://images.ogmall.com/22d32b08-6fc8-44bc-a31a-cbd093e704be","planName":"北欧风格方案"}]
         * panoramic : [{"id":1,"headImage":"http://images.ogmall.com/20734ae5-9392-4a8b-be9b-c1ccb5e55c5a"},{"id":2,"headImage":"http://images.ogmall.com/47dc3c7e-4797-4db9-80b3-4c7d043fd563"},{"id":3,"headImage":"http://images.ogmall.com/2afeee75-4a39-4186-b739-4bba4681b55a"}]
         */

        private String moduleHeadImage;
        private List<BannerBean> banner;
        private List<CategoryBean> category;
        private List<HotProductBean> hotProduct;
        private List<PlanBean> plan;
        private List<PanoramicBean> panoramic;

        public String getModuleHeadImage() {
            return moduleHeadImage;
        }

        public void setModuleHeadImage(String moduleHeadImage) {
            this.moduleHeadImage = moduleHeadImage;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public List<HotProductBean> getHotProduct() {
            return hotProduct;
        }

        public void setHotProduct(List<HotProductBean> hotProduct) {
            this.hotProduct = hotProduct;
        }

        public List<PlanBean> getPlan() {
            return plan;
        }

        public void setPlan(List<PlanBean> plan) {
            this.plan = plan;
        }

        public List<PanoramicBean> getPanoramic() {
            if (panoramic == null)
                panoramic = new ArrayList<>();
            return panoramic;
        }

        public void setPanoramic(List<PanoramicBean> panoramic) {
            this.panoramic = panoramic;
        }

        public static class BannerBean {
            /**
             * describe : 描述
             * url : http://www.baidu.com
             * picture : http://images.ogmall.com/9e7f4a4c-1da6-4aae-9ced-db225efcf22c
             */

            private String introduce;
            private String url;
            private String picture;
            private int type;
            private int typeId;

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getTypeId() {
                return typeId;
            }

            public void setTypeId(int typeId) {
                this.typeId = typeId;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }

        public static class CategoryBean {
            /**
             * picture : http://images.ogmall.com/9d7c368b-7ff8-409c-bfe1-fe1f1400f953
             * category1Id : 381
             * category2Id : 382
             * category3Id : 383
             * name : 床
             */

            private String picture;
            private int category1Id;
            private int category2Id;
            private int category3Id;
            private String name;

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public int getCategory1Id() {
                return category1Id;
            }

            public void setCategory1Id(int category1Id) {
                this.category1Id = category1Id;
            }

            public int getCategory2Id() {
                return category2Id;
            }

            public void setCategory2Id(int category2Id) {
                this.category2Id = category2Id;
            }

            public int getCategory3Id() {
                return category3Id;
            }

            public void setCategory3Id(int category3Id) {
                this.category3Id = category3Id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class HotProductBean {
            /**
             * headImage : http://images.ogmall.com/5073920e-9a5d-4094-9d31-7682b351b74c
             * id : 48368
             * name : 90后家家具 萌沙发+脚踏 北欧 白蜡木实木+布艺软包+实木框架 紫色/橙黄/深灰
             * price : 6578
             * subTitle : 萌沙发+脚踏 简约线条，装点居室的每个角落
             */

            private String headImage;
            private int id;
            private String name;
            private String price;
            private String subTitle;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }
        }

        public static class PlanBean {
            /**
             * id : 1
             * headImage : http://images.ogmall.com/0829339c-d96b-4538-9b62-209049d0bf9a
             * planName : 轻奢美式风格方案
             */

            private int id;
            private String headImage;
            private String planName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }

            public String getPlanName() {
                return planName;
            }

            public void setPlanName(String planName) {
                this.planName = planName;
            }
        }

        public static class PanoramicBean {
            /**
             * id : 1
             * headImage : http://images.ogmall.com/20734ae5-9392-4a8b-be9b-c1ccb5e55c5a
             */

            private int id;
            private String headImage;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHeadImage() {
                return headImage;
            }

            public void setHeadImage(String headImage) {
                this.headImage = headImage;
            }
        }
    }
}