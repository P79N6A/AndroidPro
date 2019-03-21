package com.ogmamllpadnew.contract;

import com.shan.netlibrary.bean.GoodsselectGoodsSpecificationBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 店铺商-规格列表
 * Created by 陈俊山 on 2018-12-11.
 */

public interface ShopspecificationlistContract {
    interface Model {
    }

    interface View extends BaseView {
        void uploadPictures();
    }

    interface Presenter {
        void showAddSize(int goodsId, boolean isEdit, GoodsselectGoodsSpecificationBean.DataBean item);

        void bindImage(String localPath);
    }
}
