package com.ogmallpad.contract;

import com.shan.netlibrary.bean.RoomTypelistBean;
import com.shan.netlibrary.contract.BaseView;

/**
 * 拎包入住
 * Created by chenjunshan on 2018-08-21.
 */

public interface BaginLeftContract {
    interface Model {
    }

    interface View extends BaseView {
        void roomTypespecs(String roomTypeId);

        void initViewPagerData();

        void bagcategorys();

        void refreshData(int position);

        void addDialogClose();
    }

    interface Presenter {
        void showHx(android.view.View view, RoomTypelistBean typelistBean);

        void showEdit(android.view.View view);

        void showAdd(android.view.View view, int position, int vpPos);
    }
}
