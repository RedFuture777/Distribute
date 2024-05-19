package org.dubbo.distribute.service;

import org.dubbo.distribute.domain.dto.UserLoginRequestDto;

/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/5/7 22:37
 */
public interface AuthService {
    String createToken(UserLoginRequestDto requestDto);
}
