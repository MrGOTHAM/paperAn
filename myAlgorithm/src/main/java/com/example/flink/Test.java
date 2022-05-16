package com.example.flink;

import com.example.flink.withoutFlink.CreateDataSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static java.util.Collections.shuffle;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 21:04
 * Description:
 */
public class Test {

    public static void main(String[] args) {

        ArrayList<String> arr = new ArrayList<>();
        arr.add("an");
        arr.add("chao");
        arr.add("guang");

        shuffle(arr);
        System.out.println(arr);


    }

}
