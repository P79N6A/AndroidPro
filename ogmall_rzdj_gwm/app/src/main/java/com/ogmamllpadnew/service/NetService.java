package com.ogmamllpadnew.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.junshan.pub.bean.MessageEvent;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.bean.Voice;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.dao.VoiceUtils;
import com.ogmamllpadnew.utils.AudioUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 网络服务
 * Created by chenjunshan on 18-5-8.
 */

public class NetService extends Service implements AudioUtils.OnStatusListener, AudioUtils.OnPlayerListener {
    private AudioUtils audioUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        audioUtils = new AudioUtils();
        audioUtils.setOnStatusListener(this);
        audioUtils.setPlayerListener(this);
        //注册事件
        EventBus.getDefault().register(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消事件注册
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgEvent(MessageEvent msgEvent) {
        if (msgEvent.getType() == MsgConstant.STARTRECORD) {
            //取消录音
            audioUtils.cancelRecord();
            //开始录音
            audioUtils.startRecord();
        } else if (msgEvent.getType() == MsgConstant.STOPRECORD) {
            if (audioUtils == null)
                return;
            audioUtils.stopRecord();
        } else if (msgEvent.getType() == MsgConstant.STARTPLAY) {
            String path = msgEvent.getMsg();
            audioUtils.openPlayer(path);
        } else if (msgEvent.getType() == MsgConstant.STOPPLAY) {
            audioUtils.stopPlayer();
        }
    }

    @Override
    public void onUpdate(double db, long time) {

    }

    @Override
    public void onStop(String filePath, long timeSize) {
        Voice voice = new Voice();
        voice.setPath(filePath);
        voice.setTimeSize(timeSize);
        voice.setTime(MyApp.getInstance().getCreateTime());
        if (MyApp.getInstance().getCurrentUser() == null){
            voice.setCustomerId(0l);
        }else {
            voice.setCustomerId(Long.valueOf(MyApp.getInstance().getCurrentUser().getId()));
        }

        VoiceUtils.insert(voice);
    }

    @Override
    public void startPlayer() {

    }

    @Override
    public void stopPlayer() {

    }

    @Override
    public void onException() {

    }

    @Override
    public void playerCompletion() {
        EventBus.getDefault().post(new MessageEvent(MsgConstant.PLAYCOMPLETE));
    }
}
