package com.zx.ioc;

import com.zx.ioc.bean.Person;
import com.zx.ioc.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {

    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:bean.xml");
//        Person person = (Person) applicationContext.getBean("person");
//        System.out.println(person);

        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
       if (context.containsBean("person")) {
           Person person = (Person)context.getBean("person");
           System.out.println(person);

       }

        System.out.println("===========获取加载的所有bean=============");
        String[] names = context.getBeanDefinitionNames();
        for (String name : names) {
            Object object = context.getBean(name);
            System.out.println(object);
        }


    }
}
