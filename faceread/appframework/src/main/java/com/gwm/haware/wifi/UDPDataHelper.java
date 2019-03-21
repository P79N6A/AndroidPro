package com.gwm.haware.wifi;

import android.content.Context;

import com.gwm.haware.base.ISerialPort;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPDataHelper implements ISerialPort {
    private DatagramSocket socket;
    private OnReadDataListener listener;

    private UDPDataHelper(Context context){
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void readData() {
        while (true) {
            try {
                int size = socket.getReceiveBufferSize();
                DatagramPacket packet = new DatagramPacket(new byte[size], size);
                socket.receive(packet);
                if (listener != null){
                    listener.receiveData(packet.getData());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendData(byte[] bytes) {
        try {
            DatagramPacket packet = new DatagramPacket(bytes,0,bytes.length,InetAddress.getByName("192.168.1.2"),6666);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseSerialPortChannel() {
        socket.close();
    }

    @Override
    public void setOnReadDataListener(OnReadDataListener listener) {
        this.listener = listener;
    }
}
