package org.dubbo.distribute.service;

import org.dubbo.distribute.domain.dto.UserRegisterDto;
import org.dubbo.distribute.domain.pojo.AuthUser;

/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/4/29 21:50
 */

public interface UserService {
    String signUp(UserRegisterDto userRegisterDto);

    AuthUser find(String username);
}
