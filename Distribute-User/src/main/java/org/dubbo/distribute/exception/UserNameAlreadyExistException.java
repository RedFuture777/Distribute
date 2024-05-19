package org.dubbo.distribute.exception;

import java.util.Map;

/**
 * @description: 自定义用户不存在异常
 * @author: muqingfeng
 * @date: 2024/4/29 23:07
 */
public class UserNameAlreadyExistException extends BaseException{

    public UserNameAlreadyExistException(Map<String, Object> data) {
        super(UserExceptionCode.USER_NAME_ALREADY_EXIST, data);
    }
}
