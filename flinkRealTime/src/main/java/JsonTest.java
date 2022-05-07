import bean.Details;
import bean.PredictBean;
import com.alibaba.fastjson.JSON;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/6
 * Time: 10:15
 * Description:
 */
public class JsonTest {

    public static void main(String[] args) {
        String jsonStr = "{\"pred\":\"No\",\"HeartDisease\":\" \",\"details\":\"{\\\"No\\\":\\\"0.8883664498499148\\\",\\\"Yes\\\":\\\"0.11163355015008525\\\"}\"}";

        String jsonStr2 = "{\n" +
                "\t\"pred\": \"No\",\n" +
                "\t\"HeartDisease\": \" \",\n" +
                "\t\"details\": {\n" +
                "\t\t\"No\": \"0.888366449849923\",\n" +
                "\t\t\"Yes\": \"0.11163355015007703\"\n" +
                "\n" +
                "\t}\n" +
                "}";
        PredictBean predictBean = JSON.parseObject(jsonStr, PredictBean.class);
        String details = predictBean.getDetails();
        Details details1 = JSON.parseObject(details, Details.class);
        System.out.println(details1.getNo());
//        System.out.println(predictBean.getDetails().getNo());
//        System.out.println(no);


    }
}
