package com.example.flink.withFlink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/12
 * Time: 22:52
 * Description:
 */
public class FlinkTest2 {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

    }
}
