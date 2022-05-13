package com.example.flink.common;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/12
 * Time: 14:58
 * Description:
 */
public class Model {
    public static void saveModel(String filename, ArrayList<Double> W) throws IOException {
        File f = new File(filename);
        // 构建FileOutputStream对象
        FileOutputStream fip = new FileOutputStream(f);
        // 构建OutputStreamWriter对象
        OutputStreamWriter writer = new OutputStreamWriter(fip,"UTF-8");
        //计算模型矩阵的元素个数
        int n = W.size();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n-1; i ++) {
            sb.append(String.valueOf(W.get(i)));
            sb.append(",");
        }
        sb.append(String.valueOf(W.get(n-1)));
        String sb1 = sb.toString();
        writer.write(sb1);
        writer.close();
        fip.close();
    }

    public static ArrayList<Double> loadModel(String filename) throws IOException {
        File file = new File(filename);                         //定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);               //定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);    //new一个BufferedReader对象，将文件内容读取到缓存
        String s = bReader.readLine();
        bReader.close();
        String[] split = s.split(",");
        ArrayList<Double> weights = new ArrayList<>();
        for (String str:split) {
            weights.add(Double.valueOf(str));
        }
        return weights;
    }

    public static void saveModela(String filename, String s) throws IOException {
        File f = new File(filename);
        // 构建FileOutputStream对象
        FileOutputStream fip = new FileOutputStream(f);
        // 构建OutputStreamWriter对象
        OutputStreamWriter writer = new OutputStreamWriter(fip,"UTF-8");
        //计算模型矩阵的元素个
        writer.write(s);
        writer.close();
        fip.close();
    }
}