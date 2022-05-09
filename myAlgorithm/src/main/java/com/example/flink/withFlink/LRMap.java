package com.example.flink.withFlink;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 20:51
 * Description:
 */
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;
import java.util.Random;

public class LRMap implements MapFunction<String,LRinfo> {

    @Override
    public LRinfo map(String value) throws Exception {
        if(StringUtils.isBlank(value)){
            return null;
        }

        Random random = new Random();
        String[] temps = value.split(",");
        LRinfo lRinfo = new LRinfo();
        ArrayList<String> list = new ArrayList<>();
        for(int i=0;i<temps.length-1;i++) list.add(temps[i]);

        lRinfo.setData(list);
        lRinfo.setLabel(temps[temps.length-1]);
        // random.nextInt(10) 随机数
        lRinfo.setGroupbyfield("logic=="+random.nextInt(10));
        return lRinfo;
    }
}