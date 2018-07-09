// DAO/Service层面的配置
package com.lxdmp.springtest.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import com.lxdmp.springtest.schedule.TaskScheduler;

import com.lxdmp.springtest.auth.SecurityConfig;

@Configuration
@ComponentScan({
	"com.lxdmp.springtest.domain", 
	"com.lxdmp.springtest.service", 
	"com.lxdmp.springtest.dto", 
	"com.lxdmp.springtest.schedule"
})
@EnableScheduling
@Import({ SecurityConfig.class })
public class RootApplicationContextConfig implements SchedulingConfigurer
{
	@Bean
	public DataSource dataSource()
	{
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("db/sql/create-table.sql")
			.addScript("db/sql/insert-data.sql")
			.build();
		return db;
	}

	@Bean
	public NamedParameterJdbcTemplate getJdbcTemplate()
	{
		return new NamedParameterJdbcTemplate(dataSource());
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

