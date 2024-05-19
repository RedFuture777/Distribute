package org.dubbo.distribute.constant;

import lombok.Getter;

/**
 * @description: 基本角色信息
 * @author: muqingfeng
 * @date: 2024/5/7 22:02
 */
@Getter
public enum RoleType {
    MANAGE(1, "管理员"),
    USER(2, "系统用户"),
    PEOPLE(3, "普通用户");

    private final int id;
    private final String name;


    RoleType(int id , String name){
        this.id = id;
        this.name = name;
    }

}
