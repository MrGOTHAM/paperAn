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

public class FeatureAfterRF {

    public static ArrayList<String> getMatrixByChangeFeature(String value){
        String[] temps;
        String newValue;
        if (value.contains("No, borderline diabetes")) {
            newValue = value.replace("No, borderline diabetes", "Borderline diabetes");
            temps = newValue.split(",");
        } else {
            temps = value.split(",");
        }

        ArrayList<String> list = new ArrayList<>();
        String featureYes = "1";
        String featureNo = "0";
        // 特征工程相关 labelIndex是标签下标
        for(int i=0;i<temps.length;i++){
            if (i == labelIndex){
                continue;
            }
            if (i == 4){
                if (temps[i].equals("No")){
                    temps[i] = "0";
                }else {
                    temps[i] = "1";
                }
                list.add(temps[i]);
            }
            else if (i == 9){
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
                list.add(temps[i]);
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
                list.add(temps[i]);
            }

        }
        return list;
    }
}
