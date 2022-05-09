package com.example.flink;

import com.example.flink.withoutFlink.CreateDataSet;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: an
 * Date: 2022/5/7
 * Time: 21:04
 * Description:
 */
public class Test {

    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(random.nextInt(10));

        Integer a = 10;
        Integer b = 20;
        System.out.println(Integer.compare(30, 20));


    }

}
