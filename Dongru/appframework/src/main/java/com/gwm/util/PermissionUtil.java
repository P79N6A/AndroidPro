package com.gwm.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    private static List<String> missingPermission = new ArrayList<>();
    private static final int REQUEST_PERMISSION_CODE = 12345;

    private static PermissionUtil utilInstance = new PermissionUtil();
    private RequestPermissionCallback callback;

    public void setCallback(RequestPermissionCallback callback) {
        this.callback = callback;
    }

    private PermissionUtil(){
    }
    public static PermissionUtil getInstance(){
        return utilInstance;
    }
    public void checkAndRequestPermissions(Activity activity,String... permissions) {
        // Check for permissions
        for (String eachPermission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, eachPermission) != PackageManager.PERMISSION_GRANTED) {
                missingPermission.add(eachPermission);
            }
        }
        // Request for missing permissions
        if (!missingPermission.isEmpty() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity,
                    missingPermission.toArray(new String[missingPermission.size()]),
                    REQUEST_PERMISSION_CODE);
        }

    }


    /**
     * 此方法在Activity的onRequestPermissionsResult方法中调用
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Check for granted permission and remove from missing list
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = grantResults.length - 1; i >= 0; i--) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    missingPermission.remove(permissions[i]);
                }
            }
        }
        // If there is enough permission, we will start the registration
        if (missingPermission.isEmpty() && callback != null) {
            //所有权限都允许
            callback.onGranted();
        } else if (callback != null){
            //有权限拒绝授权
            Object[] permission = (Object[]) missingPermission.toArray();
            callback.onDenied(permission);
        }
    }
    public interface RequestPermissionCallback{
        void onGranted();
        void onDenied(Object... permission);
    }
}
