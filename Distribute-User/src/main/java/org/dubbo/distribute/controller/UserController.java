package org.dubbo.distribute.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dubbo.distribute.common.ResultData;
import org.dubbo.distribute.domain.dto.UserRegisterDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(tags = "用户")
public class UserController {



    @PostMapping("/sign-up")
    @ApiOperation("用户注册")
    public ResultData<String> signUp(@RequestBody @Valid UserRegisterDto userRegisterDto){
        return ResultData.success("success");
    }
}
