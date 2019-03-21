package com.ogmallpad.contract;

import com.ogmallpad.bean.ShopScreenBean;
import com.shan.netlibrary.bean.BrandlistBean;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 拎包入住
 * Created by chenjunshan on 2018-08-21.
 */

public interface BaginRightContract {
    interface Model {
    }

    interface View extends BaseView {
        void setMinPrice(String minPrice);

        void setMaxPrice(String maxPrice);

        void setStyleId(String styleId);

        void setBrandId(String brandId);

        void query();

        void query(ShopScreenBean bean);
    }

    interface Presenter {
        void showType(android.view.View view, StylesBean stylesBean, BrandlistBean brandlistBean);

        void showRqDialog();

        void setRqCode(String image);

        void share(String name,String area);
        void addShop();

        void showShop();
    }
}
