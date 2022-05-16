package com.example.flink.withFlink.function;

import com.example.flink.common.Feature;
import com.example.flink.common.Model;
import com.example.flink.withFlink.LRinfo;
import com.example.flink.withoutFlink.CreateDataSet;
import com.example.flink.withoutFlink.LR;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import java.util.ArrayList;
import static com.example.flink.common.Constant.labelIndex;
import static com.example.flink.common.Constant.modelPath;


/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/12
 * Time: 21:22
 * Description:
 */
public class TrainProcessFunction extends ProcessAllWindowFunction<String, String, TimeWindow> {
    private int i = 0;

    @Override
    public void process(ProcessAllWindowFunction<String, String, TimeWindow>.Context context, Iterable<String> elements, Collector<String> out) throws Exception {

        CreateDataSet streamTrainSet = new CreateDataSet();
        for (String element : elements) {
            String newValue;
            String[] temps;
            if (element.contains("No, borderline diabetes")){
                newValue = element.replace("No, borderline diabetes", "Borderline diabetes");
                temps = newValue.split(",");
            }else {
                temps = element.split(",");
            }
            LRinfo lRinfo = new LRinfo();
            ArrayList<String> list = Feature.getMatrixByChangeFeature(element);
            lRinfo.setData(list);
            lRinfo.setLabel(temps[labelIndex].equals("Yes")?"1":"0");
            streamTrainSet.getData().add(lRinfo.getData());
            streamTrainSet.getLabels().add(lRinfo.getLabel());
        }
        ArrayList<Double> weights = new ArrayList<>();
        weights = LR.gradAscent1(streamTrainSet, streamTrainSet.labels, 500);
        ArrayList<Double> doubles = Model.loadModel(modelPath);
        ArrayList<Double> result = new ArrayList<>();
        for (int j = 0; j < weights.size(); j++) {
            double tmp = weights.get(i) + doubles.get(i)*(10.0+i);
            result.add(tmp/(10.0+i+1));
        }
        System.out.println("i====="+i);
        i++;

        Model.saveModel(modelPath,result);
    }
}
