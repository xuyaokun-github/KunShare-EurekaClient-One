package com.kunghsuspringcloud.eurekaclientone.controller;


import com.kunghsuspringcloud.eurekaclientone.bean.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuyaokun On 2020/10/14 22:56
 * @desc: 
 */
@RequestMapping("/spring-redis")
@RestController
public class SpringRedisDemoController {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringRedisDemoController.class);

    private final String HASH_KEY = "kunghsu.hash";

    private final String HASH_KEY_PREFIX = "kunghsu.hash.prefix_";

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value = "/testString", method = RequestMethod.GET)
    public ResultVo testString(HttpServletRequest request){

//        User user = new User();
//        user.setUsername("kunghsu");
//        redisTemplateHelper.set("spring-redis-demo-testString", user, 6000);
//
//        Object res = redisTemplateHelper.get("spring-redis-demo-testString");

        //下面的代码在低版本jedis包中会有问题，不能存太大的值
        // (这个问题已经在高版本修复)
        long time = 2147483647999999L;
        LOGGER.info("long值：{}", time);
        redisTemplate.opsForValue().set("spring-redis-demo-testString", "123456", time, TimeUnit.MILLISECONDS);
//        redisTemplate.opsForValue().set("spring-redis-demo-testString", "123456", time/1000, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set("spring-redis-demo-testString", "123456", (time/1000)/60, TimeUnit.MINUTES);

        return ResultVo.valueOfSuccess();
    }


}
