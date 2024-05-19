package org.dubbo.distribute.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 用户登陆请求
 * @author: muqingfeng
 * @date: 2024/5/7 22:32
 */
@Data
@Schema(name = "UserRegisterDto", description = "用户表单注册实体")
public class UserLoginRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private Boolean rememberMe;
}
