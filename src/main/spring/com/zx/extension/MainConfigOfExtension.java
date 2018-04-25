package com.zx.extension;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扩展原理：
 *  BeanPostProcessor : bean后置处理器，bean创建对象初始化前后开始工作的
 *
 *  一、实现BeanFactoryPostProcessor:beanFactory的后置处理器
 *     在BeanFactory标准初始化之后调用，来定制和修改BeanFactory的内容；
 *     所有的bean定义都保存加载到了beanFactory中，但是bean的实例还没创建
 *
 *  BeanFactoryPostProcessor原理：
 *    1、IOC容器的创建
 *    2、AnnotationConfigApplicationContext.refresh()
 *       -> AnnotationConfigApplicationContext.invokeBeanFactoryPostProcessors(beanFactory)
 *          ->PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors())
 *            此方法会先处理BeanDefinitionRegistry->BeanDefinitionRegistryPostProcessor,根据PriorityOrdered, Ordered, and the rest
 *             //First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
 *             // Next, invoke the BeanFactoryPostProcessors that implement Ordered.
 *             // Finally, invoke all other BeanFactoryPostProcessors.
 *             本项目中使用的MyBeanFactoryPostProcessor没有实现 ProorityOrder和Ordered 所以最后调用：
 *             invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);
 *             发方法循环遍历处理所有PostProcessors ,调用BeanFactoryPostProcessor的postProcessorBeanFactory(beanFactory)方法
 * 二、实现BeanDefinitionRegistryPostProcessor
 * @see org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
 *     public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *          void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;
 *     执行时机:在所有bean定义信息将要被加载，bean实例还未被创建的时候
 *
 *     BeanFactoryPostProcessor 回先处理BeanDefinitionRegistry，所以 BeanDefinitionRegistryPostProcessor优先于BeanPostProcessor的执行
 *     可以利用BeandefinitionRegistryPostProcessor给容器添加一些额外的组件
 *
 *   原理 和BeanFactoryPostProcessor 实现原理一样，
 *     在调用PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors())发方法的时候
 *     会先判断有没有Registry的如果有限执行这个
 *
 * 三 、ApplicationListener : 监听容器中发布的事件，事件驱动模型开发
 *
 *      步骤：
 *           1、写一个ApplicationListener的实现类来监听某个事件(ApplicationEvent及其子类)
 *             @EventListener;
 * 			   原理：使用EventListenerMethodProcessor处理器来解析方法上的@EventListener；
 *           2、把监听器加载到容器中
 *           3、只要容器中有相关的事件发布，我们就能监听到这个事件
 *              ContextRefreshedEvent：容器刷新完成（所有bean都完全创建）会发布这个事件；
 * 				ContextClosedEvent：关闭容器会发布这个事件；
 * 			4、发布一个事件：
 * 				applicationContext.publishEvent()；
 *
 * 	  原理：
 *  	ContextRefreshedEvent、IOCTest_Ext$1[source=我发布的时间]、ContextClosedEvent；
 *  1）、ContextRefreshedEvent事件：
 *  	1）、容器创建对象：refresh()；
 *  	2）、finishRefresh();容器刷新完成会发布ContextRefreshedEvent事件
 *  2）、自己发布事件；
 *  3）、容器关闭会发布ContextClosedEvent；
 *
 *  【事件发布流程】：
 *  	3）、publishEvent(new ContextRefreshedEvent(this));
 *  			1）、获取事件的多播器（派发器）：getApplicationEventMulticaster()
 *  			2）、multicastEvent派发事件：
 *  			3）、获取到所有的ApplicationListener；
 *  				for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
 *  				1）、如果有Executor，可以支持使用Executor进行异步派发；
 *  					Executor executor = getTaskExecutor();
 *  				2）、否则，同步的方式直接执行listener方法；invokeListener(listener, event);
 *  				 拿到listener回调onApplicationEvent方法；
 *
 *  【事件多播器（派发器）】
 *  	1）、容器创建对象：refresh();
 *  	2）、initApplicationEventMulticaster();初始化ApplicationEventMulticaster；
 *  		1）、先去容器中找有没有id=“applicationEventMulticaster”的组件；
 *  		2）、如果没有this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 *  			并且加入到容器中，我们就可以在其他组件要派发事件，自动注入这个applicationEventMulticaster；
 *
 *  【容器中有哪些监听器】
 *  	1）、容器创建对象：refresh();
 *  	2）、registerListeners();
 *  		从容器中拿到所有的监听器，把他们注册到applicationEventMulticaster中；
 *  		String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 *  		//将listener注册到ApplicationEventMulticaster中
 *  		getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 *
 *   SmartInitializingSingleton 原理：->afterSingletonsInstantiated();
 *   		1）、ioc容器创建对象并refresh()；
 *   		2）、finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean；
 *   			1）、先创建所有的单实例bean；getBean();
 *   			2）、获取所有创建好的单实例bean，判断是否是SmartInitializingSingleton类型的；
 *   				如果是就调用afterSingletonsInstantiated();
 *
 *
 */

@ComponentScan("com.zx.extension")
@Configuration
public class MainConfigOfExtension {
}
