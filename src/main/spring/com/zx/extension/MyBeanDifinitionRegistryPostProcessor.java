package com.zx.extension;

import com.zx.ioc.bean.Car;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDifinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        System.out.println("=================postProcessBeanDefinitionRegistry=================");
        System.out.println("myBeanDefinitionRegistryPostProcessor... postProcessBeanDefinitionRegistry() ...BeanDefinitions is：" + registry.getBeanDefinitionCount());
        RootBeanDefinition beanDefinition = new RootBeanDefinition(Car.class);
        registry.registerBeanDefinition("hello",beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("----------postProcessBeanFactory--------------");
        System.out.println("myBeanDefinitionRegistryPostProcessor .... postProcessorBeanFactory() ... BeanDefinitions is :" + beanFactory.getBeanDefinitionCount());

    }
}
