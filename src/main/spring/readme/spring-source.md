#Spring容器的refresh()

## Bean的创建以及预准备工作
### 1、prepareRefresh();刷新前的预处理
 1. initPropertySources();初始化一些属性设置，子类自定义个性化的属性设置方法
 2. getEnvironment().validateRequiredProperties();校验属性
 3. this.earlyApplicationEvents = new LinkedHashSet<ApplicationEvent>();保存容器中的一些早期事件
 
### 2、 ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
获取BeanFactory(Tell the subclass to refresh the internal bean factory.)
1. refreshBeanFactory();刷新【创建】BeanFactory；
  创建了一个this.beanFactory = new DefaultListableBeanFactory();
  设置id
2. ConfigurableListableBeanFactory beanFactory = getBeanFactory();返回刚才GenericApplicationContext创建的BeanFactory对象；
3. 将创建的BeanFactory【DefaultListableBeanFactory】返回；

###3、 prepareBeanFactory(beanFactory); 
BeanFactory的预准备工作（BeanFactory进行一些设置）(Prepare the bean factory for use in this context.)
  
 ```
      // Tell the internal bean factory to use the context's class loader etc.
      //设置BeanFactory的类加载器、支持表达式解析器...
 		beanFactory.setBeanClassLoader(getClassLoader());
 		beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
 		beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));
 
 		// Configure the bean factory with context callbacks.
 		//添加部分BeanPostProcessor【ApplicationContextAwareProcessor】
 		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
 		//设置忽略的自动装配的接口EnvironmentAware、EmbeddedValueResolverAware、ResourceLoaderAware...；
 		beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
 		beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);
 		beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
 		beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
 		beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
 		beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);
 
 		// BeanFactory interface not registered as resolvable type in a plain factory.
 		// MessageSource registered (and found for autowiring) as a bean.
 		//注册可以解析的自动装配；我们能直接在任何组件中自动注入：
 		beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
 		beanFactory.registerResolvableDependency(ResourceLoader.class, this);
 		beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
 		beanFactory.registerResolvableDependency(ApplicationContext.class, this);
 
 		// Register early post-processor for detecting inner beans as ApplicationListeners.
 		//添加BeanPostProcessor
 		beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(this));
 
 		// Detect a LoadTimeWeaver and prepare for weaving, if found.//添加编译时的AspectJ  (weaving  织入)
 		if (beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
 			beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
 			// Set a temporary ClassLoader for type matching.
 			beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
 		}
 
 		// Register default environment beans.
 		// 注册一些默认的组件
 		if (!beanFactory.containsLocalBean(ENVIRONMENT_BEAN_NAME)) {
 		//ConfigurableEnvironment
 			beanFactory.registerSingleton(ENVIRONMENT_BEAN_NAME, getEnvironment());
 		}
 		if (!beanFactory.containsLocalBean(SYSTEM_PROPERTIES_BEAN_NAME)) {
 		//Map<String, Object>
 			beanFactory.registerSingleton(SYSTEM_PROPERTIES_BEAN_NAME, getEnvironment().getSystemProperties());
 		}
 		if (!beanFactory.containsLocalBean(SYSTEM_ENVIRONMENT_BEAN_NAME)) {
 		//Map<String, Object>
 			beanFactory.registerSingleton(SYSTEM_ENVIRONMENT_BEAN_NAME, getEnvironment().getSystemEnvironment());
 		}
 ```
 
 ### 4、postProcessBeanFactory(beanFactory);
     BeanFactory准备工作完成后进行的后置处理工作；(Allows post-processing of the bean factory in context subclasses.)
     子类通过重写这个方法，在BeanFactory创建并预准备完成以后作进一步的设置
     
## 后置处理
### 5、invokeBeanFactoryPostProcessors(beanFactory);
 执行BeanFactoryPostProcessor方法(Invoke factory processors registered as beans in the context.)
 BeanFactoryPostProcessor : BeanFactory的后置处理器。执行时机: 在BeanFactory标准初始化之后执行
 
1. PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());
  在该方法中：先执行BeanDefinitionRegistryPostProcessors再执行BeanFactoryPostProcessor的方法
   
