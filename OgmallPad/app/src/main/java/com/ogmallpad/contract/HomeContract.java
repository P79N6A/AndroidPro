package com.ogmallpad.contract;

import com.shan.netlibrary.contract.BaseView;

/**
 * 首页
 * Created by chenjunshan on 2018-07-02.
 */

public interface HomeContract {
    interface Model {
    }

    interface View extends BaseView {
    }

    interface Presenter {
        public void hintDialog();
    }
}
