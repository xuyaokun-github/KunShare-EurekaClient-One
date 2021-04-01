package com.kunghsuspringcloud.eurekaclientone.controller;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@RequestMapping("/one")
@RestController
public class HelloController implements ApplicationContextAware {

    private ApplicationContext context;


    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init(){
        Map<String, RestTemplate> beanMap = context.getBeansOfType(RestTemplate.class);
        if (beanMap != null){
        System.out.println(beanMap.size());
            beanMap.forEach((k,v)->{
                System.out.println("===============beanMap:" + k);
            });
        }

    }

    @RequestMapping("/test")
    public String test(){

        System.out.println("进入test方法" + System.currentTimeMillis());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "kunghsu testHello";
    }

    @RequestMapping("/testHello")
    public String testHello(){

        String result = restTemplate.getForObject("http://eureka-client-two/two/testHello", String.class);
        System.out.println(result);
        return "kunghsu testHello";
    }

    /**
     * 用负载均衡
     * @return
     */
    @RequestMapping("/testBatch")
    public String testHello2(){

        invokeBatch("http://eureka-client-two/two/testHello");

        return "kunghsu testHello2";
    }

    /**
     * 不用负载均衡
     * @return
     */
    @RequestMapping("/testBatch2")
    public String testBatch2(){

        invokeBatch("http://127.0.0.1:8082/two/testHello");

        return "kunghsu testHello2";
    }


    private void invokeBatch(String url){
        new Thread(()->{
            long start = System.currentTimeMillis();
            int count = 1000;
            CountDownLatch countDownLatch = new CountDownLatch(count);
            for (int i = 0; i < count; i++) {
                new Thread(()->{
                    String result = restTemplate.getForObject(url, String.class);
                    System.out.println(result);
                    countDownLatch.countDown();
                }).start();
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("耗时：" + (System.currentTimeMillis() - start));
        }).start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }



}
