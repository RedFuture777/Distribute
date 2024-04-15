package org.dubbo.distribute.remote.Impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.dubbo.distribute.remote.HelloService;

/**
 * @description: 对外提供远程服务实现
 * @author: muqingfeng
 * @date: 2024/4/13 14:48
 */
@DubboService
public class HelloServiceImpl implements HelloService {


    @Override
    public String sayHello(String name) {
        System.out.println("---谁在提供服务");
        return "hello " + name;
    }


}
