package com.vrv.vap.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: liujinhui
 * @Date: 2019/9/11 13:56
 */
@Data
public class UserVO {

    @NotBlank(message = "用户名不能为空!!")
    private String username;

    private String password;


}
