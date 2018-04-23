package com.whpu.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.ip}")
    private  String ip;

    @Value("${spring.redis.port}")
    private  String port;

    @Bean
    RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+ip+":"+port);
        return Redisson.create(config);
    }



}
