package com.example.flink.withoutFlink;

import com.example.flink.common.Matrix;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 20:38
 * Description:
 */
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