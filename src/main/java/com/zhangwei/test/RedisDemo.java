package com.zhangwei.test;

import com.zhangwei.redis.RedisClient;

public class RedisDemo {
    public static void main(String[] args) {
        //test01();
        test02();
    }

    public static void test01(){
        String userName = "zhangwei";
        RedisClient.setStr(userName,"1521");
        String pNum = RedisClient.getStr(userName);
        System.out.println(userName+" : "+pNum);
    }

    public static void test02(){
        String userName = "zhangwei";
        RedisClient redisClient = RedisClient.getInstance();
        String pNum = redisClient.getStrVal(userName);
        System.out.println(userName+" : "+pNum);
    }
}
