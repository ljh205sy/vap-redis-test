package com.vrv.vap.consumer.service;

/**
 * @Author: liujinhui
 * @Date: 2020/1/6 16:32
 */
public interface ConsumerService {

    /**
     * 缓存key-value数据
     *
     * @param key
     * @return string
     */
    String cacheQax(String key);

    String cacheMenu(String key);

    String cacheOther(String key);

    void duplicateProcess(String key);

    String delete(Long key);

    String update(Long key);

}
