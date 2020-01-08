package com.vrv.vap.exception;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @Author: liujinhui
 * @Date: 2019/9/16 10:22
 */
@Data
public class UserException extends RuntimeException {
    private List<FieldError> fieldErrors;

    public UserException(List<FieldError> fieldErrors) {
        super("user not exist");
        this.fieldErrors = fieldErrors;
    }

    public UserException() {
        super("user not exist");
    }
}
