package com.example.flink.withFlink;

import com.example.flink.common.Matrix;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 20:48
 * Description: 数据集类：包含基本数据LRinfo和标签值
 *   主要保存特征信息以及标签值
 *   labels：主要保存标签值
 */

/***

 * */
public class CreateDataSet extends Matrix {
    public ArrayList<String> labels;

    public CreateDataSet() {
        super();
        labels = new ArrayList<String>();
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }
}