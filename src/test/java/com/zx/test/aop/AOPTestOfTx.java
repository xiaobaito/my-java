package com.zx.test.aop;

import com.zx.aop.config.MainConfigOfTx;
import com.zx.aop.tx.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by weizx on 2018/4/22.
 */
public class AOPTestOfTx {

    @Test
    public void test01() {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfTx.class);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.add();
    }
}
