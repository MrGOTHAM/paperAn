package com.example.flink.withFlink.function;

import com.example.flink.common.Model;
import com.example.flink.withoutFlink.CreateDataSet;

import java.util.ArrayList;

import static com.example.flink.common.Constant.streamDataSet;
import static com.example.flink.withoutFlink.LR.classifyVector;
import static com.example.flink.withoutFlink.LR.readFileWithoutPlus1;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/12
 * Time: 15:59
 * Description:
 */
public class BatchPredict {

    public static ArrayList<String> predict(ArrayList<Double> weights,  CreateDataSet testData){
        ArrayList<String> predicts = new ArrayList<>();
        for (int i = 0; i < testData.data.size(); i++) {
//            if (!classifyVector(testData.data.get(i), weights).equals(testData.labels.get(i))) {
//                errorCount++;
//            }
            String s = classifyVector(testData.data.get(i), weights);
            predicts.add(s);
        }
        return predicts;
    }
}
