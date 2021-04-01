package com.kunghsuspringcloud.eurekaclientone.controller;


import com.kunghsuspringcloud.eurekaclientone.service.HystrixDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hystrix")
@RestController
public class HystrixDemoController {

    @Autowired
    HystrixDemoService hystrixDemoService;

    @RequestMapping("/test1")
    public String testHello(){

        String res = hystrixDemoService.method1();
        System.out.println(res);
        return "kunghsu testHello";
    }

}
