package com.kunghsuspringcloud.eurekaclientone.foo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestBean {

    //根据直接父类可以注入
    @Autowired
    B a1;

    //根据间接父类，也可以注入
//    @Autowired
    C a2;

    //根据接口类型可以注入a
//    @Autowired
    D a;


    @PostConstruct
    public void init(){

        //最终输出的A的class类型
        if (a != null){
            System.out.println("a:" + a.getClass().getTypeName());
        }
    }
}
