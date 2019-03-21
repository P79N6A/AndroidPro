package com.ogmamllpadnew.bean;

/**
 * Created by root on 18-12-14.
 */

public class MineBean {
    private String name;
    private String subName;
    private String count;
    private int image;

    public MineBean(String name, String subName, String count, int image) {
        this.name = name;
        this.subName = subName;
        this.count = count;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
