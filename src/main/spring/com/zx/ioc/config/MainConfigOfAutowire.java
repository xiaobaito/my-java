package com.zx.ioc.config;


import com.zx.ioc.bean.Car;
import com.zx.ioc.bean.Color;
import com.zx.ioc.dao.PersonDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配
 *
 *  1、@Autowired  自动注入
 *    @see org.springframework.beans.factory.annotation.Autowired
 *    1.@默认优先按照类型去容器中找对应的组件 ：applicationContext.getBean(PersonDao.class);找到就赋值
 *    2.如果添加@Autowired注解的属性，找到多个相同类型的组件，再将属性的名称座位组件的ID去容器中找
 *                         applicationContext.getBean("personDao"); 注意 如果系统中配置了多个相同类型的组件，然后自己在手动获取就会报错(applicationContext.getBean(Persondao.class))
 *                          *org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'PersonDao' available:
 *                            expected single matching bean but found 2: personDao,personDao2
 *    3.@Qualifier(value = "personDao") 如果属性上使用@Qualifier指定了需要装配的组件的ID，就不会在使用属性名称了
 *    4.使用@Autowired自动装配就要将属性赋值好，不然就会报错  可以使用 可以使用@Autowired(required=false); 解决
 *       Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'PersonDao' available:
 *       expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Qualifier(value=personDao),
 *       @org.springframework.beans.factory.annotation.Autowired(required=true)}
 *
 *    5.@Primary : 让Spring进行自动装配的时候，默认使用首选的bean , 也继续可以使用@Qualifier指定的bean
 *
 *  2、Spring还可以使用@Resource (JSR250)  和@Inject (JSR330) [java规范的注解]
 *       @Resource：
 *       和@Autowired一样实现自动装配功能，默认按照组件的名称进行装配
 *        【 不支持 @Primary @Autowired(required = false)  】
 *       @Inject :
 *         需要带入 javax.inject包，和@Autowired功能一样，支持 @Primary 不支持 @Autowired(required = false)
 *
 *       @Autowired 是spring定义的 @Resource 、 @Inject 都是遵循java规范的
 *
 *     @see AutowiredAnnotationBeanPostProcessor : 解析完成自动装配功能
 *
 *  3、 @Autowired 可以用在 ： 构造器 参数 方法 属性 ；都是从容器中获取参数组件的值
 *      1.[标注在方法位置]：@Bean+方法参数；参数从容器中获取;默认不写@Autowired效果是一样的；都能自动装配
 * 		2.[标在构造器上]：如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中获取
 * 		3.放在参数位置：
 *
 * 	4、自定义组件	比如想使用spring底层的一些组件（ApplicationContext,BeanFactory,xxx）
 * 	   自定义组件要实现 xxxAeare ；在创建对象的时候，会调用接口规定的方法注入相关组件
 * 	   把Spring底层一些组件注入到自定义的Bean中；
 * 		xxxAware：功能使用xxxProcessor；
 * 			ApplicationContextAware==》ApplicationContextAwareProcessor；
 *
 *
 */
@ComponentScan(value = {"com.zx.ioc.controller","com.zx.ioc.service","com.zx.ioc.dao","com.zx.ioc.bean"})
@Configuration
public class MainConfigOfAutowire {

//    @Primary
    @Bean("personDao2")
    public PersonDao personDao2(){
        PersonDao personDao = new PersonDao();
        personDao.setFlag("2");
        return personDao;
    }

    /**
     * @Bean标注的方法创建对象的时候，方法参数的值从容器中获取
     * @param car
     * @return
     */
    @Bean
    public Color color(/*@Autowired*/ Car car) {
        Color color = new Color();
        color.setCar(car);
        return color;
    }
}

