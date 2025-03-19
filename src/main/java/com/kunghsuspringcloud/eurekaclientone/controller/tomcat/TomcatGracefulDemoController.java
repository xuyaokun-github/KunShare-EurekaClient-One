package com.kunghsuspringcloud.eurekaclientone.controller.tomcat;

import com.kunghsuspringcloud.eurekaclientone.bean.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tomcat")
@RestController
public class TomcatGracefulDemoController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TomcatGracefulDemoController.class);

    @GetMapping("/test1")
    public ResultVo test1(@RequestParam String requestId){

        LOGGER.info("接收到：{}", requestId);
        work();
        LOGGER.info("处理完成：{}", requestId);
        return ResultVo.valueOfSuccess(requestId);
    }

    private void work() {

//        try {
//            //假如一个请求，处理了3秒
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        long start = System.currentTimeMillis();
        while (true){
            if (System.currentTimeMillis() - start >= 70000){
                break;
            }
        }
    }


}
