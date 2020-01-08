package com.vrv.vap;

import com.vrv.vap.consumer.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: liujinhui
 * @Date: 2019/12/4 10:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void whenCreateRedis() {
        // 保存对象
        User user = new User("超人", "20");
        redisTemplate.opsForValue().set(user.getUsername(), user);

        user = new User("蝙蝠侠", "30");
        redisTemplate.opsForValue().set(user.getUsername(), user);

        user = new User("蜘蛛侠", "40");
        redisTemplate.opsForValue().set(user.getUsername(), user);

        System.out.println(user);

        Assert.assertEquals("20", ((User) redisTemplate.opsForValue().get("超人")).getId());
        Assert.assertEquals("30", ((User) redisTemplate.opsForValue().get("蝙蝠侠")).getId());
        Assert.assertEquals("40", ((User) redisTemplate.opsForValue().get("蜘蛛侠")).getId());


    }

    @Test
    public void whenSuccessTest() {
        redisTemplate.opsForValue().set("a", 1);
    }
}
