package com.unionia.ldap.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:settings.properties")
public class HibernateConfig {

	private static final String[] PACKAGES_TO_SCAN = { "com.unionia.ldap" };

	protected static final String DATABASE_DRIVER = "db.driver";
	protected static final String DATABASE_PASSWORD = "db.password";
	protected static final String DATABASE_URL = "db.url";
	protected static final String DATABASE_USERNAME = "db.username";

	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String HIBERNATE_USE_SQL_COMMENTS = "hibernate.use_sql_comments";

	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getRequiredProperty(DATABASE_DRIVER));
		dataSource.setUrl(env.getRequiredProperty(DATABASE_URL));
		dataSource.setUsername(env.getRequiredProperty(DATABASE_USERNAME));
		dataSource.setPassword(env.getRequiredProperty(DATABASE_PASSWORD));

		return dataSource;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(PACKAGES_TO_SCAN);

		Properties jpaProperties = new Properties();
		jpaProperties.put(HIBERNATE_DIALECT, env.getRequiredProperty(HIBERNATE_DIALECT));
		jpaProperties.put(HIBERNATE_FORMAT_SQL, env.getRequiredProperty(HIBERNATE_FORMAT_SQL));
		jpaProperties.put(HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(HIBERNATE_HBM2DDL_AUTO));
		jpaProperties.put(HIBERNATE_SHOW_SQL, env.getRequiredProperty(HIBERNATE_SHOW_SQL));
		jpaProperties.put(HIBERNATE_USE_SQL_COMMENTS, env.getRequiredProperty(HIBERNATE_USE_SQL_COMMENTS));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
