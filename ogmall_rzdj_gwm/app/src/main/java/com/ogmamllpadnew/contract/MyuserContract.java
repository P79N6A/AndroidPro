package com.ogmamllpadnew.contract;

import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.bean.SelectBean;
import com.shan.netlibrary.bean.UserselectCustomerBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 我的客户
 * Created by 陈俊山 on 2018-11-12.
 */

public interface MyuserContract {
    interface Model {
    }

    interface View extends BaseView {
        void toAddress(int type,String id);
        void toSelectType();
    }

    interface Presenter {
        void editUser(UserselectCustomerBean.DataBean item);

        //设置客户类型
        void setUserType(SelectBean selectBean);

        void setAddressBean(AddressCallbackBean addressBean);
        void dissMiss();
    }
}
