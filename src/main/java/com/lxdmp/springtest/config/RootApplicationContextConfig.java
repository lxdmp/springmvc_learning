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

import org.apache.log4j.Logger;

@Configuration
@ComponentScan({
	"com.lxdmp.springtest.domain", 
	"com.lxdmp.springtest.service", 
	"com.lxdmp.springtest.schedule"
})
@EnableScheduling
@Import({ SecurityConfig.class })
public class RootApplicationContextConfig implements SchedulingConfigurer
{
	private static final Logger logger = Logger.getLogger(RootApplicationContextConfig.class);

	@Autowired
	//@Qualifier("hsqlDataSource")
	@Qualifier("mysqlDataSource")
	DataSource dataSource;

	@Bean
	public NamedParameterJdbcTemplate getJdbcTemplate()
	{
		return new NamedParameterJdbcTemplate(this.dataSource);
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
			logger.error(e);
		}
		return mysqlDataSource;
	}
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
	{
		taskRegistrar.setScheduler(taskExecutor());
	}

	@Bean(destroyMethod="shutdown")
	public Executor taskExecutor()
	{
		return Executors.newScheduledThreadPool(10);
	}
}

