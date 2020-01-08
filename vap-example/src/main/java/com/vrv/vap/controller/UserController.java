package com.vrv.vap.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonView;
import com.vrv.vap.exception.UserException;
import com.vrv.vap.model.User;
import com.vrv.vap.service.UserService;
import com.vrv.vap.service.UserService;
import com.vrv.vap.vo.UserVO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liujinhui
 * @Date: 2019/8/5 7:47
 */
@RestController

public class UserController {
    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    @RequestMapping("/user")
    public List<User> query(@RequestParam String username) {
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }

    @PostMapping("/userVO")
    public List<UserVO> queryUser(@Valid @RequestBody UserVO userVO, BindingResult bindingResult) {
        if (logger.isDebugEnabled()) {
            logger.debug("createUser,插入实时数据错误， 入参User:{}", JSON.toJSONString(userVO));
        }
        if (bindingResult.getErrorCount() > 0) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError errorField : fieldErrors) {
                String defaultMessage = errorField.getDefaultMessage();
                String field = errorField.getField();
                logger.error("Controller验证对象属性：field:{}, message:{}", field, defaultMessage);
            }
            throw new UserException(fieldErrors);
        }

        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        logger.info(" BeanUtils.copyProperties(userVO, user)的值 => {}", ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
        logger.info("---------------------------------------------------------------------");
        List<UserVO> list = new ArrayList<>();
        list.add(new UserVO());
        list.add(new UserVO());
        list.add(new UserVO());
        return list;
    }

    @GetMapping("/me1")
    public Object getUsers(Authentication authentication) {
        return authentication;
    }

    @GetMapping("/username")
    // name指定参数的名字
    public List<User> getList(@RequestParam(name = "username") String nickname) {
        List<User> list = new ArrayList<>();
        System.out.println(nickname);
        list.add(new User());
        list.add(new User());
        list.add(new User());
        return list;
    }

//    // 自动组装成对象
//    @GetMapping(value = "/queryByCondition")
//    @JsonView(User.UserSimpleView.class)
//    public List<User> queryUserByCondition(UserQueryCondition condition,
//                                           @PageableDefault(page = 2, size = 10, sort = "username, asc") Pageable page) {
//        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
//        System.out.println(page.getPageSize());
//        System.out.println(page.getPageNumber());
//        System.out.println(page.getSort());
//
//        List<User> list = new ArrayList<>();
//        list.add(newUser());
//        list.add(newUser());
//        list.add(newUser());
//        return list;
//    }

    @JsonView(User.UserDetailView.class)
    @GetMapping(value = "/user/{id:\\d+}")
    public User queryUserById(@PathVariable String id) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("xss filter is open");
        }

        logger.info("进入queryUserById方法,调用实际的方式进行数据获取======");
        logger.error("-------------------");
        User user = new User();
        user.setId(id);
        user.setId("tom1");
        user.setPassword("123");
        return user;

//		throw newUserNotExistException(id);

    }

    /**
     * 如果有配置BindingResult参数，则进入到方法内部
     * 如果没配置BindingResult,则不会进入到方法体，直接返回400错误状态码
     * 如果有问题，直接返回视图，提供页面格式输出
     *
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/user")
    public User createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (logger.isDebugEnabled()) {
            logger.debug("createUser,插入实时数据错误， 入参User:{}", JSON.toJSONString(user));
        }
        if (bindingResult.getErrorCount() > 0) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError errorField : fieldErrors) {
                String defaultMessage = errorField.getDefaultMessage();
                System.out.println("此处的错误消息：" + defaultMessage);
            }
        }

        System.out.println("----------------------------------------------------------------");
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        System.out.println(user.getPassword());
        System.out.println(user.getUsername());
        user.setId("1");
        return user;
    }

    @PutMapping("/user/{id:\\d}")
    public User updateUser(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        return user;
    }

    @DeleteMapping("/user/{id:\\d}")
    public String deleteUser(@PathVariable String id) {
        logger.info("delete user:id:" + id);
        return id;
    }

    @PostMapping(value = "/usertest")
    public User createUserTest(@Valid @RequestBody User user) {
        System.out.println(user.getPassword());
        System.out.println(user.getUsername());
        user.setId("1");
        return user;
    }


}
