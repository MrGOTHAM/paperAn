package com.example.flink.withFlink;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 20:52
 * Description:
 */
import com.example.flink.withoutFlink.LR;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 输入进来的是一连串的LRinfo
 * */
public class LRReduce implements GroupReduceFunction<LRinfo, ArrayList<Double>> {
    @Override
    public void reduce(Iterable<LRinfo> values, Collector<ArrayList<Double>> out) throws Exception {
        Iterator<LRinfo> iterator = values.iterator();
        CreateDataSet trainingSet = new CreateDataSet();
        while(iterator.hasNext()){
            LRinfo lRinfo = iterator.next();
            trainingSet.getData().add(lRinfo.getData());
            trainingSet.getLabels().add(lRinfo.getLabel());
        }
        ArrayList<Double> weights = new ArrayList<>();
        weights = LR.gradAscent1(trainingSet, trainingSet.labels, 500);
        out.collect(weights);
    }
}