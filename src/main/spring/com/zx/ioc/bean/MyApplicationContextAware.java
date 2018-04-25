package com.zx.ioc.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 为了后面使用暂时注释
 */
//@Component
public class MyApplicationContextAware implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public MyApplicationContextAware() {
        System.out.println("myApplicationContextAware .... .. constructor ....");
    }

    /**
     * 对象创建并赋值之后使用
     */
    @PostConstruct
    public void init() {
        System.out.println("myApplicationContextAware .... init.....");
    }

    /**
     * 容器移除之前使用
     */
    @PreDestroy
    public void destroy(){
        System.out.println("myApplicationContextAware ... destory ....");
    }

    /**
     * 通过实现 ApplicationContextAware 接口重写其setApplicationContext()方法 拿到容器上下文
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
