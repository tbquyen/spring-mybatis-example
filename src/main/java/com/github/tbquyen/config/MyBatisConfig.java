package com.github.tbquyen.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.github.tbquyen")
public class MyBatisConfig {
	@Autowired
	private DatabaseProperties databaseConfig;

	@Bean
	public DataSourceTransactionManager mybatisTransactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public SqlSessionFactory sessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		return sessionFactory.getObject();
	}

	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(databaseConfig.getDriverClassName());
		dataSource.setUrl(databaseConfig.getUrl());
		dataSource.setUsername(databaseConfig.getUsername());
		dataSource.setPassword(databaseConfig.getPassword());
		dataSource.setMaxIdle(databaseConfig.getMaxIdle());
		dataSource.setTestWhileIdle(databaseConfig.getTestWhileIdle());
		dataSource.setValidationQuery(databaseConfig.getValidationQuery());
		return dataSource;
	}
}
