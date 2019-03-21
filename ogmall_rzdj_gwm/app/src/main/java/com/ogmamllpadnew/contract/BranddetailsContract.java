package com.ogmamllpadnew.contract;

import com.shan.netlibrary.contract.BaseView;

/**
 * 品牌详情
 * Created by 陈俊山 on 2018-11-07.
 */

public interface BranddetailsContract {
    interface Model {
    }

    interface View extends BaseView {
    }

    interface Presenter {
        void showRqDialog(String image);
    }
}
