package com.shan.netlibrary.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈俊山 on 2016/8/28.
 */

public class BaseBean implements Serializable {
    private static final long serialVersionUID = -7620435178023928252L;

    private int code;
    private boolean status;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
