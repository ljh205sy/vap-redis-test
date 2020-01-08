package com.vrv.vap.consumer.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.vrv.vap.consumer.service.ConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 2020/1/7 14:47
 *
 * @author wh1107066
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate<?, ?> redisTemplate;
    /**
     * 阿里开发规范中，使用Executors中的FixedThreadPool会导致oom，推荐使用guava提供的ThreadFactoryBuilder来创建线程池
     * 【强制】线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险
     * https://blog.csdn.net/yan88888888888888888/article/details/83927609
     */
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    private static ExecutorService pool = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());


    /**
     * unless： 如果返回的结果为空，则标识不缓存
     * redis存储的类型为zset，存储的值为返回的值
     *
     * @param key， key为在config中定义的cachename的值，否则就不能自定义缓存失效时间
     * @return String
     * <p>
     * TODO 一定要与 RedisAutoConfiguration 类中 private RedisCacheConfiguration getDefConf() 定义的键值一致， 不需要value值一样，只是需要key一样即可
     * 如果发送的请求是/v1/echo/1111 , 在redis中的key为  cache::qax:aaa:1111
     * RedisAutoConfiguration.getDefConf() 定义了  .computePrefixWith(cacheName -> "cache".concat(":").concat(cacheName).concat(":"))
     * "cache" 为前缀,所以必须在注解@Cacheable，设置的key要相同，否则不会重新设置失效时间，采用默认的时间
     */
    @Override
    @Cacheable(value = "qax", key = "'aaa:'+#key", unless = "#result==null")
    public String cacheQax(String key) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        logger.info("进入缓存队列，cacheable create. key为cache::qax:aaa:" + key);
        logger.info(format);
        String object = restTemplate.getForObject("http://vap-producer/v1/echo/" + key, String.class);
        return object;
    }

    /**
     * 自定义时间为300秒
     */
    @Override
    @Cacheable(value = "menu", key = "'bbb:'+#key", unless = "#result==null")
    public String cacheMenu(String key) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        logger.info(" 进入缓存队列，cacheable create. key:menu" + ", " + format);
        String object = restTemplate.getForObject("http://vap-producer/v1/echo/" + key, String.class);
        return object;
    }


    @Override
    @Cacheable(value = "other", key = "'bbb:'+#key", unless = "#result==null")
    public String cacheOther(String key) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        logger.info(" 进入缓存队列，cacheable create. key:other" + ", " + format);
        String object = restTemplate.getForObject("http://vap-producer/v1/echo/" + key, String.class);
        return object;
    }


    /**
     * 删除缓存失效
     */
    @Override
    @CacheEvict(value = "qax", key = "'aaa:'+#key")
    public String delete(@PathVariable Long key) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info(sdf.format(new Date()) + " : Cacheable delete,  query id is " + key);
        logger.info("----------->id:" + key);
        return null;
    }

    /**
     * 更新
     */
    @Override
    @CachePut(value = "qax", key = "'aaa:'+#key", unless = "#result==null")
    public String update(Long key) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info(sdf.format(new Date()) + " update,  query id is " + key);
        String info = "xxx";
        return info;
    }


    @Override
    public void duplicateProcess(String key) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            pool.execute(new SubThread(redisTemplate));
        }
    }


     /*   @CachePut(value = "qax", key = "'aaa:'+#user.id")
    public User update(@RequestBody User user) {
        User s = new User();
        s.setId(user.getId());
        s.setUsername(user.getUsername());
        logger.info("更新:" + s.toString());
        return s;
    }*/


}

class SubThread implements Runnable {
    private Logger logger = LoggerFactory.getLogger(SubThread.class);
    RedisTemplate redisTemplate;

    public SubThread(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run() {
        Object o = redisTemplate.opsForValue().get("cache:qax:aaa:1111");
        logger.info("从缓冲中获取数据， o:" + o);
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            //do nothing
//            logger.info(" 进入缓存队列，cacheable create. key为cache::qax:aaa:2222" );
//        }
    }
}
