package com.whpu.service.impl;

import com.whpu.service.IRedisService;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl<T,V> implements IRedisService<T,V> {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void set(T key, V value,String type) {
        redissonClient.getMapCache(type).put(key,value);
    }

    @Override
    public V get(T key, String type) {
        return (V) redissonClient.getMapCache(type).get(key);
    }
}
