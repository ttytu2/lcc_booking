package com.worthytrip.shopping.service.impl;

import com.worthytrip.shopping.service.IRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }

    @Override
    public void set(String key, Object value, int existTime) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        // 设置时间失效
        vo.set(key, value, existTime, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();

        return vo.get(key);
    }

    @Override
    public void setHash(String key, String hashKey, String hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    @Override
    public String getHash(String key, String hashKey) {
        return (String) redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public void removeKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public long getExpire(String redisKey) {
        if (redisTemplate.hasKey(redisKey)) {
            return redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
        }
        return -1;
    }

    @Override
    public boolean isExist(String redisKey) {
        return redisTemplate.hasKey(redisKey);

    }
}