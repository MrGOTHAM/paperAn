package com.example.flink.common;

import java.util.ArrayList;

import static com.example.flink.common.Constant.labelIndex;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/11
 * Time: 16:20
 * Description:
 *
 * HeartDisease,BMI,Smoking,AlcoholDrinking,Stroke,PhysicalHealth,MentalHealth,DiffWalking,Sex,AgeCategory,Race,Diabetic,PhysicalActivity,GenHealth,SleepTime,Asthma,KidneyDisease,SkinCancer
 * No,16.6,Yes,No,No,3.0,30.0,No,Female,55-59,White,Yes,Yes,Very good,5.0,Yes,No,Yes
 * */

public class Feature {

    public static ArrayList<String> getMatrixByChangeFeature(String[] temps){
        ArrayList<String> list = new ArrayList<>();
        String featureYes = "1";
        String featureNo = "0";
        // 特征工程相关 labelIndex是标签下标
        for(int i=0;i<temps.length;i++){
            if (i == labelIndex){
                continue;
            }
            // 肤色 white 和 others 分别替换为 1 和 0
            if (i == 10 ){
                if (temps[i].equals("White")){
                    temps[i] = featureYes;
                }else {
                    temps[i] = featureNo;
                }
                // Diabetic
            }else if (i==11){
//                "No, borderline diabetes"
                if (temps[i].equals("No, borderline diabetes")){
                    temps[i] = "1";
                }else if (temps[i].equals("No")){
                    temps[i] = "0";
                }else {
                    temps[i] = "1";
                }
                // AgeCategory
            }else if (i == 9){
                if (temps[i].equals("0-4")){
                    temps[i] = "1";
                }else if (temps[i].equals("5-9")){
                    temps[i] = "2";
                }else if (temps[i].equals("10-14")){
                    temps[i] = "3";
                }else if (temps[i].equals("15-19")){
                    temps[i] = "4";
                }else if (temps[i].equals("20-24")){
                    temps[i] = "5";
                }else if (temps[i].equals("25-29")){
                    temps[i] = "6";
                }else if (temps[i].equals("30-34")){
                    temps[i] = "7";
                }else if (temps[i].equals("35-39")){
                    temps[i] = "8";
                }else if (temps[i].equals("40-44")){
                    temps[i] = "9";
                }else if (temps[i].equals("45-49")){
                    temps[i] = "10";
                }else if (temps[i].equals("50-54")){
                    temps[i] = "11";
                }else if (temps[i].equals("55-59")){
                    temps[i] = "12";
                }else if (temps[i].equals("60-64")){
                    temps[i] = "13";
                }else if (temps[i].equals("65-69")){
                    temps[i] = "14";
                }else if (temps[i].equals("70-74")){
                    temps[i] = "15";
                }else if (temps[i].equals("75-79")){
                    temps[i] = "16";
                }else {
                    temps[i] = "17";
                }
                // GenHealth
            }else if(i == 13){
                if (temps[i].equals("Poor")){
                    temps[i] = "1";
                }else if (temps[i].equals("Fair")){
                    temps[i] = "2";
                }else if (temps[i].equals("Good")){
                    temps[i] = "3";
                }else if (temps[i].equals("Very good")){
                    temps[i] = "4";
                }else {
                    temps[i] = "5";
                }
            }else if (i == 8){
                // male female 分别替换为 1 和 0
                if (temps[i].equals("Female")){
                    temps[i] = featureNo;
                }else if (temps[i].equals("Male")){
                    temps[i] = featureYes;
                }
                // yes 和 no 分别替换为1 和 0
            }else if (temps[i].equals("No")){
                temps[i] = featureNo;
            }else if (temps[i].equals("Yes")){
                temps[i] = featureYes;
            }
            list.add(temps[i]);
        }
        return list;
    }
}
