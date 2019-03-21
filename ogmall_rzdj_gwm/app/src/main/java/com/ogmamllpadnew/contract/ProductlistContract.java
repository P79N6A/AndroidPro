package com.ogmamllpadnew.contract;

import com.shan.netlibrary.contract.BaseView;

/**
 * 商品列表
 * Created by 陈俊山 on 2018-11-08.
 */

public interface ProductlistContract {
    interface Model {
    }

    interface View extends BaseView {
    }

    interface Presenter {
        void showRqDialog(String image);
    }
}
