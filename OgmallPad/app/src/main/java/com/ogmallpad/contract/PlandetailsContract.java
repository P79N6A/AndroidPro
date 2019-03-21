package com.ogmallpad.contract;

import com.shan.netlibrary.bean.DesigndetailsBean;
import com.shan.netlibrary.contract.BaseView;

import java.util.List;

/**
 * 方案详情
 * Created by chenjunshan on 2018-07-02.
 */

public interface PlandetailsContract {
    interface Model {
    }

    interface View extends BaseView {
    }

    interface Presenter {
        void loadBottomVp(List<DesigndetailsBean.DataBean.ProductsBean> list);

        void lookImage(String img);
    }
}
