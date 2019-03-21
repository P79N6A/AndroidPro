package com.ogmallpad.contract;

import com.shan.netlibrary.contract.BaseView;

/**
 * 品牌家具
 * Created by chenjunshan on 2018-07-03.
 */

public interface BrandproductContract {
    interface Model {
    }

    interface View extends BaseView {
    }

    interface Presenter {
        void showRqDialog(String image);
    }
}
