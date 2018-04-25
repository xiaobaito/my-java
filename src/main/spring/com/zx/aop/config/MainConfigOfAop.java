package com.zx.aop.config;

import com.zx.aop.LogAspects;
import com.zx.aop.MathCalculate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by weizx on 2018/4/21.
 */

/**
 * AOP ： 【底层实现： 动态代理】
 *   指在程序运行期间将某段代码切入到制定方法制定位置进行运行的编程方式；
 *
 *   实现：
 *     1、首先导入AOP模块，引入spring-aspects jar包
 *     2、定义一个业务逻辑类(MathCalculate),在业务逻辑运行的时候将日志进行打印(方法之前，方法之后，方法正常返回之后，方法异常返回之后)
 *     3.定义一个日志切面类(LogAspects),切面里面的方法需要动态的感知MathCalculate.division()运行到哪里然后执行
 *       通知方法：
 *         前置通知(@Before):logstart() ;在目标方法执行之前执行
 *         后置通知（@After）：在目标方法执行之后，在正常/异常返回之前
 *         返回通知(@AfterReturnning)：在目标方法/后置通知 之后
 *         异常通知(@AfterThroeing) ： 在目标方法/后置通知 之后
 *         环绕通知(@Around)：动态代理，手动推进目标方法的执行(jointPoint.procced())
 *     4.给切面类的目标方法标注什么时候运行（通知注解）
 *     5.将切面类和业务逻辑类加载到容器中（配置类的@Bean）
 *     6.必须告诉Spring容器哪一个是切面类（LogAspect类上家@Aspect）
 *     7.[手动开启基于注解的AOP模式] 在配置类上加@EnableAspectJAutoProxy
 *
 *     在Spring中有很多@Enablexxx
 *
 *     aop实现主要的三个步骤:
 *       1.将业务逻辑类和切面类都加入到容器中，并告诉Spring哪个是切面类
 *       2.在切面类的每个方法上加上注解，告诉spring什么时候运行，(pointCut（），@Pointcut("execution(public int com.zx.aop.MathCalculate.*(..))"))
 *       3.开启基于注解的aop模式，@EnableAspectJAutoProxy
 *
 *    下面看看AOp实现的原理 ： 开启debug，跟代码
 *    【AOP原理】【看容器中注册了什么组件，组件什么时候工作，这个组件的功能是什么？】
 *     @see  EnableAspectJAutoProxy
 *      1.@EnableAspectJAutoProxy是什么？
 *         在EnableAspectJAutoProxy的注解类上导入了：@Import(AspectJAutoProxyRegistrar.class)：给容器中导入了AspectJAutoProxyRegistrar

 *
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAop {

    @Bean
    public MathCalculate mathCalculate(){
        return new MathCalculate();
    }

    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }
}
