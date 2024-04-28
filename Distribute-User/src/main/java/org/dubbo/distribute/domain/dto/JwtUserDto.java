package org.dubbo.distribute.domain.dto;

import lombok.Data;
import org.dubbo.distribute.domain.pojo.AuthRole;
import org.dubbo.distribute.domain.pojo.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class JwtUserDto implements UserDetails {

    /**
     * UserDetails 接口是 spring security 安全框架中的 用于存储用户信息及权限的
     */

    private AuthUser authUser;

    private List<AuthRole> authRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
