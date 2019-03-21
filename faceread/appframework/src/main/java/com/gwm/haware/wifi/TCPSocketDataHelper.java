package com.gwm.haware.wifi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.gwm.haware.base.ISerialPort;
import com.gwm.util.MyLogger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPSocketDataHelper implements ISerialPort {

    private static final String WIFI_TCP_IP = "WIFI_TCP_IP";
    private static final String WIFI_TCP_PORT = "WIFI_TCP_PORT";

    private InputStream inputStream;
    private OutputStream outputStream;
    private OnReadDataListener listener;
    private Socket socket;
    InetSocketAddress address;

    private static TCPSocketDataHelper socketDataHelper;
    private boolean sendState;

    public static synchronized TCPSocketDataHelper getInstance(Context context){
        if (socketDataHelper == null){
            socketDataHelper = new TCPSocketDataHelper(context);
        }
        return socketDataHelper;
    }

    private TCPSocketDataHelper(Context context){
        try {
            address = new InetSocketAddress(InetAddress.getByName("192.168.4.1"),6666);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        socket = new Socket();
    }

    public void connect(){
        try {
            socket.connect(address);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            MyLogger.kLog().e("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
            MyLogger.kLog().e("连接失败");
        }
    }
    private long currentTime;
    @Override
    public void readData() {
        try {
            while (true) {
                if(socket.isConnected() && sendState) {
                    int count = 0;
                    while(count == 0){
                        count = inputStream.available();
                        long replyTime = System.currentTimeMillis();
                        if (replyTime - currentTime > 4000 && count == 0 && sendState){
                            listener.connectionTimeOut();
                            currentTime = replyTime;
                        }
                    }
                    MyLogger.kLog().e("有数据来了....");
                    if(count != 0) {
                        sendState = false;
                        System.out.println(count);
                        byte[] bt = new byte[count];
                        int readCount = 0;
                        while (readCount < count) {
                            int read = inputStream.read(bt, readCount, count - readCount);
                            System.out.println(read);
                            if (read == -1) {
                                break;
                            }
                            readCount += read;
                        }
                        if (listener != null){
                            listener.receiveData(bt);
                        }
                    }else {
                        MyLogger.kLog().e("socket 没有接收到消息");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MyLogger.kLog().e("socket 读取异常 "+e.getMessage());
        }
    }

    @Override
    public void sendData(byte[] bytes) {
        try {
            sendState = true;
            if (socket.isConnected()) {
                outputStream.write(bytes);
                currentTime = System.currentTimeMillis();
                MyLogger.kLog().e("指令发送成功  " + bytearraytoString(bytes));
            }else {
                connect();
                sendData(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseSerialPortChannel() {
        try {
            sendState = false;
            outputStream.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOnReadDataListener(OnReadDataListener listener) {
        this.listener = listener;
    }

    public static String bytetoHex1(byte num){
        char[] chs=new char[2];

        byte index=(byte)(((num&((byte)0xf0))>>4)&0x0f);
        if(index>9)
            chs[0]=((char)(index-10+'A'));
        else  chs[0]=(char)(index+'0');
        index=(byte)((num&((byte)0x0f)));
        if(index>9)
            chs[1]=((char)(index-10+'A'));
        else  chs[1]=(char)(index+'0');

        return String.valueOf(chs);
    }
    //将数组转为字符串
    public static String bytearraytoString(byte[] arr){
        String temp = "";
        for(int i = 0;i<arr.length;i++){
            temp = temp +" "+ bytetoHex1(arr[i]);
        }
        return temp;
    }

    public static class TCPSocketDataReadService extends JobIntentService {
        public static final String SERVICE_NAME = "TCPSocketDataReadService";

        public static void enqueueWork(Context context, Intent work) {
            enqueueWork(context, TCPSocketDataReadService.class, 1, work);
        }
        @Override
        protected void onHandleWork(@NonNull Intent intent) {
            TCPSocketDataHelper instance = TCPSocketDataHelper.getInstance(getApplicationContext());
            instance.connect();
            instance.readData();
        }
    }
}
