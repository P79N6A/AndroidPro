package com.ogmamllpadnew.contract;

import com.shan.netlibrary.bean.HandbagselectHandbagCategoryBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 拎包入住
 * Created by 陈俊山 on 2018-11-02.
 */

public interface BaginContract {
    interface Model {
    }

    interface View extends BaseView {
    }

    interface Presenter {
        void showRqDialog();

        void setRqCode(String image);

        void share(String name, String area);

        void showClassifyDialog(HandbagselectHandbagCategoryBean categoryBean);
    }
}
