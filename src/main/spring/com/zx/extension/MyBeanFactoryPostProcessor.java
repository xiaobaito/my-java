package com.zx.extension;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        System.out.println("come into myBeanFactoryPostProcessor ... postProcessBeanFactory...");

        int count = beanFactory.getBeanDefinitionCount();
        System.out.println("当前BeanFactory中有：" + count + "个Bean");
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
//        System.out.println("myBeanFactoryPostProcessor ... " + Arrays.asList(beanDefinitionNames));
        for(String beanDefinitionName :beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
