package com.ogmamllpadnew.bean;

import com.shan.netlibrary.bean.BrandselectBrandByCategory1Bean;
import com.shan.netlibrary.bean.BrandselectBrandByCategoryAndOldHouseStyleBean;
import com.shan.netlibrary.bean.BrandselectOldHouseStyleByCategoryBean;

/**
 * Created by Administrator on 2018/12/22.
 */

public class ShopScreenBean {
    private int fgData;
    private int ppData;
    private int minPrice,maxPrice;
    private BrandselectBrandByCategory1Bean.DataBean dataBean1;

    public int getFgData() {
        return fgData;
    }

    public void setFgData(int fgData) {
        this.fgData = fgData;
    }

    public int getPpData() {
        return ppData;
    }

    public void setPpData(int ppData) {
        this.ppData = ppData;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
}
