// HermsMessageAidl.aidl
package com.gwm;

import com.gwm.messagesendreceive.HermsMessage;
interface HermsMessageAidl {
    void postAll(in HermsMessage obj);
    void post(in HermsMessage obj);
}
