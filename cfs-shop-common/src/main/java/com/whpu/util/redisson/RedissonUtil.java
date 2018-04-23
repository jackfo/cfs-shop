package com.whpu.util.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.yml")
public class RedissonUtil {

    @Autowired
    public static RedissonClient  redissonClient;



    public static void set(String area,String key,Object value){
        redissonClient.getMapCache(area).put(key,value);
    }

    public static Object get(String area,String key){
      return  redissonClient.getMapCache(area).get(key);
    }
}
