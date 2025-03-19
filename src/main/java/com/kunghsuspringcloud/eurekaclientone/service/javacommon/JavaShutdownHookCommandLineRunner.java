package com.kunghsuspringcloud.eurekaclientone.service.javacommon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;

@Component
public class JavaShutdownHookCommandLineRunner implements CommandLineRunner, ApplicationContextAware {

    ApplicationContext context;

    private final static Logger logger = LoggerFactory.getLogger(JavaShutdownHookCommandLineRunner.class);

    public void run(String... args) {

        init();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public void init(){

        //注册一个钩子
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是自定义ShutdownHook.....");
            }
        }));

        showAllShutdownHook();

        //注册自定义的spring钩子
        SpringApplication.getShutdownHandlers().add(new Thread(()->{
            System.out.println("我是自定义Spring ShutdownHandler.....");
        }));
    }

    private void showAllShutdownHook() {

        // 获取 ApplicationShutdownHooks 类
        Class<?> shutdownHooksClass = null;
        try {
            shutdownHooksClass = Class.forName("java.lang.ApplicationShutdownHooks");
            // 获取私有静态字段 "hooks"
            Field hooksField = shutdownHooksClass.getDeclaredField("hooks");
            hooksField.setAccessible(true); // 突破访问限制

            // 获取存储钩子的 IdentityHashMap
            IdentityHashMap<Thread, Thread> hooks =
                    (IdentityHashMap<Thread, Thread>) hooksField.get(null);

            // 遍历并打印所有已注册的 Shutdown Hook
            System.out.println("Registered Shutdown Hooks:");
            hooks.keySet().forEach(thread ->
                    System.out.println(" - " + thread.getName())
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
