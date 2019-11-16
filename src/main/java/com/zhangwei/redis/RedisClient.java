package com.zhangwei.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.Map;

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

    public void testStr() throws InterruptedException {
        Jedis jedis = getJedis();
        jedis.select(1);
        jedis.flushDB();
        System.out.println("===========testStr===========");
        jedis.set("a","1");
        jedis.setnx("a","2");
        System.out.println("a : "+jedis.get("a"));
        jedis.set("b","1", SetParams.setParams().xx());
        jedis.setex("b",2,"2");
        System.out.println("b : "+jedis.get("b"));
        Thread.sleep(3000);
        System.out.println("b : "+jedis.get("b"));
        jedis.set("b","3");
        jedis.append("b","-4");
        System.out.println("b : "+jedis.get("b"));
        jedis.mset("key01","val01","key02","val02");
        System.out.println(jedis.mget("key01","key02"));
        jedis.del("key02");
        System.out.println(jedis.mget("key01","key02"));
        System.out.println(jedis.getrange("key01",1,2));
        //jedis.mset("key03","val03","key02");//抛出异常
        //System.out.println(jedis.mget("key03","val01"));

        jedis.close();
    }

    public void testNum() {
        Jedis jedis = getJedis();
        jedis.select(1);
        jedis.flushDB();
        System.out.println("===========testNum===========");
        jedis.set("a","1");
        System.out.println("a : "+jedis.get("a"));
        jedis.incr("a");
        System.out.println("a : "+jedis.get("a"));
        jedis.decr("a");
        System.out.println("a : "+jedis.get("a"));
        jedis.incrBy("a",5);
        System.out.println("a : "+jedis.get("a"));
        jedis.decrBy("a",4);
        System.out.println("a : "+jedis.get("a"));

        jedis.close();
    }

    public void testList() {
        Jedis jedis = getJedis();
        jedis.select(1);
        jedis.flushDB();
        System.out.println("===========testList===========");
        jedis.lpush("list","a");
        jedis.lpush("list","b","c");
        System.out.println("list : "+jedis.lrange("list",0,-1));
        jedis.rpush("list","d","e");
        System.out.println("list : "+jedis.lrange("list",0,-1));
        System.out.println("list : "+jedis.lrange("list",0,2));
        jedis.lpush("list","a","a");
        System.out.println("list : "+jedis.lrange("list",0,-1));
        System.out.println("list length : "+jedis.llen("list"));
        System.out.println("element in 3 of list : "+jedis.lindex("list",3));
        jedis.lrem("list",1,"a");
        System.out.println("list : "+jedis.lrange("list",0,-1));
        jedis.ltrim("list",1,3);
        System.out.println("list : "+jedis.lrange("list",0,-1));
        System.out.println("list : "+jedis.lpop("list"));
        System.out.println("list : "+jedis.rpop("list"));
        System.out.println("list : "+jedis.lrange("list",0,-1));

        jedis.close();
    }

    public void testSet() {
        Jedis jedis = getJedis();
        jedis.select(1);
        jedis.flushDB();
        System.out.println("===========testSet===========");
        jedis.sadd("set","a");
        jedis.sadd("set","b","c","d");
        System.out.println("set : "+jedis.smembers("set"));
        jedis.sadd("set","a","b");
        System.out.println("set : "+jedis.smembers("set"));
        System.out.println(jedis.srem("set","a","b"));
        System.out.println("set : "+jedis.smembers("set"));
        System.out.println(jedis.spop("set"));
        System.out.println("set : "+jedis.smembers("set"));
        System.out.println("set size : "+jedis.scard("set"));
        jedis.del("set");
        jedis.sadd("set1","a","b","c","d");
        jedis.smove("set1","set2","a");
        System.out.println("set1 : "+jedis.smembers("set1"));
        System.out.println("set2 : "+jedis.smembers("set2"));
        jedis.sadd("set1","e");
        jedis.sadd("set2","a","b","c","d");
        System.out.println("*set1 : "+jedis.smembers("set1"));
        System.out.println("*set2 : "+jedis.smembers("set2"));
        System.out.println("inter of set1 and set2 : "+jedis.sinter("set1","set2"));
        System.out.println("union of set1 and set2 : "+jedis.sunion("set1","set2"));
        System.out.println("set1 diff set2 : "+jedis.sdiff("set1","set2"));
        System.out.println("set2 diff set1 : "+jedis.sdiff("set2","set1"));
        jedis.close();
    }

    public void testMap() {
        Jedis jedis = getJedis();
        jedis.select(1);
        jedis.flushDB();
        System.out.println("===========testMap===========");
        Map<String,String> strMap = new HashMap<>();
        strMap.put("mk1","mv1");
        strMap.put("mk2","mv2");
        jedis.hset("map","mk0","mv0");
        jedis.hmset("map",strMap);
        System.out.println("map : "+jedis.hgetAll("map"));
        System.out.println("map keys : "+jedis.hkeys("map"));
        System.out.println("map values : "+jedis.hvals("map"));
        System.out.println("map key[0] : "+jedis.hget("map","mk0"));
        System.out.println("map key[1],key[1] : "+jedis.hmget("map","mk1","mk2"));
        //jedis.hincrBy("map","mk0",5); 抛出异常：JedisDataException: ERR hash value is not an integer
        //jedis.hincrBy("map","mk3",5); 如果mk3不存在则添加mk3
        System.out.println("map size : "+jedis.hlen("map"));
        jedis.hdel("map","mk2");
        System.out.println("map : "+jedis.hgetAll("map"));
        System.out.println("map exists mk1 ? "+jedis.hexists("map","mk1"));

        jedis.close();
    }

    public void testOrderdedSet() {
        Jedis jedis = getJedis();
        jedis.select(1);
        jedis.flushDB();
        System.out.println("===========testOrderdedSet===========");
        jedis.zadd("orderSet",1.2,"a");
        Map<String,Double> map = new HashMap<>();
        map.put("b",2.0);
        map.put("c",0.8);
        map.put("d",5.0);
        map.put("e",3.2);
        jedis.zadd("orderSet",map);
        System.out.println("orderSet : "+jedis.zrange("orderSet",0,-1));
        System.out.println("orderSet with score : "+jedis.zrangeWithScores("orderSet",0,-1));
        System.out.println("orderSet* : "+jedis.zrangeByScore("orderSet",0,4));
        System.out.println("orderSet* with score : "+jedis.zrangeByScoreWithScores("orderSet",0,4));
        System.out.println("orderSet a score : "+jedis.zscore("orderSet","a"));
        System.out.println("orderSet a rank : "+jedis.zrank("orderSet","a"));
        System.out.println(jedis.zrem("orderSet","d"));
        System.out.println("orderSet : "+jedis.zrange("orderSet",0,-1));
        System.out.println("orderSet size : "+jedis.zcard("orderSet"));
        System.out.println("orderSet score 1-3 size : "+jedis.zcount("orderSet",1.0,3.0));
        jedis.zincrby("orderSet",0.5,"a");
        System.out.println("orderSet with score : "+jedis.zrangeWithScores("orderSet",0,-1));

        jedis.close();
    }
}
