package org.dubbo.distribute.remote;

/**
 * @description: 调用远程提供的服务
 * 服务消费方：业务层接口需要和服务提供方接口名称保证一致，同时该接口抽象方法也要保持一致
 * 在服务消费方，不需要去实现这个接口，消费方接口实现是依赖提供方来完成的
 * @author: muqingfeng
 * @date: 2024/4/13 14:54
 */
public interface HelloService {
    String sayHello(String name);
}
