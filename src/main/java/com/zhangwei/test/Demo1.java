package com.zhangwei.test;

import com.zhangwei.uuid.UUIDGenerator;

public class Demo1 {
    public static void main(String[] args) {
       //test01();
       test02();
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
}
