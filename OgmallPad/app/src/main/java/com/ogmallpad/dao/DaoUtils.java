package com.ogmallpad.dao;


import com.ogmallpad.MyApp;
import com.ogmallpad.bean.VoiceDao;

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
