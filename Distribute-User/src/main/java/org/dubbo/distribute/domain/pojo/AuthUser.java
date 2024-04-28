package org.dubbo.distribute.domain.pojo;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("auth_user")
@ApiModel(value = "用户对象", description = "用户信息表")
public class AuthUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("updated_time")
    private Timestamp updateTime;

    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String username;

    @ApiModelProperty(value = "实际名称")
    @TableField("full_name")
    private String fullName;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "是否启用, 默认为1, 代表启用")
    @TableField("enabled")
    private Boolean enabled;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @TableLogic
    private Boolean isDel;


}
