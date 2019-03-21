package com.ogmamllpadnew.bean;

/**
 * Created by root on 18-11-20.
 */

public class PlanCheckBean {
    private String name;
    private String id;
    private boolean isCheck;

    public PlanCheckBean(String name, String id, boolean isCheck) {
        this.name = name;
        this.id = id;
        this.isCheck = isCheck;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
