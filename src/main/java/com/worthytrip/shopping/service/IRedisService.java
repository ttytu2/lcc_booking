package com.worthytrip.shopping.service;

public interface IRedisService {
    void set(String key, Object value);

    void set(String key, Object value, int existTime);

    Object get(String key);

    long getExpire(String redisKey);

    boolean isExist(String redisKey);

    void setHash(String key, String hashKey, String hashValue);

    String getHash(String key, String hashKey);

    void removeKey(String key);
}
