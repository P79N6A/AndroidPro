package com.ogmall.faceread.datapresenter;

import android.content.Context;

import com.gwm.android.ACache;
import com.gwm.base.BaseDataPresenter;
import com.gwm.base.ViewModelCallback;
import com.gwm.retrofit.Observable;
import com.ogmall.faceread.bean.BDHTBean;
import com.ogmall.faceread.bean.BDToken;
import com.ogmall.faceread.contact.CacheContact;
import com.ogmall.faceread.contact.HttpInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2018/12/13.
 */

public class BaiduHTDataPresenter extends BaseDataPresenter {
    private String image;

    public BaiduHTDataPresenter(ViewModelCallback callback, Context context) {
        super(callback, context, HttpInterface.class);
    }

    /**
     * 百度活体检测请求
     * @param image
     */
    public void ht(String image){
        this.image = image;
        String token = getCache().getAsString(CacheContact.TOKEN);
        if(token == null){
            //TODO 获取TOKEN
            Observable<BDToken> observable = ((HttpInterface)getHttpPresenter()).getToken();
            addHttpSubscriber(observable,BDToken.class);
        }else {
            try {
                JSONObject json = new JSONObject();
                json.put("image",image);
                json.put("image_type","BASE64");
                json.put("face_field","age,beauty,expression");
                Observable<BDHTBean> observable = ((HttpInterface) getHttpPresenter()).baiduht(token, image);
                addHttpSubscriber(observable, BDHTBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onNextFileResult(ACache cache, File response, int id) {

    }

    @Override
    protected void onNextResult(ACache cache, Object response, int id) {
        if (response instanceof BDToken){   //请求的是TOKEN
            getCache().put(CacheContact.TOKEN,((BDToken)response).access_token,30*24*60*60);

            Observable<BDHTBean> observable = ((HttpInterface) getHttpPresenter()).baiduht(((BDToken)response).access_token, image);
            addHttpSubscriber(observable, BDHTBean.class);
        }
    }

    @Override
    protected void onErrorResult(Exception e, int id) {

    }
}
