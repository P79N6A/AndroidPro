package com.ogmall.faceread.datapresenter;

import android.content.Context;

import com.gwm.android.ACache;
import com.gwm.base.BaseDataPresenter;
import com.gwm.base.ViewModelCallback;
import com.gwm.retrofit.Observable;
import com.ogmall.faceread.bean.FaceImgBean;
import com.ogmall.faceread.contact.HttpInterface;

import java.io.File;

/**
 * Created by Administrator on 2018/12/13.
 */

/**
 * 后台文件对比获取个人信息
 */
public class FileDBDataPresenter extends BaseDataPresenter {
    public FileDBDataPresenter(ViewModelCallback callback, Context context) {
        super(callback, context, HttpInterface.class);
    }

    public void getInfo(String path){
        File file = new File(path);
        Observable<FaceImgBean> bean = ((HttpInterface)getHttpPresenter()).getInfo(file);
        addHttpSubscriber(bean,FaceImgBean.class);
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
