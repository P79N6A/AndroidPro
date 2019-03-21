package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 拎包入住模块获取产品分类数据【品牌家具，软装生活】
 * Created by chenjunshan on 2018-08-22.
 */

public class BagcategorysBean extends BaseBean implements Cloneable{
    private List<DataBean> data;

    public List<DataBean> getData() {
        if (data == null)
            data = new ArrayList<>();
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Cloneable{

        private int id;
        private String name;
        private int parentCategoryId;
        private Object image;
        private Object level;
        private List<ChildCaterotyBean> childCateroty;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentCategoryId() {
            return parentCategoryId;
        }

        public void setParentCategoryId(int parentCategoryId) {
            this.parentCategoryId = parentCategoryId;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }

        public List<ChildCaterotyBean> getChildCateroty() {
            if (childCateroty == null)
                childCateroty = new ArrayList<>();
            return childCateroty;
        }

        public void setChildCateroty(List<ChildCaterotyBean> childCateroty) {
            this.childCateroty = childCateroty;
        }

        public static class ChildCaterotyBean implements Cloneable{
            /**
             * id : 383
             * name : 床
             * status : null
             * code : null
             * parentCategoryId : 382
             * image : null
             * level : null
             * childCateroty : []
             */

            private int id;
            private String name;
            private int parentCategoryId;
            private Object image;
            private Object level;
            private List<?> childCateroty;
            private boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getParentCategoryId() {
                return parentCategoryId;
            }

            public void setParentCategoryId(int parentCategoryId) {
                this.parentCategoryId = parentCategoryId;
            }

            public Object getImage() {
                return image;
            }

            public void setImage(Object image) {
                this.image = image;
            }

            public Object getLevel() {
                return level;
            }

            public void setLevel(Object level) {
                this.level = level;
            }

            public List<?> getChildCateroty() {
                return childCateroty;
            }

            public void setChildCateroty(List<?> childCateroty) {
                this.childCateroty = childCateroty;
            }

            public Object clone() {
                ChildCaterotyBean cloned = null;
                try {
                    cloned = (ChildCaterotyBean) super.clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                return cloned;
            }
        }

        public Object clone() {
            DataBean cloned = null;
            try {
                cloned = (DataBean) super.clone();
                cloned.childCateroty = new ArrayList<>();
                for (int i = 0; i < childCateroty.size(); i++) {
                    cloned.childCateroty.add((DataBean.ChildCaterotyBean) childCateroty.get(i).clone());
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return cloned;
        }
    }

    public Object clone() {
        BagcategorysBean cloned = null;
        try {
            cloned = (BagcategorysBean) super.clone();
            cloned.data = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                cloned.data.add((BagcategorysBean.DataBean) data.get(i).clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloned;
    }
}