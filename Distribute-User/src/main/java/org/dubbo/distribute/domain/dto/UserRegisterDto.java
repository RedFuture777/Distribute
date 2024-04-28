package org.dubbo.distribute.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(name = "UserRegisterDto", description = "用户表单登陆实体")
public class UserRegisterDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String fullName;

}
