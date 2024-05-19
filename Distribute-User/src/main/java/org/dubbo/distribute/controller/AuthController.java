package org.dubbo.distribute.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.dubbo.distribute.constant.ResultWrapper;
import org.dubbo.distribute.constant.SecurityConstants;
import org.dubbo.distribute.domain.dto.UserLoginRequestDto;
import org.dubbo.distribute.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Api(tags = "认证")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public ResponseEntity<Void> login(@RequestBody UserLoginRequestDto requestDto){
        String token = authService.createToken(requestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, token);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

}
