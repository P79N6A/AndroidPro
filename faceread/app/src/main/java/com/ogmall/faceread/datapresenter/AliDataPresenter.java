package com.ogmall.faceread.datapresenter;

import android.content.Context;

import com.gwm.android.ACache;
import com.gwm.base.BaseDataPresenter;
import com.gwm.base.ViewModelCallback;
import com.gwm.retrofit.Observable;
import com.ogmall.faceread.bean.HtBean;
import com.ogmall.faceread.contact.HttpInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2018/12/14.
 */

public class AliDataPresenter extends BaseDataPresenter {
    public AliDataPresenter(ViewModelCallback callback, Context context) {
        super(callback, context, HttpInterface.class);
    }


    public void aliHt(String image){
        try {
            JSONObject json = new JSONObject();
            json.put("ImgBase64",image);
            Observable<HtBean> observable = ((HttpInterface)getHttpPresenter()).aliHt(json.toString());
            addHttpSubscriber(observable,HtBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNextFileResult(ACache cache, File response, int id) {

    }

    @Override
    protected void onNextResult(ACache cache, Object response, int id) {

    }

    @Override
    protected void onErrorResult(Exception e, int id) {

    }
}
