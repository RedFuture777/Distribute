package org.dubbo.distribute.exception;


import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 基本异常
 * @author: muqingfeng
 * @date: 2024/4/29 22:40
 */
abstract public class BaseException extends RuntimeException{

    private final UserExceptionCode exceptionCode;

    private final transient HashMap<String, Object> data = new HashMap<>();

    BaseException(UserExceptionCode exceptionCode, Map<String, Object> data){
        this.exceptionCode = exceptionCode;
        if(!ObjectUtils.isEmpty(data)){
            this.data.putAll(data);
        }
    }

    public UserExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public HashMap<String, Object> getData() {
        return data;
    }
}
