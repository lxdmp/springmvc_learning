// DAO/Service层面的配置
package com.lxdmp.springtest.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import com.lxdmp.springtest.schedule.TaskScheduler;

import com.lxdmp.springtest.auth.SecurityConfig;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.lxdmp.springtest.amqp.AmqpConfig;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;

@Configuration
@EnableTransactionManagement
@ComponentScan({
	"com.lxdmp.springtest.entity", 
	"com.lxdmp.springtest.service", 
	"com.lxdmp.springtest.schedule", 
})
@EnableScheduling
@Import({
	SecurityConfig.class, 
	AmqpConfig.class
})
@MapperScan(value="com.lxdmp.springtest.dao")
public class RootApplicationContextConfig implements SchedulingConfigurer
{
	private static final Logger logger = LoggerFactory.getLogger(RootApplicationContextConfig.class);

	/*
	 * 数据源配置
	 */

	@Autowired
	//@Qualifier("hsqlDataSource")
	@Qualifier("mysqlDataSource")
	private DataSource dataSource;

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception
	{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(this.dataSource);
		return sessionFactory.getObject();
	}

	@Bean
	public NamedParameterJdbcTemplate getJdbcTemplate()
	{
		return new NamedParameterJdbcTemplate(this.dataSource);
	}

	@Bean
	public PlatformTransactionManager txManager()
	{
		 return new DataSourceTransactionManager(this.dataSource);
	}

	@Bean(name={"hsqlDataSource"})
	public DataSource hsqlDataSource()
	{
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("db/hsql/create-table.sql")
			.addScript("db/hsql/insert-data.sql")
			.build();
		return db;
	}

	@Bean(name={"mysqlDataSource"})
	public DriverManagerDataSource mysqlDataSource()
	{
		Properties props = new Properties();
		FileInputStream fis = null;
		DriverManagerDataSource mysqlDataSource = null;
		try {
			String mysql_config_file_name = "mysql.properties";
			String class_path = RootApplicationContextConfig.class.getClassLoader().getResource("/").toURI().getPath();
			fis = new FileInputStream(class_path+"/"+mysql_config_file_name);
			props.load(fis);

			mysqlDataSource = new DriverManagerDataSource();
			mysqlDataSource.setDriverClassName(props.getProperty("dbDriverClass"));
			mysqlDataSource.setUrl(props.getProperty("dbUrl"));
			mysqlDataSource.setUsername(props.getProperty("dbUsr"));
			mysqlDataSource.setPassword(props.getProperty("dbPw"));
		}catch(Exception e){
			logger.error(e.toString());
		}
		return mysqlDataSource;
	}

	/*
	 * 计划任务配置
	 */
	@Autowired
	private Executor taskExecutor;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
	{
		taskRegistrar.setScheduler(this.taskExecutor);
	}

	@Bean(destroyMethod="shutdown")
	public Executor taskExecutor()
	{
		return Executors.newScheduledThreadPool(10);
	}
}

