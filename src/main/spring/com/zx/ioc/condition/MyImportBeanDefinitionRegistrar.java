package com.zx.ioc.condition;

import com.zx.ioc.bean.Yellow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param importingClassMetadata : 当前类的注解信息
     * @param registry ： BeanDefinitonRegistry 注册类
     *                 所有要向容器中添加的Bean ,调用BeanDefinitionRegistry.registerBeanDefinition手工个注册进来
     *
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        if (!registry.containsBeanDefinition("yellow")){
            RootBeanDefinition yellowBeanDefinition = new RootBeanDefinition(Yellow.class);
            registry.registerBeanDefinition("yellow", yellowBeanDefinition);
        }

    }
}
