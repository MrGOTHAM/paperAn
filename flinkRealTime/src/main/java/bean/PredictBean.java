package bean;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/6
 * Time: 9:58
 * Description:
 */
@Data
public class PredictBean {
    //        {"pred":"No","HeartDisease":" ","details":"{\"No\":\"0.888366449849923\",\"Yes\":\"0.11163355015007703\"}"}

    private String pred;
    private String HeartDisease;
    private String details;




}
