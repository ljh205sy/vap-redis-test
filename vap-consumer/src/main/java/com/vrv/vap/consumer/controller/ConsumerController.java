package com.vrv.vap.consumer.controller;

import com.vrv.vap.consumer.service.ConsumerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: liujinhui
 * @Date: 2020/1/6 16:31
 */
@RestController
@RequestMapping("/v1")
@Api(tags = "消费管理")
public class ConsumerController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/consumer")
    @ApiOperation(value = "consumer查询", notes = "查询")
    public String consumer() {
        logger.info("222");
        return "1";
    }

    /**
     * redis中缓存的时间为3600秒
     * redis中的键值为 cache:qax:aaa:2222
     *
     * @param str
     * @return
     */
    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    @ApiOperation(value = "consumer测试que队列", notes = "打印")
    public String consumer(@PathVariable String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        logger.info("Try to que get value， 时间：" + format);
        String s = consumerService.cacheQax(str);
        return s + ", " + format;
    }


    /**
     * redis中缓存时间为300秒
     * redis中的键值为 cache:menu:bbb:1111
     *
     * @param str
     * @return
     */
    @RequestMapping(value = "/menu/{str}", method = RequestMethod.GET)
    @ApiOperation(value = "consumer测试menu队列", notes = "打印")
    public String menu(@PathVariable String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        logger.info("Try to menu get value， 时间：" + format);
        String s = consumerService.cacheMenu(str);
        return s + ", " + format;
    }


    @RequestMapping(value = "/other/{str}", method = RequestMethod.GET)
    @ApiOperation(value = "consumer测试other队列", notes = "打印")
    public String other(@PathVariable String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        logger.info("Try to other get value， 时间：" + format);
        String s = consumerService.cacheOther(str);
        return s + ", " + format;
    }

    @RequestMapping(value = "/test/{str}", method = RequestMethod.GET)
    @ApiOperation(value = "consumer测试test", notes = "打印")
    public String test(@PathVariable String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        logger.info("Try to test get value， 时间：" + format);
        return format;
    }



    @RequestMapping(value = "/redis/{str}", method = RequestMethod.GET)
    @ApiOperation(value = "consumer测试线程redis队列", notes = "redis队列")
    public String redis(@PathVariable String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        logger.info("Try to redis get value， 时间：" + format);
        consumerService.duplicateProcess(str);
        return format;
    }

}