2.BeanDefinitionRegistryPostProcessors的执行： 
    * 通过循环遍历判断BeanFactoryPostProcessor的类型,并获取所有的BeanDefinitionRegistryPostProcessors
    * 看先执行实现了PriorityOrdered优先级接口的BeanDefinitionRegistryPostProcessor、
        invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);->postProcessor.postProcessBeanDefinitionRegistry(registry)
    * 在执行实现了Ordered顺序接口的BeanDefinitionRegistryPostProcessor；
        invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry)->postProcessor.postProcessBeanDefinitionRegistry(registry);
    *  最后执行没有实现任何优先级或者是顺序接口的BeanDefinitionRegistryPostProcessors；
      invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry)->postProcessor.postProcessBeanDefinitionRegistry(registry);

3.BeanFactoryPostProcessor的执行
    * 同上根据遍历获取所有的BeanFactoryPostProcessor
    * 先执行实现了PriorityOrdered优先级接口的BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory)->postProcessor.postProcessBeanFactory(beanFactory);
    * 再执行实现了Ordered顺序接口的BeanFactoryPostProcessor；
        invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory)->postProcessor.postProcessBeanFactory(beanFactory);
    * 最后执行没有实现任何优先级或者是顺序接口的BeanFactoryPostProcessor 
        invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory)->postProcessor.postProcessBeanFactory(beanFactory);    

 ```java_holder_method_tree
        // Invoke BeanDefinitionRegistryPostProcessors first, if any.
        // 任何情况下，先执行BeanDefinitionRegistryPostProcessors
		Set<String> processedBeans = new HashSet<String>();

		if (beanFactory instanceof BeanDefinitionRegistry) {
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
			List<BeanFactoryPostProcessor> regularPostProcessors = new LinkedList<BeanFactoryPostProcessor>();
			List<BeanDefinitionRegistryPostProcessor> registryProcessors = new LinkedList<BeanDefinitionRegistryPostProcessor>();

			for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
				if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
					BeanDefinitionRegistryPostProcessor registryProcessor =
							(BeanDefinitionRegistryPostProcessor) postProcessor;
					registryProcessor.postProcessBeanDefinitionRegistry(registry);
					registryProcessors.add(registryProcessor);
				}
				else {
					regularPostProcessors.add(postProcessor);
				}
			}

			// Do not initialize FactoryBeans here: We need to leave all regular beans
			// uninitialized to let the bean factory post-processors apply to them!
			// Separate between BeanDefinitionRegistryPostProcessors that implement
			// PriorityOrdered, Ordered, and the rest.
			List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<BeanDefinitionRegistryPostProcessor>();

			// First, invoke the BeanDefinitionRegistryPostProcessors that implement PriorityOrdered.
			String[] postProcessorNames =
					beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			for (String ppName : postProcessorNames) {
				if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					processedBeans.add(ppName);
				}
			}
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			registryProcessors.addAll(currentRegistryProcessors);
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			currentRegistryProcessors.clear();

			// Next, invoke the BeanDefinitionRegistryPostProcessors that implement Ordered.
			postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			for (String ppName : postProcessorNames) {
				if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					processedBeans.add(ppName);
				}
			}
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			registryProcessors.addAll(currentRegistryProcessors);
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			currentRegistryProcessors.clear();

			// Finally, invoke all other BeanDefinitionRegistryPostProcessors until no further ones appear.
			boolean reiterate = true;
			while (reiterate) {
				reiterate = false;
				postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
				for (String ppName : postProcessorNames) {
					if (!processedBeans.contains(ppName)) {
						currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
						processedBeans.add(ppName);
						reiterate = true;
					}
				}
				sortPostProcessors(currentRegistryProcessors, beanFactory);
				registryProcessors.addAll(currentRegistryProcessors);
				invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
				currentRegistryProcessors.clear();
			}

			// Now, invoke the postProcessBeanFactory callback of all processors handled so far.
			invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
			invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
		}

		else {
			// Invoke factory processors registered with the context instance.
			invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
		}

		// Do not initialize FactoryBeans here: We need to leave all regular beans
		// uninitialized to let the bean factory post-processors apply to them!
		String[] postProcessorNames =
				beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);

		// Separate between BeanFactoryPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<BeanFactoryPostProcessor>();
		List<String> orderedPostProcessorNames = new ArrayList<String>();
		List<String> nonOrderedPostProcessorNames = new ArrayList<String>();
		for (String ppName : postProcessorNames) {
			if (processedBeans.contains(ppName)) {
				// skip - already processed in first phase above
			}
			else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
			}
			else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			}
			else {
				nonOrderedPostProcessorNames.add(ppName);
			}
		}

		// First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);

		// Next, invoke the BeanFactoryPostProcessors that implement Ordered.
		List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<BeanFactoryPostProcessor>();
		for (String postProcessorName : orderedPostProcessorNames) {
			orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		sortPostProcessors(orderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);

		// Finally, invoke all other BeanFactoryPostProcessors.
		List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<BeanFactoryPostProcessor>();
		for (String postProcessorName : nonOrderedPostProcessorNames) {
			nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);

		// Clear cached merged bean definitions since the post-processors might have
		// modified the original metadata, e.g. replacing placeholders in values...
		beanFactory.clearMetadataCache();
```  

