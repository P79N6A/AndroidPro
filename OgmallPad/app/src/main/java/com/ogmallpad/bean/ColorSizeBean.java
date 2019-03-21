package com.ogmallpad.bean;

/**
 * Created by root on 18-8-23.
 */

public class ColorSizeBean {
    private String name;
    private int status;//0可选择，1选中，2不能选择

    public ColorSizeBean(String name, int status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
