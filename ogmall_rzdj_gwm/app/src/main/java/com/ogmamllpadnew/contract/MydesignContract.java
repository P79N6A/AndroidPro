package com.ogmamllpadnew.contract;

import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 我的设计师
 * Created by 陈俊山 on 2018-11-16.
 */

public interface MydesignContract {
    interface Model {
    }

    interface View extends BaseView {
        void toAddress(int type, String id);
    }

    interface Presenter {
        void showAddDesigner();

        void setAddressBean(AddressCallbackBean addressBean);

        void addOnsuccess();
    }
}
