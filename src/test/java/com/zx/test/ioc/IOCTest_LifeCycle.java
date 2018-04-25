package com.zx.test.ioc;

import com.zx.ioc.config.MainConfigOfLifrCycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_LifeCycle {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifrCycle.class);

        System.out.println("容器创建完成....");
        applicationContext.getBean("car");
        System.out.println("--------------------------");

        applicationContext.close();
    }
}
