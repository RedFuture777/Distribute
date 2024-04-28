package org.dubbo.distribute.domain.pojo;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("auth_user_role")
@ApiModel(value = "用户角色映射对象", description = "用户角色关联表")
public class AuthUserRole {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("updated_time")
    private Timestamp updateTime;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "角色ID")
    @TableField("role_id")
    private Integer roleId;

    @TableLogic
    private Boolean isDel;
}
