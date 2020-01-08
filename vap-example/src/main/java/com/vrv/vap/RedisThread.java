package com.vrv.vap;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Author: liujinhui
 * @Date: 2019/11/26 14:20
 */
public class RedisThread extends Thread {

    private StringRedisTemplate redisTemplate;
    private String REVERSE_KEY = "batchJob:task_";

    public RedisThread(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void run() {
        for (int i = 0; i < 50; i++) {
            String value = Thread.currentThread().getName() + "{user:user;name:liujinhui" + System.currentTimeMillis() + "}";
            System.out.println("---------value:" + value);
            redisTemplate.opsForValue().set(REVERSE_KEY + System.currentTimeMillis(), value);
            redisTemplate.getConnectionFactory().getConnection().close();
        }
        System.out.println("完成1111111111");
    }
}
