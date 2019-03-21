package com.ogmall.faceread.contact;

import com.gwm.annotation.FileUpload;
import com.gwm.annotation.HTTP;
import com.gwm.annotation.Header;
import com.gwm.annotation.JSON;
import com.gwm.annotation.QueryUrl;
import com.gwm.retrofit.Observable;
import com.ogmall.faceread.bean.BDHTBean;
import com.ogmall.faceread.bean.BDToken;
import com.ogmall.faceread.bean.FaceImgBean;
import com.ogmall.faceread.bean.HtBean;

import java.io.File;

/**
 * Created by Administrator on 2018/12/13.
 */

public interface HttpInterface {
    @HTTP(url = "https://aip.baidubce.com/rest/2.0/face/v3/faceverify?" +
            "access_token=24.14551cee484be51947459365fd6c662f.2592000.1547195651.282335-15140424",
            way = HTTP.WAY.POST)
    Observable<BDHTBean> upload(@JSON String file);

    @HTTP(url = "https://aip.baidubce.com/rest/2.0/face/v3/faceverify",way = HTTP.WAY.POST)
    Observable<BDHTBean> baiduht(@QueryUrl("access_token")String accessToken,@JSON String file);

    @HTTP(url = "https://aip.baidubce.com/oauth/2.0/token?" +
            "grant_type=client_credentials&" +
            "client_id=x7wbjQ6RjEPSnaCkf0aQTyiu&" +
            "client_secret=Rawl3Yd6h3Gbk55dj5fFcmszzQaH44s0",
        way = HTTP.WAY.POST)
    Observable<BDToken> getToken();


    //https://ougmall.com
    @HTTP(url = "https://face.ogmall.com/app/faceCheck?macId=158866&type=1",way = HTTP.WAY.FILE_UPLOAD)
    Observable<FaceImgBean> getInfo(@FileUpload("avatar")File file);



    @Header({"Authorization:APPCODE f0915a791e62489fa368057bdc1257a3",
            "Content-Type:application/json; charset=UTF-8"})
    @HTTP(url = "http://livebody.market.alicloudapi.com/checklive",way = HTTP.WAY.POST)
    Observable<HtBean> aliHt(@JSON String json);
}
