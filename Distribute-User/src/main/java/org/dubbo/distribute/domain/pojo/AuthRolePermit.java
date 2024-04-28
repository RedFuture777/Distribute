package org.dubbo.distribute.domain.pojo;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("auth_role_permit")
@ApiModel(value = "角色权限对象",description = "用户角色权限表")
public class AuthRolePermit {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("updated_time")
    private Timestamp updateTime;

    @ApiModelProperty(value = "角色ID")
    @TableField("role_id")
    private Integer roleId;

    @ApiModelProperty(value = "权限ID")
    @TableField("permit_id")
    private Integer permitId;

    @TableLogic
    private Boolean isDel;
}
