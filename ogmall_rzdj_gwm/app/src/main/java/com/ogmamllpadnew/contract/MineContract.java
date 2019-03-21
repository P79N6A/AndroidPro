package com.ogmamllpadnew.contract;

import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 我的
 * Created by 陈俊山 on 2018-11-02.
 */

public interface MineContract {
    interface Model {
    }

    interface View extends BaseView {
        void setDismissEditDialog(boolean dismissEditDialog);

        void selectPicture();
        void toAddress(int type,String id);
    }

    interface Presenter {
        void addUser();

        void clearUserInfo();

        void changePasswd();
        void changeDetails();
        void updatePasswordSuccess();

        void refreshHeadImage(String img);

        void setAddressBean(AddressCallbackBean addressBean);
    }
}
