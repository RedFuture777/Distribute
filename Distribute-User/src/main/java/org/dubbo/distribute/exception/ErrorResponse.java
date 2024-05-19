package org.dubbo.distribute.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
public class ErrorResponse {
    private int code;
    private String message;
    private String url;
    private Instant timestamp;
    private final HashMap<String, Object> errorDetail = new HashMap<>();

    public ErrorResponse(BaseException ex, String url) {
        this(ex.getExceptionCode().getCode(), ex.getExceptionCode().getMessage(), url, ex.getData());
    }

    public ErrorResponse(ExceptionCode exceptionCode, String url) {
        this(exceptionCode.getCode(), exceptionCode.getMessage(), url, null);
    }

    public ErrorResponse(ExceptionCode exceptionCode, String url, Map<String, Object> errorDetail) {
        this(exceptionCode.getCode(), exceptionCode.getMessage(), url, errorDetail);
    }

    private ErrorResponse(int code,  String message, String url, Map<String, Object> errorDetail) {
        this.code = code;
        this.message = message;
        this.url = url;
        this.timestamp = Instant.now();
        if (!ObjectUtils.isEmpty(errorDetail)) {
            this.errorDetail.putAll(errorDetail);
        }
    }
}
