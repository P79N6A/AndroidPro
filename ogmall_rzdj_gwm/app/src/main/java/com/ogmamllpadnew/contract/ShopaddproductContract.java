package com.ogmamllpadnew.contract;

import com.shan.netlibrary.contract.BaseView;

/**
 * 店铺商-添加商品
 * Created by 陈俊山 on 2018-12-05.
 */

public interface ShopaddproductContract {
    interface Model {
    }

    interface View extends BaseView {
        boolean isAddProduct();

        void setClassifyId1(int classifyId1);

        void setClassifyId2(int classifyId2);

        void setClassifyId3(int classifyId3);

        void setClassifyName1(String classifyName1);

        void setClassifyName2(String classifyName2);

        void setClassifyName3(String classifyName3);
    }

    interface Presenter {
        void showClassify();

        void showAddSize();
    }
}
