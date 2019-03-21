package com.ogmall.faceread.activity;

import android.Manifest;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.baidu.aip.ImageFrame;
import com.baidu.aip.face.CameraImageSource;
import com.baidu.aip.face.DetectRegionProcessor;
import com.baidu.aip.face.FaceDetectManager;
import com.baidu.aip.face.FaceFilter;
import com.baidu.aip.face.PreviewView;
import com.baidu.aip.face.camera.ICameraControl;
import com.baidu.aip.face.camera.PermissionCallback;
import com.baidu.idl.facesdk.FaceInfo;
import com.gwm.android.Handler;
import com.gwm.annotation.Layout;
import com.gwm.util.MyLogger;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.sunflower.FlowerCollector;
import com.ogmall.faceread.bean.BDHTBean;
import com.ogmall.faceread.bean.FaceImgBean;
import com.ogmall.faceread.bean.HtBean;
import com.ogmall.faceread.contact.CacheContact;
import com.ogmall.faceread.datapresenter.AliDataPresenter;
import com.ogmall.faceread.datapresenter.FileDBDataPresenter;
import com.ogmall.faceread.widget.BrightnessTools;
import com.ogmall.video.constants.PlayParameter;
import com.ogmall.video.widget.AliyunVodPlayerView;
import com.ogmalllarge.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * 实时检测人脸框并把检测到得人脸图片绘制在屏幕上，每10帧截图一张。
 * Intent intent = new Intent(MainActivity.this, DetectActivity.class);
 * startActivity(intent);
 */
@Layout(R.layout.activity_detect2)
public class DetectActivity extends com.gwm.base.BaseActivity {
    private static final int MSG_INITVIEW = 1001;
    private static final int UPDATE_DATA = 1006;
    private static final int SSFG_ANIM = 1988;
    @BindView(R.id.preview_view)
    PreviewView previewView;
    private boolean mDetectStoped = false;

    private FaceDetectManager faceDetectManager;
    private DetectRegionProcessor cropProcessor = new DetectRegionProcessor();
    private int mScreenW;
    private int mScreenH;
    @BindView(R.id.texture_view)
    TextureView mTextureView;
    private Paint paint = new Paint();
    private List<Bitmap> mList = new ArrayList<>();
    private int mRound = 2;
    private String macId;//大屏id
    private String gateid;//闸门id
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.tv_country)
    TextView tv_country;
    @BindView(R.id.tv_name)
    TextView tv_name;
    private boolean isHtjc;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.frame_bg)
    FrameLayout frame_bg;
    @BindView(R.id.video_view)
    AliyunVodPlayerView video_view;

