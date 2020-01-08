package com.vrv.vap.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: liujinhui
 * @Date: 2019/8/3 22:06
 */
@Data
public class User implements Serializable {

    public User(String username, String id) {
        this.username = username;
        this.id = id;
    }

    public User() {
    }

    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }

    @NotBlank(message = "id不能为空!!")
    private String id;

    @NotBlank(message = "用户名不能为空!!")
    private String username;

    private String password;

    private Date birthday;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

}

