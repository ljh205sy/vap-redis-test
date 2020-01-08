package com.vrv.vap.producer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: liujinhui
 * @Date: 2020/1/6 16:35
 */
@RestController
@RequestMapping("/v1")
@Api(tags = "需求管理")
public class ProducerController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/producer")
    @ApiOperation(value = "producer查询", notes = "查询")
    public String hello() {
        logger.info("111");
        return "1";
    }

    @RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
    @ApiOperation(value = "producer打印", notes = "打印")
    public String echo(@PathVariable String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        logger.info("producer value， time：" + format);
        return "Hello Nacos Discovery " + string;
    }
}

