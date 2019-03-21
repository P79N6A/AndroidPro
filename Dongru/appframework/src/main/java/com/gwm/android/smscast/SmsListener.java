package com.gwm.android.smscast;

/**
 * Created by Administrator on 2017/7/31.
 */

public interface SmsListener {
    /**
     * 当接收到消息后会调用该方法
     * @param phone 手机号码
     * @param smsContent 短信内容
     */
    public void sms(String phone, String smsContent);
}
