package com.zx.test.ioc;

import com.zx.ioc.bean.Car;
import com.zx.ioc.bean.Color;
import com.zx.ioc.config.MainConfigOfAutowire;
import com.zx.ioc.service.PersonService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Autowired {

    @Test
    public void test01() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowire.class);
        PersonService personService = applicationContext.getBean(PersonService.class);
        System.out.println(personService);


        /**
         * 项目配置了两个 PersonDao 在以类型去获取就会报错
         * org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'PersonDao' available:
         * expected single matching bean but found 2: personDao,personDao2
         */
//        PersonDao personDao = applicationContext.getBean(PersonDao.class);
//        System.out.println(personDao);

        Color color = applicationContext.getBean(Color.class);
        System.out.println(color);
        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);
    }
}
