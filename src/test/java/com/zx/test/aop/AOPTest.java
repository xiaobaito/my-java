package com.zx.test.aop;

import com.zx.aop.config.MainConfigOfAop;
import com.zx.aop.MathCalculate;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by weizx on 2018/4/21.
 */
public class AOPTest {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAop.class);

        MathCalculate mathCalculate = applicationContext.getBean(MathCalculate.class);
         mathCalculate.division(1, 1);
//        System.out.println(division);

    }
}
