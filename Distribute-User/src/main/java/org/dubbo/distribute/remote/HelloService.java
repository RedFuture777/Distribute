package org.dubbo.distribute.remote;

/**
 * @description: 对外提供远程服务接口
 * @author: muqingfeng
 * @date: 2024/4/13 14:45
 */


public interface HelloService {
    default String sayBye(){
        return "byebye";
    }

    String sayHello(String name);
}
