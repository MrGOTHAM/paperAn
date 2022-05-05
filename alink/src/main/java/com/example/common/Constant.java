package com.example.common;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/4
 * Time: 23:55
 * Description:
 */
public class Constant {

    public static final String SCHEMASTR = "HeartDisease string,BMI double,Smoking string,AlcoholDrinking string,Stroke string,PhysicalHealth double,MentalHealth double,DiffWalking string,Sex string,AgeCategory string,Race string,Diabetic string,PhysicalActivity string,GenHealth string,SleepTime double,Asthma string,KidneyDisease string,SkinCancer string";

    public static final String[] SELECTEDCOLNAMES = new String[]{"BMI","Smoking","AlcoholDrinking","Stroke","PhysicalHealth","MentalHealth","DiffWalking","Sex","AgeCategory","Race","Diabetic","PhysicalActivity","GenHealth","SleepTime","Asthma","KidneyDisease","SkinCancer"};

    public static final String[] CATEGORYCOLNAMES = new String[]{"Smoking","AlcoholDrinking","Stroke","DiffWalking","Sex","AgeCategory","Race","Diabetic","PhysicalActivity","GenHealth","Asthma","KidneyDisease","SkinCancer"};

    public static final String[] NUMERICALCOLNAMES = new String[]{"BMI","PhysicalHealth" ,"MentalHealth" ,"SleepTime"};

    public static final String LABELCOLNAME = "HeartDisease";

    public static final String BATCH_HEART_DATASET = "D:\\Datasets\\heart_2020_cleaned.csv";

    public static final String STREAM_HEART_DATASET = "D:\\Datasets\\heart_2020_cleaned.csv";

    public static final String MOCK_HEART_DATASET = "D:\\Datasets\\mock_heart_dataset.csv";


}
