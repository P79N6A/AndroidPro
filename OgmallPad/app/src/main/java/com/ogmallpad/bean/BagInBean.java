package com.ogmallpad.bean;

/**
 * Created by root on 18-8-21.
 */

public class BagInBean {
    private boolean isShow;

    public BagInBean(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
