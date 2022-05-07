package com.example.flink.common;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 20:37
 * Description:
 */
public class Matrix {
    /**
     * 分为两层ArrayList
     * 外面代表行
     * 里面代表列
     * */
    public ArrayList<ArrayList<String>> data;
    public Matrix() {
        data = new ArrayList<>();

    }

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    public void setData(ArrayList<ArrayList<String>> data) {
        this.data = data;
    }
}
