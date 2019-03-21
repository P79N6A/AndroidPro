package com.ogmamllpadnew.bean;

/**
 * Created by root on 18-12-11.
 */

public class SupplementBean {
    private String name;
    private String value;

    public SupplementBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
