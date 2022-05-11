package com.example.flink.withFlink;

import com.example.flink.common.Constant;
import com.example.flink.common.Feature;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;

import static com.example.flink.common.Constant.batchDataSet;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/11
 * Time: 17:26
 * Description:
 */
public class FlinkTest {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env1 = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<String> text = env1.readTextFile(batchDataSet);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        ArrayList<String> list = new ArrayList<>();
        DataStreamSource<String> streamSource = env.readTextFile(Constant.testDataSet2);

        DataStreamSource<String> streamSource1 = env.readTextFile(Constant.testDataSet);


        streamSource.union(streamSource1).print();


//        env.readTextFile("D:\\Datasets\\test.txt").process(new ProcessFunction<String, String>() {
//            @Override
//            public void processElement(String s, ProcessFunction<String, String>.Context context, Collector<String> collector) throws Exception {
//                String[] temps;
//                String newValue;
//                if (s.contains("No, borderline diabetes")){
//                    newValue = s.replace("No, borderline diabetes", "Borderline diabetes");
//                    temps = newValue.split(",");
//                }else {
//                    temps = s.split(",");
//                }
//                ArrayList<String> featureResults = Feature.getMatrixByChangeFeature(temps);
//                for (String ss:featureResults) {
//                    System.out.println(ss);
//                }
//
//            }
//        });

        env.execute();

    }

}
