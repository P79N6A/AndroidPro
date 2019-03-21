package com.gwm.android;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.gwm.annotation.Contact;

import java.lang.reflect.Method;

public class ViewBindUtils {

    public static void bindActivity(Object object){
        try {
            Class clazz = Class.forName(Contact.PACKAGENAME +"."+object.getClass().getSimpleName()+"_ViewBind");
            Method bind = clazz.getMethod("bind",Activity.class);
            bind.invoke(null,object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static View bindFragment(Object object){
        try {
            Class clazz = Class.forName(Contact.PACKAGENAME +"."+object.getClass().getSimpleName()+"_ViewBind");
            Method bind = clazz.getMethod("bind",Fragment.class);
            return (View) bind.invoke(null,object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
