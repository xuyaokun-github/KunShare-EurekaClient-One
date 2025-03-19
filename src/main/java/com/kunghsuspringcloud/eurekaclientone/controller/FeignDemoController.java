package com.kunghsuspringcloud.eurekaclientone.controller;


import com.kunghsuspringcloud.eurekaclientone.bean.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/feign")
@RestController
public class FeignDemoController {

    @RequestMapping("/v1/result")
    public ResultVo testHello(){
        return ResultVo.valueOfSuccess("I am FeignDemoController from One");
    }


}
