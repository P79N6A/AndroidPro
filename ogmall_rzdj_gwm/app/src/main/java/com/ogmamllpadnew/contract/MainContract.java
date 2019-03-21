package com.ogmamllpadnew.contract;

import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.shan.netlibrary.bean.UpgradeBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 首页
 * Created by chenjunshan on 2018-03-09.
 */

public interface MainContract {
    interface Model {
    }

    interface View extends BaseView {
        void setUserStatus(boolean isShow);
        void toAddress(int type,String id);
    }

    interface Presenter {
        void getLastVersion();

        void showUpgradeDialog(UpgradeBean baseBean);

        void startDownload();

        void queryDialog();

        void hintDialog();

        void addUser();

        void clearUserInfo();

        void setAddressBean(AddressCallbackBean addressBean);
    }
}
