package com.vrv.vap.consumer.controller;

import com.vrv.vap.consumer.model.User;
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
 * 2020/1/8 13:39
 *
 * @author wh1107066
 */
@Api(tags = "Cache管理")
@RestController
@RequestMapping("/redis")
public class CacheController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Query", notes = "查询")
    public Object query(@PathVariable String id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        String s = consumerService.cacheQax(id);
        logger.info("查询:" + format + " :Cacheable create.  query id is " + id + ", result:" + s);
        return s;
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete", notes = "删除")
    public String delete(@PathVariable Long id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info(sdf.format(new Date()) + " : Cacheable delete,  query id is " + id);
        logger.info("----------->id:" + id);
        final String delete = consumerService.delete(id);
        return "aaa";
    }


    @PutMapping
    @ApiOperation(value = "Update", notes = "更新")
    public String update(@RequestBody User user) {
        logger.info("更新:");
        String info = consumerService.update(1111L);
        return info;
    }


    @PostMapping
    @ApiOperation(value = "Create", notes = "新增")
    public String create(@RequestBody User user) {
        logger.info("创建:");
        return null;
    }

}
