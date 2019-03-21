package com.ogmamllpadnew.bean;

import com.shan.netlibrary.net.BaseBean;

/**
 * Created by root on 18-11-17.
 */

public class SelectBean extends BaseBean {
    private int type;
    private String name;
    private String id;

    public SelectBean(int type, String name, String id) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
