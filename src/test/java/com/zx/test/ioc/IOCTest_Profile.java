package com.zx.test.ioc;

import com.zx.ioc.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

/**
 * Created by weizx on 2018/4/22.
 */
public class IOCTest_Profile {

    /**
     * 运行动态参数运行，在虚拟机参数位置加-Dspring.profiles.active=test
     *
     */
    @Test
    public void test01(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
        String[] beanNamesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for (String dataSourceName:beanNamesForType) {
            System.out.println(dataSourceName);
        }
        System.out.println("============================================");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName: beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

    }


    @Test
    public void test02() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("dev");
        applicationContext.register(MainConfigOfProfile.class);
        applicationContext.refresh();

        String[] beanNameForType = applicationContext.getBeanNamesForType(DataSource.class);
        for (String name: beanNameForType) {
            System.out.println(name);
        }
        System.out.println("======================================");

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName: beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
