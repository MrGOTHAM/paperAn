package com.example.flink.withFlink;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 20:49
 * Description:
 */
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.GroupReduceOperator;
import org.apache.flink.api.java.utils.ParameterTool;

import java.util.*;

public class FlinkLR {
    public static void main(String[] args) {
        final ParameterTool params = ParameterTool.fromArgs(args);

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // make parameters available in the web interface
        env.getConfig().setGlobalJobParameters(params);

        // get input data
        DataSet<String> text = env.readTextFile(params.get("input"));

        DataSet<LRinfo> mapResult = text.map(new LRMap());
        GroupReduceOperator<LRinfo, ArrayList<Double>> reduceResult = mapResult.groupBy("groupbyfield").reduceGroup(new LRReduce());
        try {
            List<ArrayList<Double>> resultList = reduceResult.collect();
            int groupSize  = resultList.size();
            Map<Integer,Double> sumMap = new TreeMap<Integer,Double>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            for(ArrayList<Double> array:resultList){

                for(int i=0;i<array.size();i++){
                    double pre = sumMap.get(i)==null?0d:sumMap.get(i);
                    sumMap.put(i,pre+array.get(i));
                }
            }
            ArrayList<Double> finalWeight = new ArrayList<Double>();
            Set<Map.Entry<Integer,Double>> set = sumMap.entrySet();
            for(Map.Entry<Integer,Double> mapEntry :set){
                Integer key = mapEntry.getKey();
                Double sumValue = mapEntry.getValue();
                double finalValue = sumValue/groupSize;
                finalWeight.add(finalValue);
            }
            env.execute("LogicTask analy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
