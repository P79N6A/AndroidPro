package com.ogmallpad.bean;

/**
 * Created by Administrator on 2018/12/21
 */

public class JiaGeBean {
    public int minPrice;
    public int maxPrice;
    public boolean isCheck;

    public JiaGeBean(int minPrice, int maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.isCheck = false;
    }
}
