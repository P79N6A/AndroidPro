package com.gwm.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Administrator on 2018/4/15 0015.
 */

public class ProgressDialogUtil {
    protected static ProgressDialog progressDialog = null;
    /**
     * 显示加载 ProgressDialog
     * @param context
     * @param message
     */
    public static void showProgressDialog(Context context, String message) {
        if(context==null)return;
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context,
                    ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(message);
        }
        progressDialog.setMessage(message);
		/*if (MLStrUtil.isEmpty(message))
			progressDialog.setMessage(MLToolUtil
					.getResourceString(R.string.loading_message));
		else
			progressDialog.setMessage(message);
*/

        try {
            progressDialog.show();
        } catch (Exception e) {
        }

    }

    /**
     * dismiss ProgressDialog
     */
    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
