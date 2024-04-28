package org.dubbo.distribute.domain.pojo;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("auth_role")
@ApiModel(value = "角色对象", description = "用户角色表")
public class AuthRole {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("updated_time")
    private Timestamp updateTime;

    @ApiModelProperty(value = "角色名称")
    @TableField("name")
    private String roleName;

    @ApiModelProperty(value = "标记名称")
    @TableField("mark_name")
    private String markName;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @TableLogic
    private Boolean isDel;
}
