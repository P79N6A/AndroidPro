package com.ogmallpad.dao;

import com.ogmallpad.bean.Voice;
import com.ogmallpad.bean.VoiceDao;

import java.util.List;

/**
 * Statiscs操作工具类
 * Created by chenjunshan on 18-3-9.
 */

public class VoiceUtils {

    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param voice
     */
    public static void insert(Voice voice) {
        DaoUtils.getStatisticsDao().insertOrReplace(voice);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void delete(long id) {
        DaoUtils.getStatisticsDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param voice
     */
    public static void update(Voice voice) {
        DaoUtils.getStatisticsDao().update(voice);
    }

    /**
     * 查询条件为Type=id的数据
     *
     * @return
     */
    public static List<Voice> queryId(int id) {
        return DaoUtils.getStatisticsDao().queryBuilder().where(VoiceDao.Properties.Id.eq(id)).list();
    }

    /**
     * 查询条件为Type=customerId的数据
     *
     * @return
     */
    public static List<Voice> queryCustomerId(int customerId) {
        return DaoUtils.getStatisticsDao().queryBuilder().where(VoiceDao.Properties.CustomerId.eq(customerId)).list();
    }

    /**
     * 查询全部数据
     */
    public static List<Voice> queryAll() {
        return DaoUtils.getStatisticsDao().loadAll();
    }
}
