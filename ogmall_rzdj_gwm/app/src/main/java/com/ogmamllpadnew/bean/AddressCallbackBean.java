package com.ogmamllpadnew.bean;

/**
 * Created by root on 18-11-16.
 */

public class AddressCallbackBean {
    private int type;
    private String id;
    private String name;

    public AddressCallbackBean(int type, String id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
