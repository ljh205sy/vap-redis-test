package com.vrv.vap;

import com.vrv.vap.model.User;
import org.junit.Assert;
import org.junit.Before;
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
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheDemoApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username", "jojo")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }

    @Test
    public void whenQueryParamSuccess() throws Exception {
        String result = mockMvc.perform(
                MockMvcRequestBuilders.get("/user").param("username", "jojo").param("age", "18").param("ageTo", "60").param("xxx", "yyy")
                        // .param("size", "15")
                        // .param("page", "3")
                        // .param("sort", "age,desc")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();

        System.out.println(result);
    }

    @Test
    public void whenQueryByName() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/username")
                .param("username", "jojo")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));

    }

    @Test
    public void whenCreateSuccess() throws Exception {

        Date date = new Date();
        System.out.println(date.getTime());
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":" + date.getTime() + "}";
        String reuslt = mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(reuslt);
    }

    @Test
    public void whenCreateFail() throws Exception {

        Date date = new Date();
        System.out.println(date.getTime());
        String content = "{\"username\":\"tom\",\"password\":null,\"birthday\":" + date.getTime() + "}";
        String reuslt = mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(reuslt);
    }

    @Test
    public void whenUpdateSuccess() throws Exception {

        Date date = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(date.getTime());
        String content = "{\"id\":\"1\", \"username\":\"tom\",\"password\":null,\"birthday\":" + date.getTime() + "}";
        String reuslt = mockMvc.perform(MockMvcRequestBuilders.put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn().getResponse().getContentAsString();

        System.out.println(reuslt);
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void putValue() {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set("key0", "我是value0");
        System.out.println("11111111111");
        Assert.assertEquals("我是value0", stringRedisTemplate.opsForValue().get("key0"));

    }


    @Test
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


    @Test
    public void whenSaveSuccessString() {
        stringRedisTemplate.opsForValue().set("ccc", "我是11122aaa");
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
