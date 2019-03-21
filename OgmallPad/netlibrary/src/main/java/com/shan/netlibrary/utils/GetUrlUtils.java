package com.shan.netlibrary.utils;

import android.Manifest;
import android.os.Environment;

import com.google.gson.Gson;
import com.junshan.pub.App;
import com.junshan.pub.utils.FileUtils;
import com.junshan.pub.utils.PermissionUtis;
import com.shan.netlibrary.bean.UrlBean;

import java.io.File;

/**
 * 获取环境配置
 * Created by chenjunshan on 17-5-18.
 */

public class GetUrlUtils {
    private static String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


    /**
     * 获取服务IP地址
     *
     * @return
     */
    public static String getBaseUrl() {
        try {
            if (PermissionUtis.checkPermissions(App.getInstance(), PERMISSIONS)) {
                return "";
            }
            String baseUrl = "";
            File file = new File(Environment.getExternalStorageDirectory(), "hjqhtest");
            file = new File(file, "hjqhtest.txt");
            StringBuilder stringBuilder = FileUtils.readFile(file.getAbsolutePath(), "utf-8");
            UrlBean urlBean = new Gson().fromJson(stringBuilder.toString(), UrlBean.class);
            if (urlBean != null && urlBean.getData().size() > 0) {
                for (int i = 0; i < urlBean.getData().size(); i++) {
                    UrlBean.DataBean dataBean = urlBean.getData().get(i);
                    if (dataBean.isIsCheck()) {
                        baseUrl = dataBean.getUrl();
                        break;
                    }
                }
            }
            return baseUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
