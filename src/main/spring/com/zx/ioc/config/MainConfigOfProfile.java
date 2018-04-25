package com.zx.ioc.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zx.ioc.bean.Green;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @see Profile
 *  @Profile : spring可以根据当前的环境，动态的激活和切换一系列组件的功能
 *
 *   我们的配置一般分为：开发环境;测试环境;生产环境
 *
 *   系统中是用来@Profile注解，需要指定在哪个环境下才能加载到容器中，如果没有指定，则会全部加载到容器中
 *
 *   1、加了环境标识的bean只有这个环境被激活了才会加载到容器中
 *   2、如果@Profile写在了配置类上，只有指定相应环境的时候，整个配置类里面的所有配置才能生效
 *   3、没有标注环境标的bean任何情况下都会被加载
 *
 *
 */
@Profile("test")
@PropertySource("classpath:/db.properties")
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware{

    @Value("${db.userName}")
    private String userName;
    @Value("${db.password}")
    private String password;

    private StringValueResolver stringValueResolver;

    @Value("${db.DriverClass}")
    private String driverClass;



    @Profile("dev")
    @Bean
    public Green green() {
        return new Green();
    }



    @Profile("test")
    @Bean
    public DataSource dataSourceTest() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClass(driverClass);

        dataSource.setJdbcUrl("jdbc:mysql://localhost:3307/test");
        return dataSource;
    }

    @Profile("prod")
    @Bean
    public DataSource dataSourceProd() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClass(driverClass);

        dataSource.setJdbcUrl("jdbc:mysql://localhost:3307/obs");
        return dataSource;
    }

    @Profile("dev")
    @Bean
    public DataSource dataSourceDev() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClass(driverClass);

        dataSource.setJdbcUrl("jdbc:mysql://localhost:3307/scc");
        return dataSource;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.stringValueResolver = resolver;

        this.driverClass = resolver.resolveStringValue("db.DriverClass");;
    }
}
