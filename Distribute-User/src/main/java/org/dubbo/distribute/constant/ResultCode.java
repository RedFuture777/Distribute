package org.dubbo.distribute.constant;

import lombok.Getter;

@Getter
public enum ResultCode {
    SUCCESS(200, "调用成功"),
    SYSTEM_FAILURE(200, "调用成功"),
    ;

    private final int code;
    private final String description;

    ResultCode(int code, String description){
        this.code = code;
        this.description = description;
    }
}
