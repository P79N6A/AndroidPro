package com.gwm.messagesendreceive;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class HermsMessage implements Parcelable{
    private String action;
    private Bundle bundle;

    public HermsMessage(String action) {
        this.action = action;
    }

    protected HermsMessage(Parcel in) {
        action = in.readString();
        bundle = in.readBundle();

    }

    public static final Creator<HermsMessage> CREATOR = new Creator<HermsMessage>() {
        @Override
        public HermsMessage createFromParcel(Parcel in) {
            return new HermsMessage(in);
        }

        @Override
        public HermsMessage[] newArray(int size) {
            return new HermsMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(action);
        dest.writeBundle(bundle);
    }

    public String getAction() {
        return action;
    }

    public Bundle getBundle() {
        return bundle;
    }
}
