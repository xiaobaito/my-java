package com.zx.ioc.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {
    /**
     * @param context  : 环境上下文
     *                 context.getBeanFactory(); // 获取IOC使用的BeanFactory
     *                 context.getClassLoader();//获取类加载
     *                 context.getRegistry();//获取到Bean的注册类  通过注册类可以判断注册Bean的情况，也可注册Bean
     * @param metadata ：注解信息
     * @return
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        Environment environment = context.getEnvironment();// 获取当前环境

        String property = environment.getProperty("os.name");
        if (property.toLowerCase().contains("linux")) {
            return true;
        }


        return false;
    }
}
