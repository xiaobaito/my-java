package com.zx.ioc.config;

import com.zx.ioc.bean.Color;
import com.zx.ioc.bean.ColorFactoryBean;
import com.zx.ioc.bean.Green;
import com.zx.ioc.bean.Person;
import com.zx.ioc.condition.LinuxCondition;
import com.zx.ioc.condition.MyImportBeanDefinitionRegistrar;
import com.zx.ioc.condition.MyImportSelector;
import com.zx.ioc.condition.WindowsCondition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;

/**
 * -Dos.name=linux 更改运行环境的操作系统
 *
 * @Conditional  满足当前条件， 当前类中的所有bean注册才能生效
 */

@Conditional(WindowsCondition.class)

@Configuration
/**
 *  @Import 导入组件， 接收的是数组类型 id默认是全类名
 */
@Import({Color.class, Green.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

    /**
     *  容器启动 默认是 singleton
     *  @see ConfigurableBeanFactory#SCOPE_SINGLETON  singleton
     *  @see ConfigurableBeanFactory#SCOPE_PROTOTYPE  prototype
     *  @see org.springframework.web.context.WebApplicationContext#SCOPE_REQUEST  request
     *  @see org.springframework.web.context.WebApplicationContext#SCOPE_SESSION     sesssion
     *
     */

    /**
     * @Scope 作用 : 调整作用域
     *  prototype : 多实例： 在IOC容器启动的时候并不会去调用方法创建对象在容器中
     *              只有在使用的时候才会调用方法创建对象；
     *  singleton : 单例 (默认值) IOC容器启动的时候就会去调用方法创建对象在容器中，以后在使用直接从容器中获取(map.get)
     *  request   : 每次请求创建一个实例
     *  session   : 同一个session创建一个实例
     */

     /**
     * @Lazy 作用 : 懒加载 ： 容器在启动的时候不创建对象，第一次使用(获取)的时候创建对象，并初始化
     */
    @Lazy
    @Scope(value = "prototype")
    @Bean()
    public Person person() {
        System.out.println("向容器中添加 person ...");
        return new Person("zx", 20);
    }



    @Bean("bill")
    public Person person01() {
        return new Person("bill",89);
    }

    @Conditional(LinuxCondition.class)
    @Bean("linus")
    public Person person02() {
        return new Person("linux",89);
    }

    /**
     * 向容器中添加(注册)组件
     *   1、包扫描 + 组件标注注解 (@Controller/@Service/@Repository/@Component)[自己写的类]
     *   2、@Bean导入第三方包里面的组件
     *   3、@Import 快速给容器中导入一个组件
     *       1.@Import(要导入到容器的组件，[数组])，容器会自动注册这个组件，id默认是群类名
     *       2.ImportSelector : 返回需要导入的组件的全类名数组(可参考上面实现)
     *       3.ImportBeanDefinitionRegistrar : 手动注册到容器中(可参考上面实例)
     *   4、使用 Spring提供的 FactoryBean(工厂Bean)
     *      1.默认获取到的工厂Bean调用getObject创建对象
     *      2.获取工厂bean本身，我们需要给id前面加一个 &
     */

    @Bean
    public ColorFactoryBean colorFactoryBean () {
        return new ColorFactoryBean();
    }
}
