package com.lujingxi.wiki.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * GET,POST,PUT,DELETE
     * @return
     */
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
