package com.ogmallpad.bean;

/**
 * Created by root on 18-8-24.
 */

public class ShopCartBean {
    private int productId;
    private int specificationId;
    private int count;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(int specificationId) {
        this.specificationId = specificationId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
