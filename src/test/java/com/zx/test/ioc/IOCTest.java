package com.zx.test.ioc;

import com.zx.ioc.bean.Person;
import com.zx.ioc.config.MainConfig;
import com.zx.ioc.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

public class IOCTest {

    /**
     * test FactoryBean
     */
    @Test
    public void test04() {
        ApplicationContext applicationContext  = new AnnotationConfigApplicationContext(MainConfig2.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        System.out.println("--------------------------------------------------");
        for (String name : names) {
            System.out.println(name);
        }


        System.out.println("====================");
        Object bean1 = applicationContext.getBean("colorFactoryBean");
        Object bean2 = applicationContext.getBean("colorFactoryBean");
        System.out.println("bean的类型 ： " + bean1.getClass());
        System.out.println(bean1 == bean2);

        System.out.println("====================");
        Object bean3 = applicationContext.getBean("&colorFactoryBean");
        System.out.println(bean3);
    }



    @Test
    public void test03() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        Environment environment = applicationContext.getEnvironment();
        String property =  environment.getProperty("os.name");
        System.out.println(property);

        String[] beanNamesForType = applicationContext.getBeanNamesForType(Person.class);
        for (String name :beanNamesForType) {
            System.out.println(name);
        }
        Map<String, Person> beansOfType = applicationContext.getBeansOfType(Person.class);
        for (Map.Entry<String,Person> entry:beansOfType.entrySet()) {
            System.out.println(entry.getKey() + ">>>> : " + entry.getValue());
        }
    }

    @Test
    public void test02() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);


        System.out.println("容器IOC 启动完成 ...");
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        /*校验单实例 和 多实例 他们的bean是否是同一个实例*/
//       Object bean =  applicationContext.getBean("person");
//       Object bean1 =  applicationContext.getBean("person");
//        System.out.println(bean == bean1);
    }




    @Test
    public void test01() {
        ApplicationContext applicationContext  = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

    }
}
