package org.dubbo.distribute.domain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("auth_permit")
@ApiModel(value = "权限对象", description = "角色权限表")
public class AuthPermit {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @TableField("created_time")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("updated_time")
    private Timestamp updateTime;

    @ApiModelProperty(value = "父菜单ID")
    @TableField("pid")
    private Integer pid;

    @ApiModelProperty(value = "权限名称")
    @TableField("name")
    private String permitName;

    @ApiModelProperty(value = "授权路径")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @TableLogic
    private Boolean isDel;
}
