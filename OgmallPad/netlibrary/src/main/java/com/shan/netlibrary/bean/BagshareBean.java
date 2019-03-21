package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * 在线设计方案一键分享
 * Created by chenjunshan on 2018-08-22.
 */

public class BagshareBean extends BaseBean {

    /**
     * data : {"url":"http://testb2b2c.ougohome.com/design/online_list_mobile?houseTypeId=45&name=黑色的后果都是&houseStyleId=62&area=165.0&data_id=3336"}
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
         * url : http://testb2b2c.ougohome.com/design/online_list_mobile?houseTypeId=45&name=黑色的后果都是&houseStyleId=62&area=165.0&data_id=3336
         */

        private String url;

        public String getUrl() {
            if (url == null)
                url = "";
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}