### 6、registerBeanPostProcessors(beanFactory);
注册BeanPostPrcessor后置处理器(Register bean processors that intercept bean creation.)
 * PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this);
 BeanPostProcessor 的子接口有 DestructionAwareBeanPostProcessor(destruction 销毁)、InstantiationAwareBeanPostProcessor (instaniation 实例化)、
                            SmartInstantiationAwareBeanPostProcessor、MergedBeanDefinitionPostProcessor(internalPostProcessors 内部)
 * 根据BeanPostProcessor类型获取所有的BeanPostProcessor，他也会同上面的一样，根据优先级PriorityOrdered、Ordered接口来执行
   先注册PriorityOrdered优先级接口的BeanPostProcessor；把每一个BeanPostProcessor；添加到BeanFactory中 beanFactory.addBeanPostProcessor(postProcessor);
 * 再注册Ordered接口的
 * 最后注册没有实现任何优先级接口的
 * 最终注册MergedBeanDefinitionPostProcessor； 
 * 注册一个ApplicationListenerDetector；来在Bean创建完成后检查是否是ApplicationListener，如果是
   			applicationContext.addApplicationListener((ApplicationListener<?>) bea); 
                       
 
 PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this)的源码
 ```java_holder_method_tree
String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);

		// Register BeanPostProcessorChecker that logs an info message when
		// a bean is created during BeanPostProcessor instantiation, i.e. when
		// a bean is not eligible for getting processed by all BeanPostProcessors.
		int beanProcessorTargetCount = beanFactory.getBeanPostProcessorCount() + 1 + postProcessorNames.length;
		beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));

		// Separate between BeanPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		List<BeanPostProcessor> priorityOrderedPostProcessors = new ArrayList<BeanPostProcessor>();
		List<BeanPostProcessor> internalPostProcessors = new ArrayList<BeanPostProcessor>();
		List<String> orderedPostProcessorNames = new ArrayList<String>();
		List<String> nonOrderedPostProcessorNames = new ArrayList<String>();
		for (String ppName : postProcessorNames) {
			if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
				priorityOrderedPostProcessors.add(pp);
				if (pp instanceof MergedBeanDefinitionPostProcessor) {
					internalPostProcessors.add(pp);
				}
			}
			else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			}
			else {
				nonOrderedPostProcessorNames.add(ppName);
			}
		}

		// First, register the BeanPostProcessors that implement PriorityOrdered.
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors);

		// Next, register the BeanPostProcessors that implement Ordered.
		List<BeanPostProcessor> orderedPostProcessors = new ArrayList<BeanPostProcessor>();
		for (String ppName : orderedPostProcessorNames) {
			BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
			orderedPostProcessors.add(pp);
			if (pp instanceof MergedBeanDefinitionPostProcessor) {
				internalPostProcessors.add(pp);
			}
		}
		sortPostProcessors(orderedPostProcessors, beanFactory);
		registerBeanPostProcessors(beanFactory, orderedPostProcessors);

		// Now, register all regular BeanPostProcessors.
		List<BeanPostProcessor> nonOrderedPostProcessors = new ArrayList<BeanPostProcessor>();
		for (String ppName : nonOrderedPostProcessorNames) {
			BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
			nonOrderedPostProcessors.add(pp);
			if (pp instanceof MergedBeanDefinitionPostProcessor) {
				internalPostProcessors.add(pp);
			}
		}
		registerBeanPostProcessors(beanFactory, nonOrderedPostProcessors);

		// Finally, re-register all internal BeanPostProcessors.
		sortPostProcessors(internalPostProcessors, beanFactory);
		registerBeanPostProcessors(beanFactory, internalPostProcessors);

		// Re-register post-processor for detecting inner beans as ApplicationListeners,
		// moving it to the end of the processor chain (for picking up proxies etc).
		beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));
```

