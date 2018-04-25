package com.whpu.util.redisson;


import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;


public class RedissonUtil {



    public static void set(String area,String key,Object value,RedissonClient redissonClient){
        redissonClient.getMapCache(area).put(key,value);
    }

    public static Object get(String area,String key,RedissonClient redissonClient){
      return  redissonClient.getMapCache(area).get(key);
    }
}
