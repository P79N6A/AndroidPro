package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

/**
 * Created by chenjunshan on 17-3-21.
 */

public class LoginBean extends BaseBean {
    /**
     * data : {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbl9pbmZvIjoie1wiY29udGFjdFBob25lXCI6XCIxMzgwMDEzODExM1wiLFwiaWRcIjo2NyxcInR5cGVcIjozMCxcInR5cGVBcmVhQWdlbnRJZFwiOjU5LFwidHlwZUZhY3RvcnlJZFwiOjU2fSIsImlhdCI6MTU0MjExMjk0MCwibWFyayI6IjY5YjAyZWU1LTNhM2EtNGZiMC04YTU5LWZhODVmNWFmNjE3OCJ9.3rTr8OqzXpqcZhoeZDmusNb8c1X0urWvtRmvPND3bPE"}
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
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbl9pbmZvIjoie1wiY29udGFjdFBob25lXCI6XCIxMzgwMDEzODExM1wiLFwiaWRcIjo2NyxcInR5cGVcIjozMCxcInR5cGVBcmVhQWdlbnRJZFwiOjU5LFwidHlwZUZhY3RvcnlJZFwiOjU2fSIsImlhdCI6MTU0MjExMjk0MCwibWFyayI6IjY5YjAyZWU1LTNhM2EtNGZiMC04YTU5LWZhODVmNWFmNjE3OCJ9.3rTr8OqzXpqcZhoeZDmusNb8c1X0urWvtRmvPND3bPE
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
