package org.dubbo.distribute.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dubbo.distribute.domain.pojo.AuthRole;
import org.dubbo.distribute.domain.pojo.AuthUserRole;

@Mapper
public interface AuthUserRoleMapper extends BaseMapper<AuthUserRole> {
}
