package org.dubbo.distribute.exception;

/**
 * @description: 异常枚举
 * @author: muqingfeng
 * @date: 2024/4/29 22:29
 */
public enum UserExceptionCode implements ExceptionCode{

    UNAUTHORIZED(401, "未认证"),
    NO_PERMIT(403, "没有权限"),
    SERVER_ERROR(500, "服务错误"),
    USER_NOT_EXIST(1002, "用户不存在"),
    USER_NAME_ALREADY_EXIST(1001,"用户已存在")

    ;
    private final int code;
    private final String message;

    UserExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getCode() {
        return this.code;
    }
}
