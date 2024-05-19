package org.dubbo.distribute.service.impl;

import lombok.RequiredArgsConstructor;
import org.dubbo.distribute.domain.dto.UserLoginRequestDto;
import org.dubbo.distribute.domain.pojo.AuthUser;
import org.dubbo.distribute.service.AuthService;
import org.dubbo.distribute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/5/7 22:37
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    @Override
    public String createToken(UserLoginRequestDto requestDto) {
        AuthUser authUser = userService.find(requestDto.getUsername());
        return null;
    }
}
