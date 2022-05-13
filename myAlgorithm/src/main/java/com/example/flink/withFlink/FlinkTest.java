package com.example.flink.withFlink;

import com.example.flink.common.Model;
import com.example.flink.common.MyKafkaUtil;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/11
 * Time: 17:26
 * Description:
 */
public class FlinkTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        // 设置时间语义 处理时间
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        final ArrayList<String> list = new ArrayList<>();
        env.socketTextStream("an",8888)
                .timeWindowAll(Time.seconds(10)).process(new ProcessAllWindowFunction<String, String, TimeWindow>() {
                    @Override
                    public void process(ProcessAllWindowFunction<String, String, TimeWindow>.Context context, Iterable<String> elements, Collector<String> out) throws Exception {
                        for (String element : elements) {
//                            System.out.println(element);
                            list.add(element);
                            Model.saveModela("test.txt",element);
                        }
                        for (String s :list){
                            System.out.print(s+" ");
                        }
                        System.out.println();
                    }
                });
        env.execute();

    }

}
