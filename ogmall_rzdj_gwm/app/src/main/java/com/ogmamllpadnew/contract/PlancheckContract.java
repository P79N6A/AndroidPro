package com.ogmamllpadnew.contract;

import com.ogmamllpadnew.bean.PlanCheckBean;
import com.shan.netlibrary.bean.PlanselectPlanOfOneselfDesignerBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 方案审核
 * Created by 陈俊山 on 2018-11-17.
 */

public interface PlancheckContract {
    interface Model {
    }

    interface View extends BaseView {
        void setAuditState(PlanCheckBean auditState);
    }

    interface Presenter {
        void showStatusDialog();

        void showPlanCheck(PlanselectPlanOfOneselfDesignerBean.DataBean item);
    }
}
