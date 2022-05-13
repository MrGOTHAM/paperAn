package com.example.flink.withFlink.evaluate;

import com.example.flink.withoutFlink.CreateDataSet;

import java.util.ArrayList;

import static com.example.flink.withoutFlink.LR.classifyVector;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/12
 * Time: 15:29
 * Description:
 */
public class Evaluator {
    // 准确度
    public void accuracy(ArrayList<String> predicts, ArrayList<String> labels) {
        int errorCount = 0;
        if (predicts.size() == labels.size()) {
            for (int i = 0; i < predicts.size(); i++) {
                errorCount += predicts.get(i).equals(labels.get(i)) ? 0 : 1;
            }
        }


        System.out.println("准确率accuracy：" + (labels.size() - errorCount) * 1.0 / labels.size());
        ;
    }

    // 真正为1的里面 被预测为1的比例             召回率
    public void recall(ArrayList<String> predicts, ArrayList<String> labels) {
        int realTrue = 0;
        int predictAndRealTrue = 0;
        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).equals("1")) {
                realTrue++;
                if (predicts.get(i).equals("1")) {
                    predictAndRealTrue++;
                }
            }
        }
        System.out.println("召回率recall：" + (predictAndRealTrue) * 1.0 / realTrue);
    }

    // 预测为1的里面 真正为1的比例          精确度
    public void precision(ArrayList<String> predicts, ArrayList<String> labels) {
        int predictTrue = 0;
        int realTrue = 0;
        for (int i = 0; i < labels.size(); i++) {

            if (predicts.get(i).equals("1")) {
                predictTrue++;
                if (labels.get(i).equals("1")) {
                    realTrue++;
                }
            }
        }
        System.out.println("精确度precision：" + (realTrue) * 1.0 / predictTrue);
    }

    public void f1Score(ArrayList<String> predicts, ArrayList<String> labels) {
        int predictTrue = 0;
        int realTrue = 0;
        int predictTrueAndRealTrue = 0;

        for (int i = 0; i < predicts.size(); i++) {
            if (predicts.get(i).equals("1")) {
                predictTrue++;
            }

            if (labels.get(i).equals("1")) {
                realTrue++;
            }

            if (labels.get(i).equals("1") && predicts.get(i).equals("1")) {
                predictTrueAndRealTrue++;
            }
        }
        System.out.println("F1-Score稳健度为：" + predictTrueAndRealTrue * 2.0 / (predictTrue + realTrue));
    }
    /*
     * 正负样本比例
     */
    public void falseAndTrue(ArrayList<String> labels){
        int trueF = 0;
        int falseF = 0;
        for (String s:labels) {
            if (s.equals("1")){
                trueF++;
            }else {
                falseF++;
            }
        }
        System.out.println("负样本比正样本："+falseF/trueF);
    }
}
