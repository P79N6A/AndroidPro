package com.ogmall.faceread.bean;

/**
 * Created by 13258 on 2018/12/16.
 */


import java.io.Serializable;

/**
 * {"code":200,"message":"success","data":{"companyName":"轩之风有限责任公司","name":"龚伟明","videoId":"fbbe5fd32f1e42169c63435f40bcc967","avatar":"http://192.168.1.116/image/20190312/175af6bdc11d4711bb3453b571b156b7.jpg"},"version":"1.0.0","mobile":""}
 */
public class FaceImgBean implements Serializable{
    public int code;
    public String message;
    public Result data;

    public static class Result implements Serializable{
        public String companyName;
        public String name;
        public String videoId;
        public String avatar;
        public int user_type;

    }
}
