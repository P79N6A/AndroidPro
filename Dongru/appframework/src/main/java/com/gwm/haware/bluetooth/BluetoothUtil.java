package com.gwm.haware.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BluetoothUtil {
    private BluetoothAdapter mBluetoothAdapter;
    public BluetoothUtil(Context context){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * 检查当前蓝牙设备是否开启或可用
     * @return
     */
    public boolean isEnabled(){
        if (mBluetoothAdapter != null){
            return mBluetoothAdapter.isEnabled();
        }
        return false;
    }

    /**
     * 跳转到蓝牙设置界面
     * @param activity
     * @param requestCode
     */
    public void launchBluetoothSetting(Activity activity,int requestCode){
        if (!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, requestCode);
        }
    }

    /**
     * 搜索蓝牙设备
     * @param context
     * @param listener
     */
    public void search(Context context,final SearchDevicesListener listener){
        BroadcastReceiver mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // 已经配对的则跳过
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        listener.onBluetoothDevice(device);
                    }
                }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {  //搜索结束
                    listener.onSearchEnd();
                    context.unregisterReceiver(this);
                }
            }
        };
        //注册，当一个设备被发现时调用onReceive
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter);
    }
    interface SearchDevicesListener{
        void onBluetoothDevice(BluetoothDevice device);
        void onSearchEnd();
    }
}
