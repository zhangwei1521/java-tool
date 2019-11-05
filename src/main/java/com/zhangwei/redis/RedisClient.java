package com.zhangwei.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisClient {

    public static boolean setStr(String key,String value){
        Jedis jedis = new Jedis("192.168.56.2",6379);
        jedis.set(key,value);
        jedis.close();
        return true;
    }

    public static String getStr(String key){
        Jedis jedis = new Jedis("192.168.56.2",6379);
        String value = jedis.get(key);
        jedis.close();
        return value;
    }

    private JedisPool jedisPool;

    private RedisClient(){}

    private static class RedisClientHolder{
        private static final RedisClient instance = new RedisClient();
    }

    public static RedisClient getInstance(){
        return RedisClientHolder.instance;
    }

    private synchronized void initialize(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(20);
        jedisPool = new JedisPool(config,"192.168.56.2",6379,2000,null);
    }

    private Jedis getJedis(){
        if(jedisPool==null){
            initialize();
        }
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    public boolean setStrVal(String key,String value){
        Jedis jedis = getJedis();
        jedis.set(key,value);
        jedis.close();
        return true;
    }

    public String getStrVal(String key){
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }
}
