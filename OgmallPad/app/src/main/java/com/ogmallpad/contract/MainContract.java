package com.ogmallpad.contract;

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
        void tabSelect(int position);
    }

    interface Presenter {
        void getLastVersion();

        void showUpgradeDialog(UpgradeBean baseBean);

        void startDownload();
    }
}
