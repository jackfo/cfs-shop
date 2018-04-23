package com.whpu.service;

/**
 * 针对redission做的一层封装
 * */
public interface JedisClient<T,V> {

    public void set(T key, V value);

    public V get(T key);
}
