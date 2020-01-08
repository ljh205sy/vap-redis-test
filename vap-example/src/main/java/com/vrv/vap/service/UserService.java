package com.vrv.vap.service;

import com.vrv.vap.model.User;

/**
 * @Author: liujinhui
 * @Date: 2019/8/4 22:38
 */
public interface UserService {
    /**
     * 数据查询缓存
     *
     * @return
     */
    User getUser(String id);

    void deleteUser(String id);

    User updateUser(User user);

    User saveUser(User user);

    User getUserFromRedisByID(String id);
}
