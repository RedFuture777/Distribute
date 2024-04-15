package org.dubbo.distribute.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.dubbo.distribute.remote.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: Hello
 * @author: muqingfeng
 * @date: 2024/4/13 19:30
 */
@RestController
public class HelloAction {

    @DubboReference
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello(String name){
        return helloService.sayHello(name);
    }
}
