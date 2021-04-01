package com.kunghsuspringcloud.eurekaclientone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xuyaokun On 2020/7/5 22:57
 * @desc:
 */
@Service
public class HystrixDemoService {

    @Autowired
    private RestTemplate restTemplate;

//    @HystrixCommand(fallbackMethod = "errorCallBack")
    public String method1() {
        String result = restTemplate.getForObject("http://eureka-client-two/two/testHello2", String.class);
        System.out.println(result);
        return result;
    }

    /**
     * 降级方法
     * （注意这里的方法签名必须和@HystrixCommand注解修饰的方法签名一致）
     * @return
     */
    public String errorCallBack() {
        return "sorry 触发服务降级";
    }


}
