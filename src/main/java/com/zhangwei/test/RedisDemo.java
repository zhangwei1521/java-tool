package com.zhangwei.test;

import com.zhangwei.redis.RedisClient;

public class RedisDemo {
    public static void main(String[] args) {
        //test01();
        //test02();
        test03();
    }

    public static void test01(){
        String userName = "zhangwei";
        RedisClient.setStr(userName,"1234");
        String pNum = RedisClient.getStr(userName);
        System.out.println(userName+" : "+pNum);
    }

    public static void test02(){
        String userName = "zhangwei";
        RedisClient redisClient = RedisClient.getInstance();
        String pNum = redisClient.getStrVal(userName);
        System.out.println(userName+" : "+pNum);
    }

    public static void test03(){
        RedisClient redisClient = RedisClient.getInstance();
        try {
            //redisClient.testStr();
            //redisClient.testNum();
            //redisClient.testList();
            //redisClient.testSet();
            //redisClient.testMap();
            redisClient.testOrderdedSet();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
