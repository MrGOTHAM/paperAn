package com.example.flink.withFlink.function;

import com.example.flink.common.Feature;
import org.apache.flink.api.common.functions.AbstractRichFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;

import static com.example.flink.withoutFlink.LR.classifyVector;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/12
 * Time: 15:16
 * Description:
 */
public class PredictProcessFunction extends ProcessFunction<String, ArrayList<String>> {
    private ArrayList<Double> finalWeight = new ArrayList<Double>();

    public PredictProcessFunction(ArrayList<Double> finalWeight){
        this.finalWeight = finalWeight;
    }
    @Override
    public void processElement(String value, ProcessFunction<String, ArrayList<String>>.Context ctx, Collector<ArrayList<String>> out) throws Exception {

        ArrayList<String> featureResults = Feature.getMatrixByChangeFeature(value);


        String s1 = classifyVector(featureResults, finalWeight);
        System.out.println("对于 features："+featureResults+"的预测结果为：："+s1);

        out.collect(featureResults);
    }
}
