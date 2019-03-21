package com.ogmamllpadnew.contract;

import com.shan.netlibrary.bean.BrandselectBrandByCategory1Bean;
import com.shan.netlibrary.bean.BrandselectBrandByCategoryAndOldHouseStyleBean;
import com.shan.netlibrary.bean.BrandselectOldHouseStyleByCategoryBean;
import com.shan.netlibrary.contract.BaseView;

import java.util.List;

/**
 * 拎包入住右边
 * Created by 陈俊山 on 2018-11-10.
 */

public interface BaginrightContract {
    interface Model {
    }

    interface View extends BaseView {
        void setBrandId(String brandId);
    }

    interface Presenter {
        void showSelectDialog(final List<BrandselectOldHouseStyleByCategoryBean.DataBean> fgData, final List<BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean> ppData);

        void dismissDialog();
    }
}
