package com.kunghsuspringcloud.eurekaclientone.aspect;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;

//@Component
//@Aspect
// 假如了这个注解，就相当于定义一个切面类，但是前提是该类也被定义为bean
public class MyAspect implements ApplicationContextAware {

    @Autowired
    private DruidDataSource dataSource2;

    private ApplicationContext applicationContext;


    //1 （通过接口注入肯定是可以的，但是假如有多个实现，就要通过属性名或者bean名区分了）
//    @Autowired
//    private DataSource dataSource;

    //2 （假如只有2这种注入，直接就会报错的，因为不能通过父类注入）


    //3 （假如用2和3，2在前3在后，会发现无法注入DruidDataSource，
    // 但是假如用1和2这个顺序，1在前2在后，DruidDataSource将可以被注入，为什么？）
    //因为spring的设计，假如注入过某个类型，它就能简单判断这个类型是否存在过，直接快捷注入
    //假如发现没有，就会报错。所以把能注入的放前面，后面的父类也能注入了
//    @Autowired
//    private DataSource dataSource;

    //4 （用实例直接注入，不行，因为本例中DruidDataSourceWrapper不是一个public类，但是可以通过接口来注入它）
//    @Autowired
//    private DruidDataSourceWrapper dataSource3;


    @PostConstruct
    public void init(){

        Map<String, DataSource> dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
        System.out.println(dataSourceMap.size());
        BasicDataSource basicDataSource = null;
        System.out.println("dataSource test:" + dataSource2.getClass().getTypeName());
    }


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }
}
