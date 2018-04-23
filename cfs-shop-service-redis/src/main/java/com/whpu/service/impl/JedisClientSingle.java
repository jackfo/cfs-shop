package com.whpu.service.impl;

import com.whpu.service.JedisClient;

import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.springframework.beans.factory.annotation.Autowired;

public class JedisClientSingle<T,V> implements JedisClient<T,V> {

    @Override
    public void set(T key, V value) {

    }

    @Override
    public V get(T key) {
        return null;
    }
}
