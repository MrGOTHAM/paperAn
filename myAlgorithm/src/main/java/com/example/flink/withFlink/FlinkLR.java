package com.example.flink.withFlink;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 20:49
 * Description:
 */

import com.example.flink.common.Model;
import com.example.flink.withFlink.evaluate.Evaluator;
import com.example.flink.withFlink.function.BatchPredict;
import com.example.flink.withFlink.function.PredictProcessFunction;
import com.example.flink.withFlink.function.TrainProcessFunction;
import com.example.flink.withoutFlink.CreateDataSet;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.GroupReduceOperator;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import java.util.*;

import static com.example.flink.common.Constant.*;
import static com.example.flink.withoutFlink.LR.*;

public class FlinkLR {
    public static void main(String[] args) throws Exception {
        /*
         * 批数据训练部分
         */
        final ExecutionEnvironment env1 = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<String> text = env1.readTextFile("D:\\Datasets\\paperAn\\underSampling\\heart-train.csv");
        DataSet<LRinfo> mapResult = text.map(new LRMap());
        GroupReduceOperator<LRinfo, ArrayList<Double>> reduceResult = mapResult.groupBy("groupbyfield").reduceGroup(new LRReduce());
        // 把10个组的数据全部收集起来
        List<ArrayList<Double>> resultList = reduceResult.collect();
        int groupSize = resultList.size();
        Map<Integer, Double> sumMap = new TreeMap<Integer, Double>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // o1 == o2 -> 0
                // o1 >  o2 -> 1
                // o1 <  o2 -> -1
                return o1.compareTo(o2);
            }
        });
        // 树中的对应key的值将被更新 （更新的值为对应key的value值的和）
        for (ArrayList<Double> array : resultList) {

            for (int i = 0; i < array.size(); i++) {
                double pre = sumMap.get(i) == null ? 0d : sumMap.get(i);
                sumMap.put(i, pre + array.get(i));
            }
        }
        ArrayList<Double> finalWeight = new ArrayList<Double>();
        // 跟上面的treemap数据是一样的，只是数据类型 不一样
        Set<Map.Entry<Integer, Double>> set = sumMap.entrySet();
        // 所有的value在对应的key条件下都除以groupSize的个数，如何按顺序放入finalWeight 作为最后的weight
        for (Map.Entry<Integer, Double> mapEntry : set) {
            Integer key = mapEntry.getKey();
            Double sumValue = mapEntry.getValue();
            double finalValue = sumValue / groupSize;
            finalWeight.add(finalValue);
        }
        System.out.println(finalWeight);
        //保存模型
        Model.saveModel(modelPath, finalWeight);

        /*
         * 验证部分
         */
        CreateDataSet testData = readFileWithoutPlus1("D:\\Datasets\\paperAn\\underSampling\\heart-test.csv");
        ArrayList<String> batchPredict = BatchPredict.predict(Model.loadModel(modelPath), testData);
        Evaluator eva = new Evaluator();
        eva.accuracy(batchPredict, testData.labels);
        eva.recall(batchPredict, testData.labels);
        eva.precision(batchPredict, testData.labels);
        eva.f1Score(batchPredict, testData.labels);
        eva.falseAndTrue(testData.labels);

        /*
         * 继续以流数据进行训练部分
         */
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        // 设置时间语义 处理时间
//        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
//        env.socketTextStream("an",8888)
//                .timeWindowAll(Time.seconds(10)).process(new TrainProcessFunction());


        /*
         * 用流数据进行在线预测部分
         */
        SingleOutputStreamOperator<ArrayList<String>> streamPredictionDS = env.readTextFile("D:\\Datasets\\test.txt").process(new PredictProcessFunction(Model.loadModel(modelPath)));

        /*
         * 流数据用来验证准确率部分
         */

        env.execute();
    }
}
