import org.apache.flink.streaming.api.datastream.DataStreamSource;
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

        DataStreamSource<String> streamSource = env.addSource(MyKafkaUtil.getKafkaConsumer("alink-test-1", "alink_group2"));



        streamSource.print();
        env.execute();


    }
}
