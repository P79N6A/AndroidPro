package com.ogmamllpadnew.bean;

/**
 * Created by root on 18-11-30.
 */

public class PlanleftBean {
    private int type;
    private int id;

    public PlanleftBean(int type, int id) {
        this.type = type;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
