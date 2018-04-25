package com.zx.aop.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 声明式事务：
 *
 *  步骤：
 *    1.导入相关依赖 ： 数据源，数据驱动，spring-jdbc模块
 *    2.配置数据源、JdbcTemplate(Spring提供的数据库简化操作工具)操作数据库
 *    3.要在需要开启事务的方法上加@Transactional，表示当前事务是一个事务方法
 *    4.@EnableTransactionManager开启基于注解的事务管理功能
 *    5.配置事务管理器来控制事务
 *       @Bean
 *       public PlatformTransactionManager platformTransactionManager() {
 *       return new DataSourceTransactionManager();
 *       }
 *
 *   原理：
 *   @see  EnableTransactionManagement
 *   1.  EnableTransactionManagement
 *        利用TransactionManagementConfigurationSelector给容器导入组件
 *
 *
 *
 */
@EnableTransactionManagement
@Configuration
@ComponentScan("com.zx.aop.tx")
@PropertySource("classpath:db.properties")
public class MainConfigOfTx {

    @Value("${db.userName}")
    private String userName;

    @Value("${db.password}")
    private String password;

    @Value("${db.DriverClass}")
    private String driverClass;

    @Value("${db.jdbcUrl}")
    private String dbJdbcUrl;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(dbJdbcUrl);
        dataSource.setUser(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClass(driverClass);
        return dataSource;
    }

    /**
     * 使用配置类里面的dataSOurce()方法会直接从容器中获取dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception{
        JdbcTemplate jdbcTemplate =new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    /**
     * 开启事务需要将事务管理器平台注册到容器中
     * @return
     */
    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager();
    }

}
