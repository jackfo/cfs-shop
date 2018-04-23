package com.whpu.util.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;


public class RedissonUtil {

    static class redisInner{
      public static  RedissonClient redissonClient;
      static {
          redissonClient = createInstance();
      }
    }

    @Value("${spring.redis.ip}")
    private static String ip;

    @Value("${spring.redis.port}")
    private static String port;

    public RedissonClient getRedissonClient(){
        return redisInner.redissonClient;
    }

    public static RedissonClient createInstance() {
        Config config = createConfig();
        return Redisson.create(config);
    }

    public static Config createConfig() {
        Config config = new Config();
        config.useSingleServer().setAddress("http://"+ip+":"+port);
        return config;
    }
}
