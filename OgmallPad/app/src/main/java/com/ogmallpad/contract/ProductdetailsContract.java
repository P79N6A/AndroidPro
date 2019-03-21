package com.ogmallpad.contract;

import com.shan.netlibrary.contract.BaseView;

/**
 * 商品详情
 * Created by chenjunshan on 2018-10-10.
 */

public interface ProductdetailsContract {
    interface Model {
    }

    interface View extends BaseView {
    }

    interface Presenter {
        void showRqDialog(String image);
    }
}
