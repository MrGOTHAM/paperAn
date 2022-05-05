package util;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/5
 * Time: 17:42
 * Description:     把origin-heart的数据，2w条写入heart-batch，其余写入 heart-stream文件  ，数据切分，一部分作为皮数据，一部分作为流数据（在线学习使用）
 */
public class ProduceStreamHeartData {

    public static void main(String[] args) throws IOException {

        //BufferedReader是可以按行读取文件
        FileInputStream inputStream = new FileInputStream("D:\\Datasets\\origin-heart.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String str = null;
        int i = 0;

//写入中文字符时解决中文乱码问题
        FileOutputStream fos = new FileOutputStream(new File("D:\\Datasets\\heart-batch.csv"), true);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);
        while ((str = bufferedReader.readLine()) != null) {
            if (i == 200000) {
                break;
            }
            bw.write(str + "\n");
            i++;
        }

        //注意关闭的先后顺序，先打开的后关闭，后打开的先关闭
        bw.close();
        osw.close();
        fos.close();
        //close
        inputStream.close();
        bufferedReader.close();

    }


}
