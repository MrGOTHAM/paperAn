package com.example.flink.withFlink;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 20:49
 * Description:
 */
import com.example.flink.withoutFlink.CreateDataSet;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.GroupReduceOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.*;

import static com.example.flink.common.Constant.*;
import static com.example.flink.withoutFlink.LR.*;

public class FlinkLR {
    public static void main(String[] args) {

        final ExecutionEnvironment env1 = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<String> text = env1.readTextFile(batchDataSet);
        DataSet<LRinfo> mapResult = text.map(new LRMap());
        GroupReduceOperator<LRinfo, ArrayList<Double>> reduceResult = mapResult.groupBy("groupbyfield").reduceGroup(new LRReduce());
        try {
            List<ArrayList<Double>> resultList = reduceResult.collect();
            int groupSize  = resultList.size();
            Map<Integer,Double> sumMap = new TreeMap<Integer,Double>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    // o1 == o2 -> 0
                    // o1 >  o2 -> 1
                    // o1 <  o2 -> -1
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
            System.out.println(finalWeight);

            // predict
            //创建测试集对象
            CreateDataSet testData = readFileWithoutPlus1(streamDataSet);// 23 445 34 1  45 56 67 0

            /**
             * 计算误差
             * */
            int errorCount = 0;
            for (int i = 0; i < testData.data.size(); i++) {
                if (!classifyVector(testData.data.get(i), finalWeight).equals(testData.labels.get(i))) {
                    errorCount++;
                }
                System.out.println("这里是结果：：：："+classifyVector(testData.data.get(i), finalWeight) + "," + testData.labels.get(i));
            }
            System.out.println("预测准确度：："+(testData.data.size() - 1.0 * errorCount) / testData.data.size());

//            env.execute("LogicTask analy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
