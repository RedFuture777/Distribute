package org.dubbo.distribute.controller;

import org.dubbo.distribute.constant.CaffeineCacheConstants;
import org.dubbo.distribute.mapper.AuthUserMapper;
import org.dubbo.distribute.service.impl.CaffeineAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: some desc
 * @author: muqingfeng
 * @date: 2024/5/19 18:22
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CaffeineAsyncService caffeineAsyncService;

    @Autowired
    private AuthUserMapper authUserMapper;


    @GetMapping("/setAsyncCache")
    public void setAsyncCache(String key) {
        caffeineAsyncService.setCache(CaffeineCacheConstants.cacheKeyPrefix + CaffeineCacheConstants.prefixOfUser + key, "123");
    }

    @GetMapping("/getAsyncCache")
    public Object getAsyncCache(String key) {
        return caffeineAsyncService.getCache(key);
    }

    @GetMapping("/getUserInfo")
    public Object getUserInfo(String key) {
        return authUserMapper.findByUserName(key);
    }
}
