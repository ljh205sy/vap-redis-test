package com.vrv.vap.exception;

import com.vrv.vap.utils.ResponseData;
import com.vrv.vap.utils.ResponseDataUtil;
import com.vrv.vap.utils.ResultEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: liujinhui
 * @Date: 2019/9/16 10:24
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(UserException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData handleRoleNotExistException(UserException ex) {
        Map<String, Object> result = new HashMap<>();
        List<FieldError> fieldErrors = ex.getFieldErrors();
        for (FieldError errorField : fieldErrors) {
            String defaultMessage = errorField.getDefaultMessage();
            String field = errorField.getField();
            logger.error("@Valid验证对象，此处的错误消息：field:{}, message:{}", field, defaultMessage);
            result.put(field, defaultMessage);
        }
        String message = ex.getMessage();
        return ResponseDataUtil.buildError(ResultEnums.PARAM_ERROR.getCode(), message, result);
    }
}
