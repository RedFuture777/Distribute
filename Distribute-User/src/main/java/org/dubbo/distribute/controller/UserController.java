package org.dubbo.distribute.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dubbo.distribute.common.ResultData;
import org.dubbo.distribute.constant.ResultWrapper;
import org.dubbo.distribute.domain.dto.UserRegisterDto;
import org.dubbo.distribute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Api(tags = "用户")
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/sign-up")
    @ApiOperation("用户注册")
    public ResultWrapper<String> signUp(@RequestBody @Valid UserRegisterDto userRegisterDto){
        return ResultWrapper.success(userService.signUp(userRegisterDto));
    }
}
