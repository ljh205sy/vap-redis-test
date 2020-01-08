package com.vrv.vap.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrv.vap.model.User;
import com.vrv.vap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: liujinhui
 * @Date: 2019/8/4 22:38
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 获取缓存数据
     *
     */

    @Override
    public User getUserFromRedisByID(String id) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String jackson = stringRedisTemplate.opsForValue().get(id);
        User user = JSON.parseObject(jackson, User.class);
        String user_json = null;
        try {
            user_json = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("jackson;" + jackson);
        System.out.println("user_json;" + user_json);
        return user;
    }

    /**
     * 用于查询的时候缓存数据,可以不用调用或者操作redisTemplate的set或者get等方法
     * 可以直接查询时，直接放入到指定的缓存
     * 如果是取缓存，可以使用Redis的Repository的方式进行获取
     * user::0
     */
    @Override
    @Cacheable(value = "user", key = "#id")
    public User getUser(String id) {
        User user = new User();
        user.setId(String.valueOf(id));
        user.setUsername("张三");
        user.setPassword("123456");
        user.setBirthday(new Date());
        return user;
    }

    /**
     * 用于对数据删除的时候清除缓存中的数据
     * @param id
     */
    @Override
    @CacheEvict(value = "user", key = "#id", condition = "#id!='1'")
    public void deleteUser(String id) {
        System.out.println(id + "进入实现类删除数据！");
    }

    /**
     * 用于对数据修改的时候修改缓存中的数据
     * @param user
     * @return
     */
    @Override
    @CachePut(value = "user",key="#user.id")
    public User updateUser(User user) {
        user.setId(user.getId());
        user.setUsername("李四");
        user.setPassword("abcdefg");
        return user;
    }

    @Override
    @CachePut(value = "user",key="#user.id")
    public User saveUser(User user) {
        String id = user.getId();
        System.out.println(id + "进入实现类获取数据！");
        user.setId(String.valueOf(id));
        user.setUsername("张三");
        user.setPassword("123456");
        user.setBirthday(new Date());
        return user;
    }
}
