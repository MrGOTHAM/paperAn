package com.example;

import com.alibaba.alink.operator.stream.StreamOperator;
import com.alibaba.alink.operator.stream.source.KafkaSourceStreamOp;
import com.example.common.MyKafkaUtil;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/5
 * Time: 11:33
 * Description:
 */
public class KafkaCOnsumer {

    public static void main(String[] args) throws Exception {

        KafkaSourceStreamOp kafkaConsumer = MyKafkaUtil.getKafkaConsumer("alink-test-1");
        kafkaConsumer.print();

        StreamOperator.execute();


    }
}
