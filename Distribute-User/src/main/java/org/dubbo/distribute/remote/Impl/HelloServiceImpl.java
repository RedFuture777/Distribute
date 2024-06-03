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

    public String allString(int n){
        String result;
        switch (n) {
            case 2:
                result = "abc";
                break;
            case 3:
                result = "def";
                break;
            case 4:
                result = "ghi";
                break;
            case 5:
                result = "jkl";
                break;
            case 6:
                result = "mno";
                break;
            case 7:
                result = "pqrs";
                break;
            case 8:
                result = "tuv";
                break;
            default:
                result = "wxyz";
        }
        return result;
    }


}
