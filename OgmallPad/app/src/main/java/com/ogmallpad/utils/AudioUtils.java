package com.ogmallpad.utils;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;

import com.junshan.pub.utils.LogUtils;

import java.io.File;
import java.io.IOException;

public class AudioUtils {
    //文件路径
    private String filePath;
    //文件夹路径
    private String folderPath;
    private long startTime;

    private MediaRecorder mMediaRecorder;
    private final String TAG = "fan";
    public static final int MAX_LENGTH = 1000 * 60 * 10;// 最大录音时长1000*60*10;
    private OnStatusListener listener;
    private long currentTime;

    /**
     * 文件存储默认sdcard/record
     */
    public AudioUtils() {
        folderPath = Environment.getExternalStorageDirectory() + "/record/";
        File path = new File(folderPath);
        if (!path.exists())
            path.mkdirs();//创建文件夹
    }

    /**
     * 开始录音 使用amr格式
     * 录音文件
     *
     * @return
     */
    public void startRecord() {
        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        try {

            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            filePath = folderPath + System.currentTimeMillis() + ".aac";
            /* ③准备 */
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.setMaxDuration(MAX_LENGTH);
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            // AudioRecord audioRecord.
            /* 获取开始时间* */
            startTime = System.currentTimeMillis();
            updateMicStatus();
            LogUtils.e("fan" + "startTime" + startTime);
        } catch (IllegalStateException | IOException e) {
            LogUtils.e(TAG + "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    /**
     * 停止录音
     */
    public long stopRecord() {
        if (mMediaRecorder == null)
            return 0L;
        //有一些网友反应在5.0以上在调用stop的时候会报错，翻阅了一下谷歌文档发现上面确实写的有可能会报错的情况，捕获异常清理一下就行了，感谢大家反馈！
        try {
            stop();
            listener.onStop(filePath, currentTime - startTime);//录音结束回调
            filePath = "";
        } catch (RuntimeException e) {
            onRuntimeException();
            if (deleteDirectory(filePath)) {
                filePath = "";
            }
        }
        return currentTime - startTime;
    }

    /**
     * 取消录音
     */
    public void cancelRecord() {
        try {
            stop();
        } catch (RuntimeException e) {
            onRuntimeException();
        }
        if (deleteDirectory(filePath)) {
            filePath = "";
        }

    }

    //录音停止
    private void stop() {
        currentTime = System.currentTimeMillis();
        if (mMediaRecorder == null)
            return;
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    /**
     * 异常处理
     */
    private void onRuntimeException() {
        if (mMediaRecorder == null)
            return;
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    /**
     * 更新麦克状态
     */
    private int BASE = 1;
    private int SPACE = 100;// 间隔取样时间

    private void updateMicStatus() {
        if (mMediaRecorder != null) {
            double ratio = (double) mMediaRecorder.getMaxAmplitude() / BASE;
            LogUtils.d("getMaxAmplitude:" + mMediaRecorder.getMaxAmplitude());
            LogUtils.d("ratio:" + ratio);
            double db = 0;// 分贝
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
                if (null != listener) {
                    currentTime = System.currentTimeMillis();
                    listener.onUpdate(ratio, currentTime - startTime);
                }
            }
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }

    private final Handler mHandler = new Handler();
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    public void setOnStatusListener(OnStatusListener audioStatusUpdateListener) {
        this.listener = audioStatusUpdateListener;
    }

    /**
     * 录音回调
     */
    public interface OnStatusListener {
        /**
         * 录音中...
         *
         * @param db   当前声音分贝
         * @param time 录音时长
         */
        void onUpdate(double db, long time);

        /**
         * 停止录音
         *
         * @param filePath 保存路径
         * @param time     录音时长
         */
        void onStop(String filePath, long time);
    }

    //删除文件/文件夹
    public boolean deleteDirectory(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(path);
            } else {
                // 为目录时调用删除目录方法
                return deleteFolder(path);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param filePath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteFolder(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }

    /**
     * 语音播放功能
     */
    private MediaPlayer mediaPlayer;

    /**
     * 初始化播放
     *
     * @return
     */
    public MediaPlayer getPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private boolean isComplete = false;

    public void openPlayer(String openPath) {
        isComplete = false;
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (isComplete) {
                        playerListener.playerCompletion();
                    } else {
                        isComplete = true;
                    }
                }
            });
        }
        stopPlayer();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            try {
                playerListener.startPlayer();
                mediaPlayer.setDataSource(openPath);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IllegalArgumentException | IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                playerListener.onException();
                e.printStackTrace();
            }
        } else {
            playerListener.stopPlayer();
        }
    }

    public void stopPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.stop();
                playerListener.stopPlayer();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                playerListener.onException();
                e.printStackTrace();
            }
        } else {
            playerListener.stopPlayer();
        }
    }

    /**
     * 语音播放回调
     */
    public interface OnPlayerListener {
        void startPlayer();//开始播放

        void stopPlayer();//停止播放

        void playerCompletion();//播放完成

        void onException();//播放异常
    }

    private OnPlayerListener playerListener;

    public void setPlayerListener(OnPlayerListener playerListener) {
        this.playerListener = playerListener;
    }
}
