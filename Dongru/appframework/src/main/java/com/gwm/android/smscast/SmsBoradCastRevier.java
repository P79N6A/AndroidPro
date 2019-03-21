package com.gwm.android.smscast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.gwm.base.BaseAppcation;
import com.gwm.base.BaseBroadcastReceiver;

/**
 * Created by Administrator on 2017/7/31.
 * 短信监听器的底层代码  监听了短信消息的广播
 */

public class SmsBoradCastRevier extends BaseBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Object[] pdus = (Object[])bundle.get("pdus");
                if (pdus != null && pdus.length > 0){
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    int length = messages.length;
                    for (int i = 0; i < length; i++){
                        byte[] pdu = (byte[])pdus[i];
                        messages[i] = SmsMessage.createFromPdu(pdu);
                    }
                    for (SmsMessage msg : messages){
                        // 获取短信内容
                        String content = msg.getMessageBody();  //内容
                        String sender = msg.getOriginatingAddress();  //号码
                        SmsListener listener = BaseAppcation.getInstance().getSmsListener().get(sender);
                        if(listener != null){
                            listener.sms(sender,content);
                        }
                    }
                }
            }
        }
    }
}
