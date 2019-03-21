package com.gwm.android;

import android.content.Context;
/**
 * 去掉重叠的Toast
 * @author gwm
 */
public class Toast {
	public static final int LENGTH_SHORT = android.widget.Toast.LENGTH_SHORT;
	public static final int LENGTH_LONG = android.widget.Toast.LENGTH_LONG;
	private static android.widget.Toast toast;
	private Toast(android.widget.Toast makeText){
		toast = makeText;
	}
	public static Toast makeText(Context context,String text,int duration){
		if(toast == null){
			toast = android.widget.Toast.makeText(context, text, duration);
		}else{
			toast.setText(text);
		}
		return new Toast(toast);
	}
	public static Toast makeText(Context context,int resId,int duration){
		if(toast == null){
			toast = android.widget.Toast.makeText(context, resId, duration);
		}else{
			toast.setText(resId);
		}
		return new Toast(toast);
	}
	public void show(){
		toast.show();
	}
}
