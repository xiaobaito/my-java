package com.zx.ioc.config;


import com.zx.ioc.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * bean的生命周期
 *   bean的创建 -- 初始化 -- 销毁
 *   容器自己管理bean的生命周期
 *   也可以自己定义bean的初始化和销毁方法，当bean在容器中进行到当前的生命周期时，来调用我们自己定义的生命周期
 *
 *   构造(创建对象)
 *        单实例： 容器启动的时，创建对象
 *        多实例： 在每次使用获取时，创建对象
 *
 *   BeanPostProcessor.postProcessBeforeInitialization()
 *   初始化
 *      对象的创建，初始化，并调用初始化方法....
 *
 *   BeanPostProcessor.postProcessAfterInitialization()
 *   销毁 ：
 *         单实例：容器关闭的时候
 *         多实例：容器不会管理这个bean，不会调用销毁方法
 *
 *  自定义容器初始化和销毁方式：
 *   1、 通过让bean实现InitializingBean并实现afterPropertiesSet()方法 可以和ApplicationContextAware一起使用
 *
 *   2、 在@Bean注解上指定初始化和销毁方法  @Bean(intiMethod="init",destroyMethod="destory")
 *
 *   3、 可以是用java的JSR250  (可参考 MyApplicationContextAware)
 *        @PostConstruct : 在bean创建完成并属性赋值完成，来执行初始化方法
 *        @PreDestroy :在容器销毁Bean之前通知我们进行清理工作
 *
 *   4、 通过让bean实现BeanPostProcessor的两个方法
 *        postProcessBeforeInitialization() ：在初始化之前
 *        postProcessAfterInitialization() ：在出事后之后
 *
 *
 * BeanPostProcessor 原理：
 * AbstractConfigApplication()有参构造，(先实现无参构造，在注册，最后refresh())
 *    -> AbstractApplicationContext.refresh()
 *       -> AbstractApplicationContext.finishBeanFactoryInitialization()
 *         -> DefaultListAbleBeanFactory.preInstantialization()
 *          -> AbstractBeanFactory.getBean()
 *            ->AbstractBeanFactory.doGetBean()
 *              -> DefaultSingletonBeanRegistry.getSingleton()
 *                -> AbstractBeanFactory.getObject()
 *                  -> AbstractAutowireCapableBeanFactory.createBean()
 *                    -> AbstractAutowireCapableBeanFactory.doCreateBean()
 *                      -> AbstractAutowireCapableBeanFactory.initializeBean()
 *                        -> AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization()
 *                          ->MyBeanPostProcessor.postProcessBeforeInitialization()
 *                           -> AbstractAutowireCapableBeanfactory.applyBeanPostProcessorsBeforeInitialization()
 *                           循环处理自己实现BeanPostProcessor的postProcessBeforeBeanFactor方法，如果返回的result为null 跳出循环，否则直到实现BeanPostProcessor的实现类循环结束
 *                           ->AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization()
 *                            跟Before类似
 *
 * AbstractAutowireCapableBeanFactory.initializeBean()->
 *
 *
 */
@ComponentScan("com.zx.ioc.bean")
@Configuration
public class MainConfigOfLifrCycle {

//    @Scope(value = "prototype")
    @Bean(initMethod ="init",destroyMethod = "destory")
    public Car car() {
        return new Car();
    }
}
