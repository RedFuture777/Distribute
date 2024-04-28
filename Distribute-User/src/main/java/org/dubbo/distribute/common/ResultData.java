package org.dubbo.distribute.common;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ResultData", description = "统一返回体")
public class ResultData<T> {
    private HttpStatus status;

    private String message;

    private T data;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public ResultData(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static <T> ResultData<T> success(T t) {
        ResultData<T> resp = new ResultData<>();
        resp.setData(t);
        resp.setTimestamp(new Timestamp(System.currentTimeMillis()));
        resp.setStatus(HttpStatus.OK);
        return resp;
    }

    public static <T> ResultData<T> fail(HttpStatus status, String message) {
        ResultData<T> resp = new ResultData<>();
        resp.setTimestamp(new Timestamp(System.currentTimeMillis()));
        resp.setStatus(status);
        resp.setMessage(message);
        return resp;
    }


}
