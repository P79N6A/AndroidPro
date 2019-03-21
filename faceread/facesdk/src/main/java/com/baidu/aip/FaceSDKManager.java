/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.aip;

import android.content.Context;

import com.baidu.idl.facesdk.FaceSDK;
import com.baidu.idl.facesdk.FaceTracker;

/**
 * FaceSDK功能接口
 */
public class FaceSDKManager {

    private static FaceSDKManager instance = null;
    private Context mContext;
    private FaceTracker mFaceTracker;
    private boolean mInitFlag = false;
    private FaceConfig mFaceConfig = new FaceConfig();

    private FaceSDKManager() {
    }

    public static FaceSDKManager getInstance() {
        if (instance == null) {
            synchronized (FaceSDKManager.class) {
                if (instance == null) {
                    instance = new FaceSDKManager();
                }
            }
        }
        return instance;
    }

    public void initialize(final Context context, String licenseID) {
        initialize(context, licenseID, "");
    }

    public void initialize(final Context context, String licenseID, String licenseFileName) {
        mContext = context;
        mFaceTracker = new FaceTracker(
                context,
                context.getAssets(),
                licenseID,
                licenseFileName,
                FaceSDK.AlignMethodType.CDNN,
                FaceSDK.ParsMethodType.NOT_USE);
        mFaceTracker.set_isFineAlign(false);
        mFaceTracker.set_isVerifyLive(true);
        mFaceTracker.set_DetectMethodType(1);
        mFaceTracker.set_isCheckQuality(FaceEnvironment.VALUE_IS_CHECK_QUALITY);
        mFaceTracker.set_notFace_thr(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        mFaceTracker.set_min_face_size(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        mFaceTracker.set_cropFaceSize(FaceEnvironment.VALUE_CROP_FACE_SIZE);
        mFaceTracker.set_illum_thr(FaceEnvironment.VALUE_BRIGHTNESS);
        mFaceTracker.set_blur_thr(FaceEnvironment.VALUE_BLURNESS);
        mFaceTracker.set_occlu_thr(FaceEnvironment.VALUE_OCCLUSION);
        mFaceTracker.set_max_reg_img_num(FaceEnvironment.VALUE_MAX_CROP_IMAGE_NUM);
        mFaceTracker.set_eulur_angle_thr(
                FaceEnvironment.VALUE_HEAD_PITCH,
                FaceEnvironment.VALUE_HEAD_YAW,
                FaceEnvironment.VALUE_HEAD_ROLL
        );
        mFaceTracker.set_track_by_detection_interval(800);
        FaceSDK.setPerfLogFlag(0);
        FaceSDK.setValueLogFlag(0);
        FaceSDK.setNumberOfThreads(FaceEnvironment.VALUE_DECODE_THREAD_NUM);
        mInitFlag = true;
    }

    public FaceTracker getFaceTracker() {
        return mFaceTracker;
    }

    public FaceConfig getFaceConfig() {
        return mFaceConfig;
    }

    public void setFaceConfig(FaceConfig config) {
        this.mFaceConfig = config;
        setSDKValue(mFaceConfig);
    }

    private void setSDKValue(FaceConfig options) {
        if (mFaceTracker != null && options != null) {
            mFaceTracker.set_isCheckQuality(options.isCheckFaceQuality);
            mFaceTracker.set_notFace_thr(options.notFaceValue);
            mFaceTracker.set_min_face_size(options.minFaceSize);
            mFaceTracker.set_cropFaceSize(options.cropFaceValue);
            mFaceTracker.set_illum_thr(options.brightnessValue);
            mFaceTracker.set_blur_thr(options.blurnessValue);
            mFaceTracker.set_occlu_thr(options.occlusionValue);
            mFaceTracker.set_isVerifyLive(options.isVerifyLive);
            mFaceTracker.set_eulur_angle_thr(
                    options.headPitchValue,
                    options.headYawValue,
                    options.headRollValue
            );
            FaceSDK.setNumberOfThreads(options.faceDecodeNumberOfThreads);
        }
    }


    public static boolean isLicenseSuccess() {
        return FaceSDK.isAuthoritySucceeded();
    }

    public static String getVersion() {
        return FaceEnvironment.SDK_VERSION;
    }

    // 释放资源
    public static void release() {
        synchronized (FaceSDKManager.class) {
            if (instance != null) {
                instance.mInitFlag = false;
                instance.mFaceTracker = null;
                instance.mContext = null;
                instance = null;
            }
        }
    }
}