### 7、initMessageSource();
  初始化MessageSource组件（做国际化功能；消息绑定，消息解析）；(Initialize message source for this context.)
  * 获取BeanFactory
  * 看容器中是否有id为messageSource的，类型是MessageSource的组件
    			如果有赋值给messageSource，如果没有自己创建一个DelegatingMessageSource；
    				MessageSource：取出国际化配置文件中的某个key的值；能按照区域信息获取；
  * 把创建好的MessageSource注册在容器中，以后获取国际化配置文件的值的时候，可以自动注入MessageSource；
    			beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);	
    			MessageSource.getMessage(String code, Object[] args, String defaultMessage, Locale locale);  				
### 8、initApplicationEventMulticaster();
初始化事件派发器(Initialize event multicaster for this context.)     
* 获取BeanFactory	
* 从BeanFactory中获取applicationEventMulticaster的ApplicationEventMulticaster；
* 如果上一步没有配置；创建一个SimpleApplicationEventMulticaster
* 将创建的ApplicationEventMulticaster添加到BeanFactory中，以后其他组件直接自动注入		
  
 ```java_holder_method_tree
ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		if (beanFactory.containsLocalBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME)) {
			this.applicationEventMulticaster =
					beanFactory.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
			if (logger.isDebugEnabled()) {
				logger.debug("Using ApplicationEventMulticaster [" + this.applicationEventMulticaster + "]");
			}
		}
		else {
			this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
			beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
			if (logger.isDebugEnabled()) {
				logger.debug("Unable to locate ApplicationEventMulticaster with name '" +
						APPLICATION_EVENT_MULTICASTER_BEAN_NAME +
						"': using default [" + this.applicationEventMulticaster + "]");
			}
		}
```

### 9、onRefresh();
   留给子容器(子类) (Initialize other special beans in specific context subclasses.)
   * 子类重写这个方法，在容器刷新的时候可以自定义逻辑；

### 10、registerListeners()
给容器中将所有项目里面的ApplicationListener注册进来；( Check for listener beans and register them.)
   * 从容器中拿到所有的ApplicationListener
   * 将每个监听器添加到事件派发器中；getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
   * 派发之前步骤产生的事件；
  
```java_holder_method_tree
        // Register statically specified listeners first.
        //从容器中拿到所有的ApplicationListener
       for (ApplicationListener<?> listener : getApplicationListeners()) {
			getApplicationEventMulticaster().addApplicationListener(listener);
		}

		// Do not initialize FactoryBeans here: We need to leave all regular beans
		// uninitialized to let post-processors apply to them!
		String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
		for (String listenerBeanName : listenerBeanNames) {
		    //将每个监听器添加到事件派发器中
			getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
		}

		// Publish early application events now that we finally have a multicaster...
		//派发之前步骤产生的事件；
		Set<ApplicationEvent> earlyEventsToProcess = this.earlyApplicationEvents;
		this.earlyApplicationEvents = null;
		if (earlyEventsToProcess != null) {
			for (ApplicationEvent earlyEvent : earlyEventsToProcess) {
				getApplicationEventMulticaster().multicastEvent(earlyEvent);
			}
		}
```

