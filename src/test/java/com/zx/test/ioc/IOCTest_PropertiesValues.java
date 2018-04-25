package com.zx.test.ioc;

import com.zx.ioc.bean.Person;
import com.zx.ioc.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_PropertiesValues {

    private ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);
    @Test
    public void test01() {

        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);

    }
}
