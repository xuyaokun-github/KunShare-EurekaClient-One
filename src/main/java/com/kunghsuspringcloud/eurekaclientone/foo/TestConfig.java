package com.kunghsuspringcloud.eurekaclientone.foo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public A a(){
        return new A();
    }
}
