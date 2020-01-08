package com.vrv.vap.controller;

import com.vrv.vap.RedisThread;
import com.vrv.vap.model.User;
import com.vrv.vap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: liujinhui
 * @Date: 2019/8/19 19:27
 */
@RequestMapping("/redis")
@RestController
public class RedisUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/userById/{id}")
    public User getUserFromRedisById(@PathVariable String id) {
        User userFromRedisById = userService.getUserFromRedisByID("user::" + id);
        return userFromRedisById;
    }

    @DeleteMapping("/user/{id}")
    public void deleteRedisUser(@PathVariable String id) {
        // 删除用户，同时把缓存中的数据删除
        userService.deleteUser(id);
    }


    @PostMapping("/user")
    public User saveRedisUser(@RequestBody User user) {
        userService.saveUser(user);
        return null;
    }

    @GetMapping("/user/{id}")
    public User getRedisUser(@PathVariable String id) {
        User user = userService.getUser(String.valueOf(id));
        return user;
    }

    @RequestMapping
    public void testRedis(Model model, HttpServletRequest request) {
        System.out.println("\n---------");
        for (int i = 0; i < 5; i++) {
            Thread thread = new RedisThread(stringRedisTemplate);
            thread.setName("线程：" + i);
            thread.start();
        }
        model.addAttribute("status", "完成" + System.currentTimeMillis());
        System.out.println("完成");
        System.out.println("\n");
    }

    @RequestMapping("/test")
    public void test() {
        stringRedisTemplate.opsForValue().set("ccc", "我是11122aaa");
    }

    @RequestMapping("/test1")
    public void whenSaveSuccessRedis() {
        Object xxx = stringRedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                StringRedisConnection stringRedisConn = (StringRedisConnection) redisConnection;
                Boolean b = stringRedisConn.set("bbb", "bbbb");
                return b;
            }
        });
        System.out.println(String.format("xxx: {}", xxx));
    }
}

