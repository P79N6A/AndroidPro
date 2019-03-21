package com.ogmamllpadnew.contract;

import com.shan.netlibrary.bean.HandbagselectAllHouseTypeBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 拎包入住左边
 * Created by 陈俊山 on 2018-11-10.
 */

public interface BaginlefttabContract {
    interface Model {
    }

    interface View extends BaseView {
        void setHouseName(String name);

        void initTitleData();
    }

    interface Presenter {
        void showHxDialog(HandbagselectAllHouseTypeBean bean);

        void showEditDialog();

        void dismissDialog();
    }
}
