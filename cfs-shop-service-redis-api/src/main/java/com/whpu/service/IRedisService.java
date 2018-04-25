package com.whpu.service;

public interface IRedisService<T,V> {

    public void set(T key,V value,String type);

    public V get(T key,String type);
}
