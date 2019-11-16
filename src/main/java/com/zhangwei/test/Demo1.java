package com.zhangwei.test;

import com.zhangwei.uuid.UUIDGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo1 {
    public static void main(String[] args) {
        test01();
        //test02();
        //test03();
        //test04();
        //test05();
        //test06();
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
        dateList.sort((a,b)->b.compareTo(a));
        System.out.println(dateList);
        //索引越界抛出异常：IndexOutOfBoundsException
        //System.out.println(dateList.get(3));
        dateList.remove(0);
        System.out.println(dateList);
    }

    //测试日期格式化
    private static void test05(){
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String nowStr = format.format(now);
        System.out.println(nowStr);
    }

    //测试String
    private static void test06(){
        Map<String,Object> alarmResultNameMap = new HashMap<>();
        alarmResultNameMap.put("I级","1");
        alarmResultNameMap.put("II级","2");
        alarmResultNameMap.put("III级","3");
        alarmResultNameMap.put("Ⅳ级","4");
        alarmResultNameMap.put("V级","5");
        String s = new String("I级");
        System.out.println(alarmResultNameMap.get(s));
    }
}
