package com.zx.ioc.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 后置处理器
 *      初始化前后进行处理工作
 *
 *
 * @Component 将后置处理器加入到容器中
 */
/**
 * 为了后面使用暂时注释
 */
//@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("mybeanpostProcessor ... ... postprocessBeforeInitlization....." + beanName + "【bean】" + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("mybeanpostProcessor ... ... postProcessAfterInitialization....." + beanName + "【bean】" + bean);
        return bean;
    }
}
