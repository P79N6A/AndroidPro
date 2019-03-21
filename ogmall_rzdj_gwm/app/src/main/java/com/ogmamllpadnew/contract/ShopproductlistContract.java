package com.ogmamllpadnew.contract;

import com.shan.netlibrary.contract.BaseView;

/**
 * 店铺商-商品列表
 * Created by 陈俊山 on 2018-12-05.
 */

public interface ShopproductlistContract {
    interface Model {
    }

    interface View extends BaseView {
        void setSellStatus(String sellStatus,String sellStatusName);

        void setStatus(String status, String statusName);
    }

    interface Presenter {
        void showStatus1Dialog();

        void showStatus2Dialog();
    }
}
