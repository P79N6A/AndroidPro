package com.ogmamllpadnew.contract;

import com.ogmamllpadnew.bean.PlanCheckBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 方案
 * Created by 陈俊山 on 2018-11-02.
 */

public interface PlanContract {
    interface Model {
    }

    interface View extends BaseView {
        void setAuditState(PlanCheckBean bean);
    }

    interface Presenter {
        void showStatusDialog();
    }
}
