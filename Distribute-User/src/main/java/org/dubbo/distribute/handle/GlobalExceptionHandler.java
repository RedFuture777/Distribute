package org.dubbo.distribute.handle;

/**
 * @description: 全局异常处理
 * @author: muqingfeng
 * @date: 2024/4/29 22:26
 */

import org.dubbo.distribute.exception.BaseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestControllerAdvice
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public String handleBaseException(BaseException e) {
        return null;
    }

}
