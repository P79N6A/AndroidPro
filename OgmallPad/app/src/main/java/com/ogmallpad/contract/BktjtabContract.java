package com.ogmallpad.contract;

import com.ogmallpad.bean.ShopScreenBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 爆款推荐
 * Created by chenjunshan on 2018-07-06.
 */

public interface BktjtabContract {
    interface Model {
    }

    interface View extends BaseView {
        void query(ShopScreenBean bean);
    }

    interface Presenter {
    }
}