//    AliyunVodPlayer aliyunVodPlayer;
    String path;


    private SpeechSynthesizer mTts;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 默认发音人
    private String voicer = "xiaoyan";


    @Override
    public void onCreate(FragmentManager manager,Bundle savedInstanceState) {
        macId = getCache().getAsString(CacheContact.BIGMIRRORID);
        gateid = getCache().getAsString(CacheContact.GATEID);
        hideNavigationBar();
        faceDetectManager = new FaceDetectManager(this);
        initScreen();
        initView();
        mTts = SpeechSynthesizer.createSynthesizer(this,mTtsInitListener);
//        aliyunVodPlayer = new AliyunVodPlayer(this);
        Handler.getHandler().sendEmptyMessageDelayed(MSG_INITVIEW, 500,this);
        Handler.getHandler().sendEmptyMessage(UPDATE_DATA,this);
        Handler.getHandler().sendEmptyMessage(SSFG_ANIM,this);
//        video_view.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion() {
//                video_view.onStop();
//                video_view.setVisibility(View.GONE);
//            }
//        });
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            MyLogger.kLog().e("keda code="+code);
            if (code == ErrorCode.SUCCESS){
                showToast("语音模块初始化成功");
                startVoice("欢迎光临");
            }else{
                showToast("语音模块初始化失败");
            }
        }
    };

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case UPDATE_DATA:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minuns = calendar.get(Calendar.MINUTE);
                tv_date.setText(String.format("%d-%d-%d %d/%d",year,month+1,day,hour,minuns));
                Handler.getHandler().sendEmptyMessageDelayed(UPDATE_DATA,1000*60,this);
                break;
            case SSFG_ANIM:
                startFlick(frame_bg);
                Handler.getHandler().sendEmptyMessageDelayed(SSFG_ANIM,1000,this);
                break;
            case MSG_INITVIEW:
                start();
                break;
        }
    }

    /**
     * 获取屏幕参数
     */
    private void initScreen() {
        WindowManager manager = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenW = outMetrics.widthPixels;
        mScreenH = outMetrics.heightPixels;
        mRound = getResources().getDimensionPixelSize(R.dimen.round);
    }

    /**
     * 闪闪发光动画
     * @param view
     */
    private void startFlick(View view) {
        if (null == view) {
            return;
        }
        Animation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (video_view != null){
            video_view.onDestroy();
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        mTextureView.setOpaque(false);

        // 不需要屏幕自动变黑。
        mTextureView.setKeepScreenOn(true);

        final CameraImageSource cameraImageSource = new CameraImageSource(this);
        cameraImageSource.setPreviewView(previewView);

        faceDetectManager.setImageSource(cameraImageSource);
        faceDetectManager.setOnFaceDetectListener(new FaceDetectManager.OnFaceDetectListener() {
            @Override
            public void onDetectFace(final int retCode, FaceInfo[] infos, ImageFrame frame) {

            }
        });
        faceDetectManager.setOnTrackListener(new FaceFilter.OnTrackListener() {
            @Override
            public void onTrack(final FaceFilter.TrackedModel trackedModel) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showFrame(trackedModel);
                    }
                });

            }
        });

        cameraImageSource.getCameraControl().setPermissionCallback(new PermissionCallback() {
            @Override
            public boolean onRequestPermission() {
                ActivityCompat.requestPermissions(DetectActivity.this,
                        new String[]{Manifest.permission.CAMERA}, 100);
                return true;
            }
        });


        ICameraControl control = cameraImageSource.getCameraControl();
        control.setPreviewView(previewView);
        // 设置检测裁剪处理器
        faceDetectManager.addPreProcessor(cropProcessor);

        int orientation = getResources().getConfiguration().orientation;
        boolean isPortrait = (orientation == Configuration.ORIENTATION_PORTRAIT);

        if (isPortrait) {
            previewView.setScaleType(PreviewView.ScaleType.FIT_WIDTH);
        } else {
            previewView.setScaleType(PreviewView.ScaleType.FIT_HEIGHT);
        }

        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        cameraImageSource.getCameraControl().setDisplayOrientation(rotation);
        setCameraType(cameraImageSource);
        initBrightness();
        initPaint();
    }

    /**
     * 设置相机亮度，不够200自动调整亮度到200
     */
    private void initBrightness() {
        int brightness = BrightnessTools.getScreenBrightness(DetectActivity.this);
        if (brightness < 200) {
            BrightnessTools.setBrightness(this, 200);
        }
    }

    /**
     * 启动人脸检测
     */
    private void start() {
        try {
            RectF newDetectedRect = new RectF(0, 0, mScreenW, mScreenH);
            cropProcessor.setDetectedRect(newDetectedRect);
            faceDetectManager.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        faceDetectManager.stop();
        mDetectStoped = true;
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            Bitmap bmp = mList.get(i);
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
            }
        }
        mList.clear();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDetectStoped) {
            faceDetectManager.start();
            mDetectStoped = false;
        }
    }

    @Override
    public void onRequestSuccessData(Object data) {
        if (data instanceof BDHTBean){   //百度活体
            isHtjc = false;
            if (((BDHTBean)data).face_liveness > 0.9){
                faceimages(path);
            }else {
                //TODO 活体检测不通过
            }
        }else if (data instanceof FaceImgBean){

            FaceImgBean bean = (FaceImgBean) data;
            if (bean.code == 200){
                tv_country.setText(bean.data.companyName);
                tv_name.setText(bean.data.name);
                isUploading = false;
//                HwitManager.HwitSetIOValue(5, isOpen);
                startVoice(bean.data.name+"欢迎光临");
//                if (!video_view.isPlaying())
//                    startVid(bean.data.videoId);
//                Handler.getHandler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        isUploading = false;
//                        HwitManager.HwitSetIOValue(5, 0);
//                    }
//                },8000);
            }else {
                tv_name.setText("查无此人");
                isUploading = false;
            }
        }else if (data instanceof HtBean){  //阿里活体
            isHtjc = false;
            if (((HtBean)data).Code == 0){
                faceimages(path);
            }
        }
    }

    @Override
    public void onError() {

    }
    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 绘制人脸框。
     *
     * @param model 追踪到的人脸
     */
    private void showFrame(final FaceFilter.TrackedModel model) {
        Canvas canvas = mTextureView.lockCanvas();
        if (canvas == null) {
            return;
        }
        // 清空canvas
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        if (model != null) {
            FaceInfo info = model.getInfo();
            model.getImageFrame().retain();
            RectF rectCenter = new RectF(info.mCenter_x - 2 - info.mWidth * 3 / 5,
                    info.mCenter_y - 2 - info.mWidth * 3 / 5,
                    info.mCenter_x + 2 + info.mWidth * 3 / 5,
                    info.mCenter_y + 2 + info.mWidth * 3 / 5);
            previewView.mapFromOriginalRect(rectCenter);
            // 绘制框
            //paint.setStrokeWidth(mRound);
            //paint.setAntiAlias(true);
            //canvas.drawRect(rectCenter, paint);

            if (model.meetCriteria()) {
                // 符合检测要求，绘制绿框
                //paint.setColor(Color.GREEN);
            }
            if (!isCompressing && !isUploading) {
                compressImage(model);
            }
        }
        mTextureView.unlockCanvasAndPost(canvas);
    }

    private boolean isCompressing = false;//是否正在压缩

    /**
     * 获取头像并压缩
     *
     * @param model
     */
    private void compressImage(FaceFilter.TrackedModel model) {
        try {
            isCompressing = true;
            Bitmap face = model.cropFace();
            //  final Bitmap face =ImageUtil.bitmapFromArgb(model.getImageFrame());
            if (face != null) {
                int size = mList.size();
                // 释放一些，以防止太多
                if (size >= 4) {
                    Bitmap bmp = mList.get(size - 4);
                    if (bmp != null) {
                        bmp.recycle();
                        bmp = null;
                    }
                }
                //压缩图片
                face = Bitmap.createScaledBitmap(face, 217, 200, true);
                path = saveImage(face);
                imageView.setImageBitmap(face);
                if (s.toString().length() > 500) {
                    s = new StringBuffer();
                }
                s.append("采集头像成功\n");
                tv.setText(s.toString());
                if (!isUploading) {
                    //TODO 添加活体检测
                    faceimages(path);

                }
                mList.add(face);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isCompressing = false;
        }
    }

    private String saveImage(Bitmap face) {
        try {
            String path = getApplicationContext().getCacheDir()+"/"+System.currentTimeMillis()+".png";
            File file = new File(path);
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(path);
            face.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.flush();
            fos.close();
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private StringBuffer s = new StringBuffer();
    private boolean isUploading = false;//是否正在上传
    private int isOpen = 1;//是否开门，1开门，0关门
    private int openTime = 1000;//开门时间间隔5秒
//    private Handler handler = new Handler();
//    private Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            isUploading = false;
//        }
//    };

    private long startTime;


    public void htjc(final String image, final Bitmap face){
        isHtjc = true;
        AliDataPresenter dataPresenter = new AliDataPresenter(this,this);
        dataPresenter.aliHt(image);
//        BaiduHTDataPresenter dataPresenter = new BaiduHTDataPresenter(this,this);
//        dataPresenter.ht(image);
    }

    /**
     * 上传图片
     *
     * @param path 文件路劲
     */
    public void faceimages(String path) {
        isUploading = true;
        FileDBDataPresenter dataPresenter = new FileDBDataPresenter(this,this);
        dataPresenter.getInfo(path);
    }

//    private Handler mHandler2 = new Handler();
//    private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            //第二个参数0代表关门
////            HwitManager.HwitSetIOValue(5, 0);
//        }
//    };

    private void setCameraType(CameraImageSource cameraImageSource) {
        // TODO 选择使用前置摄像头
//        cameraImageSource.getCameraControl().setCameraFacing(ICameraControl.CAMERA_FACING_FRONT);

        // TODO 选择使用usb摄像头
        cameraImageSource.getCameraControl().setCameraFacing(ICameraControl.CAMERA_USB);
        // 如果不设置，人脸框会镜像，显示不准
        previewView.getTextureView().setScaleX(-1);

        // TODO 选择使用后置摄像头
        //cameraImageSource.getCameraControl().setCameraFacing(ICameraControl.CAMERA_FACING_BACK);
        //previewView.getTextureView().setScaleX(-1);
    }


    public void startVoice(String content){
        FlowerCollector.onEvent(this, "tts_play");


        // 设置参数
        setParam();
        int code = mTts.startSpeaking(content, mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
			/*String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
			int code = mTts.synthesizeToUri(text, path, mTtsListener);*/


    }

    /**
     * 播放视频
     */
    public void startVid(String vid){
        video_view.onResume();
        video_view.setVisibility(View.VISIBLE);
        AliyunVidSts vidSts = new AliyunVidSts();
        vidSts.setVid(vid);
        vidSts.setAcId(PlayParameter.PLAY_PARAM_AK_ID);
        vidSts.setAkSceret(PlayParameter.PLAY_PARAM_AK_SECRE);
        vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
        vidSts.setTitle("视频");
        video_view.setAutoPlay(true);
        video_view.setVidSts(vidSts);
    }
    public void startVideo(String vid,String playAuth){

        AliyunPlayAuth.AliyunPlayAuthBuilder aliyunPlayAuthBuilder = new AliyunPlayAuth.AliyunPlayAuthBuilder();
        aliyunPlayAuthBuilder.setVid(vid);
        aliyunPlayAuthBuilder.setPlayAuth(playAuth);
        aliyunPlayAuthBuilder.setQuality(IAliyunVodPlayer.QualityValue.QUALITY_LOW);
        AliyunPlayAuth mPlayAuth = aliyunPlayAuthBuilder.build();
//        aliyunVodPlayer.prepareAsync(mPlayAuth);
    }
    /**
     * 参数设置
     * @return
     */
    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        }else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");

        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.pcm");
    }
    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {

        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {

        }

        @Override
        public void onCompleted(SpeechError error) {

        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {


        }
    };

}
