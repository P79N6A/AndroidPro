package com.ogmamllpadnew.dao;


import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.bean.VoiceDao;

/**
 * Created by root on 18-3-9.
 */

public class DaoUtils {

    /**
     * 获取Statistics表的Dao
     *
     * @return
     */
    public static VoiceDao getStatisticsDao() {
        return MyApp.getDaoSession().getVoiceDao();
    }
}
