package com.example.flink.common;

import com.example.flink.withoutFlink.CreateDataSet;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.flink.common.Constant.batchDataSet;
import static com.example.flink.common.Constant.streamDataSet;
import static java.util.Collections.shuffle;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/16
 * Time: 15:21
 * Description:对数据集做欠采样
 */
public class UnderSampling {


    public static void main(String[] args) throws IOException {

        File file = new File(streamDataSet);
        int allCount=0;
        int yesCount = 0;
        int noCount = 0;
        ArrayList<ArrayList<String>> allArr = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                String[] strArr = tempString.split(",");

                if (strArr[0].equals("Yes")){
                    ArrayList<String> yes = new ArrayList<>(Arrays.asList(strArr));
                    yesCount++;
                    allArr.add(yes);
                } else if(strArr[0].equals("No")) {
                    if (noCount >10306){
                        continue;
                    }else {
                        ArrayList<String> no = new ArrayList<>(Arrays.asList(strArr));
                        allArr.add(no);
                    }
                    noCount++;
                }
                allCount++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        System.out.println("yesCount: "+yesCount);
        System.out.println("noCount: "+noCount);
        System.out.println("allCount: "+allCount);
        System.out.println("allArrSize: "+allArr.size());

        shuffle(allArr);


writeFile01(allArr);




    }

    public static void writeFile01(ArrayList<ArrayList<String>> allArr) throws IOException {
        FileWriter fw=new FileWriter(new File("D:\\Datasets\\paperAn\\underSampling\\heart-test.csv"));
        //写入中文字符时会出现乱码
        BufferedWriter  bw=new BufferedWriter(fw);
        for (int i = 0; i < allArr.size(); i++) {
            StringBuilder s= new StringBuilder();
            for (int j = 0; j < allArr.get(i).size(); j++) {
                s.append(allArr.get(i).get(j));
                if (j != allArr.get(i).size()-1){
                    s.append(',');
                }
            }
            bw.write(s+"\n");
        }
        System.out.println("ok");
        bw.close();
        fw.close();
    }

}
