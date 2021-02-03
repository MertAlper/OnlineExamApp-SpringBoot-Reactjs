package com.example.examapp.demo.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db-credentials.properties")
public class DatabaseConfig {

    @Autowired
    private Environment env;

    @Bean
    public ComboPooledDataSource comboPooledDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();

        dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
        dataSource.setDriverClass(env.getProperty("jdbc.driver"));
        dataSource.setUser(env.getProperty("jdbc.username"));
        dataSource.setPassword(env.getProperty("jdbc.password"));

        dataSource.setMinPoolSize(getIntegerValue(env.getProperty("connection.minPoolSize")));
        dataSource.setMaxPoolSize(getIntegerValue(env.getProperty("connection.maxPoolSize")));
        dataSource.setMaxIdleTime(getIntegerValue(env.getProperty("connection.maxIdleTime")));
        dataSource.setInitialPoolSize(getIntegerValue(env.getProperty("connection.initialPoolSize")));

        return dataSource;
    }

    @Bean
    @Autowired
    public LocalSessionFactoryBean localSessionFactoryBean(DataSource dataSource){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));

        Properties pros = new Properties();
        pros.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        pros.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        pros.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        sessionFactory.setHibernateProperties(pros);
        sessionFactory.setDataSource(dataSource);

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager hibernateTransactionManager(SessionFactory sessionFactory){
        HibernateTransactionManager transactionManager =
                new HibernateTransactionManager(sessionFactory);

        return transactionManager;
    }

    /**
     * This method is for parsing an integer value from given string
     * It will be used to parse string pooling properties that comes from properties file.
     * @param value, the string that contains the integer value.
     * @return, the integer value of string
     */
    private int getIntegerValue(String value){
        int intValue = 0;

        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException exp){
            exp.printStackTrace();
        }

        return intValue;
    }
}
