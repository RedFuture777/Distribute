package org.dubbo.distribute.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dubbo.distribute.domain.pojo.AuthUser;

@Mapper
public interface AuthUserMapper extends BaseMapper<AuthUser> {
}
