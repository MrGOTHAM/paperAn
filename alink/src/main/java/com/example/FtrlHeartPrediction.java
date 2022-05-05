package com.example;

import com.alibaba.alink.operator.batch.BatchOperator;
import com.alibaba.alink.operator.batch.classification.LogisticRegressionTrainBatchOp;
import com.alibaba.alink.operator.batch.source.CsvSourceBatchOp;
import com.alibaba.alink.operator.stream.StreamOperator;
import com.alibaba.alink.operator.stream.dataproc.JsonValueStreamOp;
import com.alibaba.alink.operator.stream.dataproc.SplitStreamOp;
import com.alibaba.alink.operator.stream.evaluation.EvalBinaryClassStreamOp;
import com.alibaba.alink.operator.stream.onlinelearning.FtrlPredictStreamOp;
import com.alibaba.alink.operator.stream.onlinelearning.FtrlTrainStreamOp;
import com.alibaba.alink.operator.stream.sink.KafkaSinkStreamOp;
import com.alibaba.alink.operator.stream.source.CsvSourceStreamOp;
import com.alibaba.alink.operator.stream.source.KafkaSourceStreamOp;
import com.alibaba.alink.pipeline.Pipeline;
import com.alibaba.alink.pipeline.PipelineModel;
import com.alibaba.alink.pipeline.feature.FeatureHasher;
import com.example.common.MyKafkaUtil;

import static com.example.common.Constant.*;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/4
 * Time: 16:55
 * Description: 在线学习、预测心脏病模型
 */
public class FtrlHeartPrediction {
    String schemaStr;
    String[] selectedColNames;
    String[] categoryColNames;
    String[] numericalColNames;
    String labelColName;

    String vecColName = "vec";
    int numHashFeatures = 30000;

    public FtrlHeartPrediction() throws Exception {
        this.schemaStr = SCHEMASTR;
        this.selectedColNames = SELECTEDCOLNAMES;
        this.categoryColNames = CATEGORYCOLNAMES;
        this.numericalColNames = NUMERICALCOLNAMES;
        this.labelColName = LABELCOLNAME;
        startModel();
    }

    public FtrlHeartPrediction(String schemaStr, String[] selectedColNames, String[] categoryColNames, String[] numericalColNames, String labelColName) throws Exception {
        this.schemaStr = schemaStr;
        this.selectedColNames = selectedColNames;
        this.categoryColNames = categoryColNames;
        this.numericalColNames = numericalColNames;
        this.labelColName = labelColName;
        startModel();
    }

    public void startModel() throws Exception {

        CsvSourceBatchOp trainBatchData = new CsvSourceBatchOp()
                .setIgnoreFirstLine(true)
                .setFilePath(BATCH_HEART_DATASET)
                .setSchemaStr(schemaStr);

        Pipeline feature_pipeline = new Pipeline()
                .add(
                        new FeatureHasher()
                                .setSelectedCols(selectedColNames)
                                .setCategoricalCols(categoryColNames)
                                .setOutputCol(vecColName)
                                .setNumFeatures(numHashFeatures)
                );

        // 训练并保存管道模型
        // fit and save feature pipeline model
        String FEATURE_PIPELINE_MODEL_FILE = "D:\\machine_learning\\models\\feature_heart_pipe_model.csv";
        feature_pipeline.fit(trainBatchData).save(FEATURE_PIPELINE_MODEL_FILE, true);

        BatchOperator.execute();
        StreamOperator.setParallelism(2);

        // 准备流式数据 并切分，训练集和测试集 1：1
        // prepare stream train data
        CsvSourceStreamOp data = new CsvSourceStreamOp()
                .setFilePath(STREAM_HEART_DATASET)
                .setIgnoreFirstLine(true)
                .setSchemaStr(schemaStr);
        // split stream to train and eval data
        SplitStreamOp spliter = new SplitStreamOp().setFraction(0.5).linkFrom(data);
        StreamOperator train_stream_data = spliter;
        StreamOperator test_stream_data2 = spliter.getSideOutput(0);
        CsvSourceStreamOp test_stream_data = new CsvSourceStreamOp()
                .setFilePath(MOCK_HEART_DATASET)
                .setSchemaStr(schemaStr);
        KafkaSourceStreamOp serverRequest = MyKafkaUtil.getKafkaConsumer("alink-test-1");
        PipelineModel feature_pipelineModel = PipelineModel.load(FEATURE_PIPELINE_MODEL_FILE);

        LogisticRegressionTrainBatchOp lr = new LogisticRegressionTrainBatchOp()
                .setVectorCol(vecColName)
                .setLabelCol(labelColName)
                .setWithIntercept(true)
                .setMaxIter(10);
        // 现在训练出的才是初始模型
        BatchOperator initModel = feature_pipelineModel.transform(trainBatchData).link(lr);


        //FtrlTrainStreamOp的构造函数中输入初始模型initModel，随后是设置各种参数，并“连接“流式向量训练数据
        // ftrl train
        FtrlTrainStreamOp model = new FtrlTrainStreamOp(initModel)
                .setVectorCol(vecColName)
                .setLabelCol(labelColName)
                .setWithIntercept(true)
                .setAlpha(0.1)
                .setBeta(0.1)
                .setL1(0.01)
                .setL2(0.01)
                .setTimeInterval(10)
                .setVectorSize(numHashFeatures)
                .linkFrom(feature_pipelineModel.transform(train_stream_data));


        // FTRL在线预测的代码如下，需要“连接”FTRL在线模型训练输出的模型流，和流式向量预测数据。
        // ftrl predict
        FtrlPredictStreamOp predictResult = new FtrlPredictStreamOp(initModel)
                .setVectorCol(vecColName)
                .setPredictionCol("pred")
                .setReservedCols(new String[]{labelColName})
                .setPredictionDetailCol("details")
                // 用来预测来自client端用户的心脏情况
                .linkFrom(model, feature_pipelineModel.transform(serverRequest));
        //  预测完之后把消息发送回前端
        predictResult.link(MyKafkaUtil.getKafkaProducer("alink-test-1"));
        // 用来在线训练的测试集
        predictResult.linkFrom(model, feature_pipelineModel.transform(test_stream_data2));
//        predictResult.print();
        predictResult
                .link(
                        new EvalBinaryClassStreamOp()
                                .setLabelCol(labelColName)
                                .setPredictionCol("pred")
                                .setPredictionDetailCol("details")
                                .setTimeInterval(10)
                )
                .link(
                        new JsonValueStreamOp()
                                .setSelectedCol("Data")
                                .setReservedCols(new String[]{"Statistics"})
                                .setOutputCols(new String[]{"Accuracy", "AUC", "ConfusionMatrix"})
                                .setJsonPath(new String[]{"$.Accuracy", "$.AUC", "$.ConfusionMatrix"})
                )
                .print();

        StreamOperator.execute();
    }
}
