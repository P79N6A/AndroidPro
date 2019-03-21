package com.ogmamllpadnew.contract;

import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 我的店铺商
 * Created by 陈俊山 on 2018-11-12.
 */

public interface MyshopContract {
    interface Model {
    }

    interface View extends BaseView {
        void toAddress(int type, String id);
    }

    interface Presenter {
        void showAddShop();

        void setAddressBean(AddressCallbackBean addressBean);

    }
}
