package com.ducetech.api.remote.impl;

import com.ducetech.api.remote.HelloService;

/**
 * Created by lenzhao on 17-2-15.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
