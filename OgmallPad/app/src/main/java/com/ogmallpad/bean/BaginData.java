package com.ogmallpad.bean;

import com.shan.netlibrary.bean.RoomTypespecsBean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/8.
 */

public class BaginData {
    public String currentVpName;
    public List<RoomTypespecsBean.DataBean.CategoryListBean.ProductBean> dataBean;
    private static BaginData data = new BaginData();
    private BaginData(){}
    public static synchronized BaginData getBaginData(){
        return data;
    }
}
