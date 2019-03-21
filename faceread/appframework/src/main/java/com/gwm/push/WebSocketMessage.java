package com.gwm.push;

import android.os.Parcel;
import android.os.Parcelable;

public class WebSocketMessage implements Parcelable{
    public String type;
    public String content;

    protected WebSocketMessage(Parcel in) {
        type = in.readString();
        content = in.readString();
    }

    public static final Creator<WebSocketMessage> CREATOR = new Creator<WebSocketMessage>() {
        @Override
        public WebSocketMessage createFromParcel(Parcel in) {
            return new WebSocketMessage(in);
        }

        @Override
        public WebSocketMessage[] newArray(int size) {
            return new WebSocketMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(content);
    }
}
