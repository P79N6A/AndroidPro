package com.ogmallpad.contract;

import com.shan.netlibrary.contract.BaseView;

/**
 * 商品清单
 * Created by chenjunshan on 2018-07-02.
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
