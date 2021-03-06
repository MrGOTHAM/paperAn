package com.example.flink.withFlink;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 20:51
 * Description:
 */
import com.example.flink.common.Feature;
import com.example.flink.common.FeatureAfterRF;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;
import java.util.Random;

import static com.example.flink.common.Constant.labelIndex;

public class LRMap implements MapFunction<String,LRinfo> {
    public LRMap(){}

    @Override
    public LRinfo map(String value) throws Exception {
        if(StringUtils.isBlank(value)){
            return null;
        }

        Random random = new Random();
        String newValue;
        String[] temps;
        if (value.contains("No, borderline diabetes")){
            newValue = value.replace("No, borderline diabetes", "Borderline diabetes");
            temps = newValue.split(",");
        }else {
            temps = value.split(",");
        }
        LRinfo lRinfo = new LRinfo();
        ArrayList<String> list = Feature.getMatrixByChangeFeature(value);
        lRinfo.setData(list);
        lRinfo.setLabel(temps[labelIndex].equals("Yes")?"1":"0");
        // random.nextInt(10) 随机数
        lRinfo.setGroupbyfield("logic=="+random.nextInt(10));
        return lRinfo;
    }
}