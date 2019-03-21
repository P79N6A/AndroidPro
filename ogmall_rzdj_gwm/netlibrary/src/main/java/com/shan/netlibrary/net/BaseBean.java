package com.shan.netlibrary.net;

import java.io.Serializable;

/**
 * Created by 陈俊山 on 2016/8/28.
 */

public class BaseBean implements Serializable {
    private static final long serialVersionUID = -7620435178023928252L;

    /**
     * code : 0
     * data : {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbl9pbmZvIjoie1wiY29udGFjdFBob25lXCI6XCIxMzgwMDEzODExM1wiLFwiaWRcIjo2NyxcInR5cGVcIjozMCxcInR5cGVBcmVhQWdlbnRJZFwiOjU5LFwidHlwZUZhY3RvcnlJZFwiOjU2fSIsImlhdCI6MTU0MjExMjk0MCwibWFyayI6IjY5YjAyZWU1LTNhM2EtNGZiMC04YTU5LWZhODVmNWFmNjE3OCJ9.3rTr8OqzXpqcZhoeZDmusNb8c1X0urWvtRmvPND3bPE"}
     * message : 登录成功
     */

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
