package com.vrv.vap;

import com.vrv.vap.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void whenSaveSuccessRedis() {
        for (int i = 0; i < 5; i++) {
            Thread thread = new RedisThread(stringRedisTemplate);
            thread.setName("线程：" + i);
            thread.start();
            System.out.println("完成");
        }
    }

    @Test
    public void defaultTestRedis() {
        // 保存字符串
        stringRedisTemplate.opsForValue().set("bbb", "我是11122aaa");

        Object xxx = stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                StringRedisConnection stringRedisConn = (StringRedisConnection) redisConnection;
                String bbb = stringRedisConn.get("bbb");
                return bbb;
            }
        });
        System.out.println(String.format("xxx: {}", xxx));


        Assert.assertEquals("我是11122aaa", stringRedisTemplate.opsForValue().get("bbb"));
        // 保存对象
        User user = new User("超人1", "31");    // 默认是永远不失效，值为-1
        redisTemplate.opsForValue().set(user.getUsername(), user);

        user = new User("超人2", "50");
        redisTemplate.opsForValue().set(user.getUsername(), user, 20, TimeUnit.SECONDS);  // 20秒后失效

        Assert.assertEquals("31", ((User) redisTemplate.opsForValue().get("超人1")).getId());
        Assert.assertEquals("50", ((User) redisTemplate.opsForValue().get("超人2")).getId());

        System.out.println(user);
    }

    @Test
    public void whenCreateRedis() {

        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "我是11122aaa");
        Assert.assertEquals("我是11122aaa", stringRedisTemplate.opsForValue().get("aaa"));

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
}
