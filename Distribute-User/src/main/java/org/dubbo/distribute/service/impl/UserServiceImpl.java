package org.dubbo.distribute.service.impl;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.dubbo.distribute.constant.RoleType;
import org.dubbo.distribute.domain.dto.UserRegisterDto;
import org.dubbo.distribute.domain.pojo.AuthUser;
import org.dubbo.distribute.domain.pojo.AuthUserRole;
import org.dubbo.distribute.exception.UserNameAlreadyExistException;
import org.dubbo.distribute.mapper.AuthUserMapper;
import org.dubbo.distribute.mapper.AuthUserRoleMapper;
import org.dubbo.distribute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/4/29 21:51
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private static final String USERNAME = "username: ";

    private final AuthUserMapper authUserMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthUserRoleMapper authUserRoleMapper;

    @Override
    @Transactional
    public String signUp(UserRegisterDto userRegisterDto) {
        //解析用户信息，并将用户信息存入数据库中
        ensureUserNameNotExist(userRegisterDto.getUsername());
        //获取用户信息，构建AuthUser对象
        AuthUser authUser = new AuthUser();
        authUser.setUsername(userRegisterDto.getUsername());
        authUser.setPassword(bCryptPasswordEncoder.encode(userRegisterDto.getPassword()));
        authUser.setFullName(userRegisterDto.getFullName());
        authUserMapper.insert(authUser);
        Optional<AuthUser> user = authUserMapper.findByUserName(userRegisterDto.getUsername());
        if(user.isPresent()){
            //给新增用户绑定用户角色
            AuthUserRole userRole = new AuthUserRole();
            userRole.setUserId(user.get().getId());
            userRole.setRoleId(RoleType.USER.getId());
            authUserRoleMapper.insert(userRole);
        }else{
            return "fail";
        }
        return "success";
    }

    @Override
    public AuthUser find(String username) {
        return new AuthUser();
//        return authUserMapper.findByUserName(username).orElseThrow(() -> new UserNameNotFoundException(ImmutableMap.of(USERNAME, username)));
    }


    /**
     * 确保用户名不存在
     * @param username 用户名
     */
    private void ensureUserNameNotExist(String username){
//        LambdaQueryWrapper<AuthUser> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(AuthUser::getUsername, username);
//        List<AuthUser> authUsers = authUserMapper.selectList(queryWrapper);
//        return authUsers != null && authUsers.size() <= 1;
        boolean exist = authUserMapper.findByUserName(username).isPresent();
        if(exist){
            //TODO
            throw new UserNameAlreadyExistException(ImmutableMap.of(USERNAME, username));
        }
    }
}
