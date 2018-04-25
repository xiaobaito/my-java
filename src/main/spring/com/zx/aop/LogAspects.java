package com.zx.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * Created by weizx on 2018/4/21.
 */
@Aspect
public class LogAspects {

    @Pointcut("execution(public int com.zx.aop.MathCalculate.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        Object[] joinPointArgs = joinPoint.getArgs();
        System.out.println("LogAspects .... logstart ...method:"+joinPoint.getSignature().getName() +"... 参数:" + Arrays.asList(joinPointArgs));
    }

    @After("com.zx.aop.LogAspects.pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println("logAspects ... logEnd ... " + joinPoint.getSignature().getName() + "  is ovevr");
    }

    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println("logAspects ... logReturn ... " + joinPoint.getSignature().getName() + "  is result : " + result);
    }

    @AfterThrowing(value = "pointCut()",throwing = "e")
    public void logThrowing(JoinPoint joinPoint, Exception e) {
        System.out.println("LogAspeccts ... logThrowing...method:" + joinPoint.getSignature().getName() + "exceprion:" + e);
    }
}
