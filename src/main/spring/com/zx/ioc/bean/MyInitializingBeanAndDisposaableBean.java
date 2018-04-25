package com.zx.ioc.bean;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
/**
 * 为了后面使用暂时注释
 */
//@Component
public class MyInitializingBeanAndDisposaableBean implements InitializingBean ,DisposableBean ,BeanNameAware{

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(" MyInitializingBeanAndDisposaableBean ... ... afterpropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("MyInitializingBeanAndDisposaableBean.............destory");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("MyInitializingBeanAndDisposaableBean ....  setBeanName:" + name);
    }
}
