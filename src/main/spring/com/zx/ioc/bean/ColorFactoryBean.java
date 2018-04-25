package com.zx.ioc.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * 创建一个自定义的FactoryBean
 */
public class ColorFactoryBean implements FactoryBean {

    //返回一个Color对象 这个对象会添加到容器中去
    @Override
    public Object getObject() throws Exception {
        System.out.println("colorFactoryBean ... getObject ...");
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
