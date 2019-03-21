package com.ogmamllpadnew.bean;

import java.util.List;

/**
 * Created by root on 18-8-24.
 */

public class ShareBean {
    /**
     * name : 客厅
     * products : [{"spec_id":4448,"count":1},{"spec_id":4446,"count":1},{"spec_id":2672,"count":1},{"spec_id":26192,"count":1},{"spec_id":2662,"count":2},{"spec_id":2827,"count":1}]
     */

    private String name;
    private List<ProductsBean> products;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductsBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsBean> products) {
        this.products = products;
    }

    public static class ProductsBean {
        /**
         * spec_id : 4448
         * count : 1
         */

        private int spec_id;
        private int count;

        public int getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(int spec_id) {
            this.spec_id = spec_id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
