package com.zx.ioc.config;

import com.zx.ioc.bean.Person;
import org.springframework.context.annotation.*;


/**
 * @see PropertySources  可以引入多个  PropertySource[] value();
 *
 * @see PropertySource  类似于配置文件中 <context:property-placeholder location="classpath:conf/*.properties" file-encoding="UTF-8"/>
 *
 * 使用@PropertySource读取外部配置文件中的k/v保存到运行的环境变量中;加载完外部的配置文件以后使用${}取出配置文件的值
 */

/**
 * 使用@Value赋值；  具体在Person类中可以查看
 * 1、基本的数值
 * 2、可以写SpEL表达式
 * 3、可以写${}；去除配置文件(*.properties)中的值(会通过@propertySource配置读取到运行的环境中)
 *
 */
@ComponentScan("com.zx.ioc.bean")
@PropertySources(value = {
        @PropertySource(value = "classpath:/ioc/person.properties",encoding = "UTF-8")
})
@Configuration
public class MainConfigOfPropertyValues {

    @Bean
    public Person person() {
        return new Person();
    }
}
