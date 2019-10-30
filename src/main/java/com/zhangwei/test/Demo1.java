package com.zhangwei.test;

import com.zhangwei.uuid.UUIDGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Demo1 {
    public static void main(String[] args) {
        //test01();
        //test02();
        //test03();
        test04();
    }

    //测试UUIDGenerator
    private static void test01(){
        UUIDGenerator uuidGenerator = new UUIDGenerator(27,16);
        long uuid1 = uuidGenerator.nextUUID();
        long uuid2 = uuidGenerator.nextUUID();
        long uuid3 = uuidGenerator.nextUUID();
        System.out.println("uuid: "+uuid1);
        System.out.println("uuid: "+uuid2);
        System.out.println("uuid: "+uuid3);
    }

    //测试左移运算
    private static void test02(){
        long i = -1l;
        System.out.println(i+" : "+Long.toBinaryString(i));
        long j = i << 5;
        System.out.println(j+" : "+Long.toBinaryString(j));
        long k = i^j;
        System.out.println(k+" : "+Long.toBinaryString(k));

        byte l = -19;
        System.out.println(l+" : "+Integer.toBinaryString(l));
        int m = l << 6;
        System.out.println(m+" : "+Integer.toBinaryString(m));

        byte o = 4;
        System.out.println(o+" : "+Integer.toBinaryString(o));
        int p = o << 6;
        System.out.println(p+" : "+Integer.toBinaryString(p));
    }

    //打印classpath
    private static void test03(){
        String classpath = System.getProperty("java.class.path");
        String [] arr = classpath.split(";");
        for(String path : arr){
            System.out.println(path);
        }
    }

    //测试List排序
    private static void test04(){
        List<Date> dateList = new ArrayList<>();
        Date date1 = new Date(119,11,10,13,56,59);
        Date date2 = new Date(119,11,9,13,56,59);
        Date date3 = new Date(119,11,11,13,56,59);
        dateList.add(date1);
        dateList.add(date2);
        dateList.add(date3);
        dateList.sort((a,b)->a.compareTo(b));
        System.out.println(dateList);
    }
}
