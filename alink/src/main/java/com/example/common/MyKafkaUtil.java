package com.example.common;

import com.alibaba.alink.operator.stream.sink.KafkaSinkStreamOp;
import com.alibaba.alink.operator.stream.source.KafkaSourceStreamOp;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/5
 * Time: 11:25
 * Description:
 */
public class MyKafkaUtil {
    public static String kafkaServer = "tao:9092";
    public static String startupMode = "EARLIEST";
    public static String groupID = "alink_group1";
    public static String dataFormat = "json";


    public static KafkaSourceStreamOp getKafkaConsumer(String topic){

        KafkaSourceStreamOp source = new KafkaSourceStreamOp()
                .setBootstrapServers(kafkaServer)
                .setTopic(topic)
                .setStartupMode(startupMode)
                .setGroupId(groupID);
        return source;
    }

    public static KafkaSinkStreamOp getKafkaProducer(String topic){
        KafkaSinkStreamOp sink = new KafkaSinkStreamOp()
                .setBootstrapServers(kafkaServer)
                .setDataFormat(dataFormat)
                .setTopic(topic);
        return sink;
    }

}
