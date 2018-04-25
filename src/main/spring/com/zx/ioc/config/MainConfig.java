package com.zx.ioc.config;

import com.zx.ioc.bean.Person;
import org.springframework.context.annotation.*;

// 配置类 == 配置文件
@Configuration  // 作用：告诉spring这是一个配置类

/**
 * @ComponentScans
 * @ComponentScan value : 制定具体要扫描的包 == xml中的base-package
 *                useDefaultFilters : 指示是否自动检测类的注释  默认是true 在使用 includeFilters excludeFilter等配置的时，要将其改为 false
 *                includeFilters : 指定扫描包的时候只需要扫描哪些组件
 *                      使用ComponentScan 内部接口 Filter
 *                      Filter.ANNOTATION : 按照注解
 *                      FilterType.ASSIGNABLE_TYPE :按照给定类型
 *                      FilterType.ASPECTJ : 使用 ASPECTJ 表达式
 *                      FilterType.REGEX : 使用正则表达式
 *                      FilterType.CUSTOM :使用自定义
 */
@ComponentScans(value = {
        @ComponentScan(value = "com.zx.ioc", includeFilters = {
//              @ComponentScan.Filter(type = FilterType.ANNOTATION,value = Service.class)
//              @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = PersonController.class)
                @ComponentScan.Filter(type = FilterType.CUSTOM, classes = MyTypeFilter.class)
        }, useDefaultFilters = false
//        ,excludeFilters ={@ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Service.class)}
        )
})
public class MainConfig {

    // @Bean的作用 : 给容器注册一个Bean, 类型为返回值类型,id默认是使用方法名
    @Bean("person")
    public Person person01() {
        return new Person("zhang", 13);
    }
}
