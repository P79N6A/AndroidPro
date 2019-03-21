package com.shan.netlibrary.bean;


import com.shan.netlibrary.net.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有的风格数据列表
 * Created by chenjunshan on 2018-07-05.
 */

public class StylesBean extends BaseBean {

    /**
     * data : [{"introduce":"欧式风格最大的特点是在造型上极其讲究，给人的感觉端庄典雅、高贵华丽，具有浓厚的文化气息。在软装选配上，一般采用宽大精美的家具，配以精致的雕刻，整体营造出一种华丽、高贵、温馨的感觉。","name":"欧式风格","id":45},{"introduce":"新中式是中国传统风格文化意义在当代的新演绎，以现代人的审美需求打造富有传统韵味的家居生活，让经典艺术在当下得到最合适的展现。","name":"新中式风格","id":46},{"introduce":"","name":"田园风格","id":47},{"introduce":null,"name":"地中海风格","id":48},{"introduce":"美式风格以享受生活为最高原则，有着独特的随性气质，非常符合时下的文化资产者对家居生活的需求：既要有文化感、贵气感，又兼具自在感与情调。","name":"美式风格","id":49},{"introduce":null,"name":"欧法式风格","id":50},{"introduce":"北欧风格的居家家具，浅淡的色彩、洁净的清爽感，让居家空间得以彻底降温。客厅空间的布置重点在于家具的选购与色彩以及布品的搭配，协调、对称的技巧，让每一个细节的铺排，都呈现出令人感觉舒适的气氛。","name":"北欧风格","id":51},{"introduce":"","name":"新古典风格","id":52},{"introduce":null,"name":"儿童家具","id":53},{"introduce":"现代风格重居室的布局与使用功能的完美结合，造型简洁，无过多装饰，工艺构造方面讲究科学合理，而且注重充分发挥材料的性能，给人以良好的使用体验。","name":"现代风格","id":54},{"introduce":null,"name":"青花瓷","id":55},{"introduce":"轻奢简美风格强调设计与材质，摒弃了美式仿古风格中的繁琐元素，保留着美式家具的宽大舒适感，同时与现代轻奢主义相融，奢华大气，而且品质感强烈。","name":"轻奢简美","id":56},{"introduce":"轻奢中式延续了传统文化的温润雅致，兼具现代主义手法的简洁轻快，气质独特，精致时尚，典雅高贵而又兼具内敛的文化气质，是越来越多人的选择。","name":"轻奢中式","id":57},{"introduce":null,"name":"东南亚风格","id":58},{"introduce":null,"name":"户外家具","id":59},{"introduce":null,"name":"工业风","id":60},{"introduce":"现代轻奢风格，即在轻奢华的基础上融入现代家具的元素而形成一种现代比较独特的家具风格，深受现代很多年轻人的欣赏与青睐。","name":"轻奢现代","id":61},{"introduce":"法式风格家具讲究突出轴线的对称，家具工艺注重精雕细琢，雕花、线条的点缀散发着浓郁的宫廷贵族气质和古典气息，高贵典雅。","name":"法式风格","id":62},{"introduce":null,"name":"办公家具","id":63},{"introduce":"","name":"软体类","id":64},{"introduce":null,"name":"其他","id":65}]
     * message : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        if (data == null)
            data = new ArrayList<>();
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * introduce : 欧式风格最大的特点是在造型上极其讲究，给人的感觉端庄典雅、高贵华丽，具有浓厚的文化气息。在软装选配上，一般采用宽大精美的家具，配以精致的雕刻，整体营造出一种华丽、高贵、温馨的感觉。
         * name : 欧式风格
         * id : 45
         */

        private String introduce;
        private String name;
        private int id;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}