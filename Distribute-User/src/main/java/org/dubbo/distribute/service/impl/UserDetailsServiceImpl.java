package org.dubbo.distribute.service.impl;

import org.dubbo.distribute.service.BaseUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements BaseUserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //这是 UserDetailsService 接口里的 loadUserByUsername 方法，默认是从缓存中根据用户名称，加载用户信息

        return null;
    }
}
