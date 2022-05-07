import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import util.MyKafkaUtil;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/5
 * Time: 16:16
 * Description:
 */
public class Test {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        String sourceTopic = "alink-test-1";
        String groupId ="alink_group2";

        DataStreamSource<String> streamSource = env.addSource(MyKafkaUtil.getKafkaConsumer(sourceTopic, groupId));

//        {"pred":"No","HeartDisease":" ","details":"{\"No\":\"0.888366449849923\",\"Yes\":\"0.11163355015007703\"}"}
        SingleOutputStreamOperator<JSONObject> map = streamSource.map(JSON::parseObject);

        streamSource.print();
        env.execute();


    }
}
