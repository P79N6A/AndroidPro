package com.ogmamllpadnew.bean;

/**
 * Created by Administrator on 2019/3/5.
 */

public class ScomboSingle {
//    public int sum;
    public double price;
    private static ScomboSingle single = new ScomboSingle();
    private ScomboSingle(){

    }
    public static synchronized ScomboSingle getSingle(){
        return single;
    }
}
