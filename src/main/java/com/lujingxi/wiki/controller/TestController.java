package com.lujingxi.wiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${test.hello}")
    private String testHello;

    /**
     * GET,POST,PUT,DELETE
     * @return string
     */
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World"+testHello;
    }

    @PostMapping("/hello/post")
    public String helloPost(String name) {
        return "hello,world,post" + name;
    }
}