### 11、finishBeanFactoryInitialization(beanFactory);
初始化所有剩下的单实例bean(Instantiate all remaining (non-lazy-init) singletons.)
 1. beanFactory.preInstantiateSingletons(); //初始化后剩下的单实例bean( Instantiate all remaining (non-lazy-init) singletons.)
   * 获取容器中的所有Bean，依次进行初始化和创建对象
   * 获取Bean的定义信息；RootBeanDefinition
   * Bean不是抽象的，是单实例的，不是懒加载；
     * 判断是不是工厂的实现，即是不是实现了FactoryBean的bean
     * 如果不是工厂bean,使用getBean(beanName); 创建对象
       *  DefaultListableBeanFactory.preInstantiateSingletons()  
           ->getBean(beanName);   
            ->AbstractBeanFactory.getBean()   
            ->doGetBean(name, null, null, false);
       * 先获取缓存中保存的单实例Bean。如果能获取到说明这个Bean之前被创建过（所有创建过的单实例Bean都会被缓存起来）
         从Object sharedInstance = getSingleton(beanName); -> private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);获取的     
       * 如果缓存中没有，再开始bean的创建对象 
       * 标记当前bean已经被创建
       * 获取Bean的定义信息
       * 获取当前Bean依赖的其他Bean;如果有按照getBean()把依赖的Bean先创建出来； mbd.getDependsOn();
       * 启动单实例Bean的创建流程；return createBean(beanName, mbd, args);
         -Object bean = resolveBeforeInstantiation(beanName, mbdToUse);  //  让BeanPostProcessor先拦截返回代理对象；(Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.)
         -> AbstractAutowireCapableBeabFactory.applyBeanPostProcessorsBeforeInstantiation() 
         先执行InstantiationAwareBeanPostProcessor   
         ->Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);    
         -> if (result != null )  bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
       * 如果前面的InstantiationAwareBeanPostProcessor没有返回代理对象；继续调用下面
         * Object beanInstance = doCreateBean(beanName, mbdToUse, args);创建Bean
         * 创建instanceWrapper = createBeanInstance(beanName, mbd, args);   利用工厂方法或者对象的构造器创建出Bean实例；    
            ->applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);   ->
            ->MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition(mbd, beanType, beanName);   
            ->populateBean(beanName, mbd, instanceWrapper); //Bean属性赋值        
                 -> 赋值之前：
                 
                 ```
                 if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {           
                                               for (BeanPostProcessor bp : getBeanPostProcessors()) {         
                                                 if (bp instanceof InstantiationAwareBeanPostProcessor) {           
                                    					InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;            
                                    					if (!ibp.postProcessAfterInstantiation(bw.getWrappedInstance(), beanName)) {              
                                    						continueWithPropertyPopulation = false;              
                                    						break;           
                                    					}          
                                    				}           
                                    			}   
                 ```   

         * 应用Bean属性的值；为属性利用setter方法等进行赋值； 
         exposedObject = initializeBean(beanName, exposedObject, mbd); 初始化    
         ->invokeAwareMethods(beanName, bean);   //执行Aware接口方法       BeanNameAware\BeanClassLoaderAware\BeanFactoryAware     
         ->wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName); //执行后置处理器初始化之前    
            ->result = beanProcessor.postProcessBeforeInitialization(result, beanName);
         ->invokeInitMethods(beanName, wrappedBean, mbd); //执行初始化方法    
           是否是InitializingBean接口的实现；执行接口规定的初始化； 是否自定义初始化方法； 
         ->wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);// 执行后置处理器初始化之后   
           ->BeanPostProcessor.postProcessAfterInitialization(); 
       * 注册Bean的销毁方法；
     * 将创建的Bean添加到缓存中singletonObjects；
     ioc容器就是这些Map；很多的Map里面保存了单实例Bean，环境信息。。。。； 所有Bean都利用getBean创建完成以后；  
           
              
     
     
 
 
 beanFactory.preInstantiateSingletons()的源码 【DefaultListableBeanFactory】
 ```java_holder_method_tree
        if (this.logger.isDebugEnabled()) {
			this.logger.debug("Pre-instantiating singletons in " + this);
		}

		// Iterate over a copy to allow for init methods which in turn register new bean definitions.
		// While this may not be part of the regular factory bootstrap, it does otherwise work fine.
		List<String> beanNames = new ArrayList<String>(this.beanDefinitionNames);

		// Trigger initialization of all non-lazy singleton beans...
		for (String beanName : beanNames) {
			RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
			if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
				if (isFactoryBean(beanName)) {
					final FactoryBean<?> factory = (FactoryBean<?>) getBean(FACTORY_BEAN_PREFIX + beanName);
					boolean isEagerInit;
					if (System.getSecurityManager() != null && factory instanceof SmartFactoryBean) {
						isEagerInit = AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
							@Override
							public Boolean run() {
								return ((SmartFactoryBean<?>) factory).isEagerInit();
							}
						}, getAccessControlContext());
					}
					else {
						isEagerInit = (factory instanceof SmartFactoryBean &&
								((SmartFactoryBean<?>) factory).isEagerInit());
					}
					if (isEagerInit) {
						getBean(beanName);
					}
				}
				else {
				// 不是工厂Bean 走这边
					getBean(beanName);
				}
			}
		}

		// Trigger post-initialization callback for all applicable beans...
		for (String beanName : beanNames) {
			Object singletonInstance = getSingleton(beanName);
			if (singletonInstance instanceof SmartInitializingSingleton) {
				final SmartInitializingSingleton smartSingleton = (SmartInitializingSingleton) singletonInstance;
				if (System.getSecurityManager() != null) {
					AccessController.doPrivileged(new PrivilegedAction<Object>() {
						@Override
						public Object run() {
							smartSingleton.afterSingletonsInstantiated();
							return null;
						}
					}, getAccessControlContext());
				}
				else {
					smartSingleton.afterSingletonsInstantiated();
				}
			}
		}
``` 
   		
 
###12、finishRefresh();
完成BeanFactory的初始化创建工作，IOC容器就创建完成 (Last step: publish corresponding event.)

 1. initLifecycleProcessor();初始化和生命周期相关的后置处理器(Initialize lifecycle processor for this context.)
    ->this.lifecycleProcessor = beanFactory.getBean(LIFECYCLE_PROCESSOR_BEAN_NAME, LifecycleProcessor.class);
    默认从容器中找是否有lifecycleProcessor的组件[LifecycleProcessor]    
    没有就   ->DefaultLifecycleProcessor defaultProcessor = new DefaultLifecycleProcessor();
       		->	defaultProcessor.setBeanFactory(beanFactory);
    并加入到容器中；
 2. getLifecycleProcessor().onRefresh(); 拿到前面定义的生命周期处理器(BeanFactory)(Propagate refresh to lifecycle processor first.) 回调onRefresh()；
 3.publishEvent(new ContextRefreshedEvent(this));发布容器刷新完成事件；Publish the final event.
 4. LiveBeansView.registerApplicationContext(this);     // Participate in LiveBeansView MBean, if active.
 
 
 
 ##总结 
  ### spring容器在启动的时候，会先保存所有注册进来的Bean定义信息；
   * xml注册bean：<bean></bean>
   * 注解注册bean : @Controller,@Sevice,@Component,@Bean ....
  ### Spring容器会在合适的时机创建一些Bean 
   * 用到这个bean的时候，利用getBean()创建bean,创建好以后保存在容器中
   * finishBeanFactoryInitialization(),负责统一创建剩下的所有bean
  ### 后置处理器 
   * 每一个bean创建完成，都会使用各种后置处理器进行处理，来增强bean的功能
     AutowiredAnnotationBeanPostProcessor:处理自动注入   
     AnnotationAwareAspectJAutoProxyCreator:来做AOP功能；   
     xxx....    
     增强的功能注解：    
        AsyncAnnotationBeanPostProcessor ....
        
   ### 事件驱动模型；
   * ApplicationListener；事件监听；
   * ApplicationEventMulticaster；事件派发：       
   		





































   			