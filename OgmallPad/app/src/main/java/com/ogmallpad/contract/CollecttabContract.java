package com.ogmallpad.contract;

import com.shan.netlibrary.bean.CollectsstylesBean;
import com.shan.netlibrary.contract.BaseView;

import java.util.List;

/**
 * 收藏
 * Created by chenjunshan on 2018-09-27.
 */

public interface CollecttabContract {
    interface Model {
    }

    interface View extends BaseView {
        void setStyleName(String styleName);
    }

    interface Presenter {
        void showStyle(boolean isStyle, List<CollectsstylesBean.DataBean> data);
    }
}
