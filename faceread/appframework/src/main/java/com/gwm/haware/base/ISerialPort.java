package com.gwm.haware.base;


import okio.ByteString;

/**
 * create time: 2018/5/7
 * author: Mr Huang
 * describe:
 */
public interface ISerialPort {
    void readData();
    void sendData(byte[] bytes);
    void releaseSerialPortChannel();
    void setOnReadDataListener(OnReadDataListener listener);

    public interface OnReadDataListener{
        void receiveData(ByteString byteString);
        void receiveData(byte[] buf);
        void connectionTimeOut();
    }
}
