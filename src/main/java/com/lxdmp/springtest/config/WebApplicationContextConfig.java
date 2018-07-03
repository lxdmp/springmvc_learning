package com.lxdmp.springtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import com.lxdmp.springtest.interceptor.ProcessTimeLogInterceptor;

import org.springframework.format.FormatterRegistry;
import com.lxdmp.springtest.formatter.CustomFormatTestFormatter;
import com.lxdmp.springtest.domain.CustomFormatTestObj;

@Configuration
@EnableWebMvc
@ComponentScan("com.lxdmp.springtest")
public class WebApplicationContextConfig extends WebMvcConfigurerAdapter
{
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(new ProcessTimeLogInterceptor());
	}

	@Override
	public void addFormatters(FormatterRegistry registry)
	{
		// 将请求参数格式化为自定义的实体类(比如解码一个自定义编码的字符串)
		registry.addFormatterForFieldType(CustomFormatTestObj.class, new CustomFormatTestFormatter()); 
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver()
	{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
}

