package com.gwm.haware.bluetooth;


import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.gwm.haware.base.ISerialPort;
import com.gwm.haware.base.Message;
import com.gwm.util.AppUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

import okio.ByteString;

public class BluetoothDataHelper implements ISerialPort{
    private BluetoothSocket btsocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    private static String BLUETOOTH_UUID = "com.gwm.haware.BLUETOOTH_UUID";
    private static String startStr = "{&<STARY>";
    private static String endStr = "&<END>}";
    private OnReadDataListener listener;

    private static BluetoothDataHelper helper;

    private BluetoothDataHelper(Context context,BluetoothDevice device){
        String uuidStr = AppUtils.getInstance(context).getMetaValue(BLUETOOTH_UUID);
        UUID uuid = UUID.fromString(uuidStr);
        try {
            btsocket = device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized BluetoothDataHelper getHelper(Context context,BluetoothDevice device) {
        if (helper == null){
            helper = new BluetoothDataHelper(context,device);
        }
        return helper;
    }

    /**
     * 连接蓝牙设备
     */
    public void openConnection(){
        if (btsocket != null && !btsocket.isConnected()){
            try {
                btsocket.connect();
                if (btsocket.isConnected()){
                    inputStream = btsocket.getInputStream();
                    outputStream = btsocket.getOutputStream();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 需要知道一条消息的开始跟结束的标志，不然无法读取
     */
    @Override
    public void readData() {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer str = new StringBuffer();
        while(true){
            try {
                str.append(br.readLine());
                String string = str.toString();
                if (string.startsWith(startStr) && string.endsWith(endStr)){
                    listener.receiveData(ByteString.encodeUtf8(string));
                    str.delete(0,string.length());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendData(byte[] bytes) {
        try {
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseSerialPortChannel() {
        try {
            outputStream.close();
            inputStream.close();
            btsocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOnReadDataListener(OnReadDataListener listener) {
        this.listener = listener;
    }

    public static class BluetoothDataReadService extends IntentService{
        public BluetoothDataReadService() {
            super("BluetoothDataReadService");
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {

        }
    }
}
