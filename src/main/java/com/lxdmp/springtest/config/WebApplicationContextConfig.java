// Controller层面的配置
package com.lxdmp.springtest.config;

import java.util.*;

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
import com.lxdmp.springtest.interceptor.ProductsInXmlViewInterceptor;

import org.springframework.format.FormatterRegistry;
import com.lxdmp.springtest.formatter.CustomFormatTestFormatter;
import com.lxdmp.springtest.domain.CustomFormatTestObj;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.view.xml.MarshallingView;
import com.lxdmp.springtest.domain.Product;
import com.lxdmp.springtest.dto.ProductList;

@Configuration
@EnableWebMvc
@ComponentScan({"com.lxdmp.springtest.controller", "com.lxdmp.springtest.interceptor", "com.lxdmp.springtest.formatter"})
public class WebApplicationContextConfig extends WebMvcConfigurerAdapter
{
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		super.configureDefaultServletHandling(configurer);

		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		// 添加interceptor
		super.addInterceptors(registry);
		registry.addInterceptor(new ProcessTimeLogInterceptor());
		registry.addInterceptor(new ProductsInXmlViewInterceptor());
	}

	@Override
	public void addFormatters(FormatterRegistry registry)
	{
		// 添加formatter
		super.addFormatters(registry);
		registry.addFormatterForFieldType(CustomFormatTestObj.class, new CustomFormatTestFormatter());  // 将请求参数格式化为自定义的实体类(比如解码一个自定义编码的字符串)
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver()
	{
		// 指定视图解析器
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public UrlBasedViewResolver viewResolver()
	{
		// 指定模板风格的视图解析器(tiles,并指定了优先级,越小优先级越高)
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(TilesView.class);
		viewResolver.setOrder(-2);
		return viewResolver;
	}

	@Bean
	public TilesConfigurer tilesConfigurer()
	{
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("/WEB-INF/layouts/definitions/tiles.xml");
		tilesConfigurer.setCheckRefresh(true);
		return tilesConfigurer;
	}

	// 指定ContentNegotiatingViewResolver的json/xml实现
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager)
	{
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		ArrayList<View> views = new ArrayList<>();
		views.add(jsonView());
		views.add(xmlView());
		resolver.setDefaultViews(views);
		return resolver;
	}

	public MappingJackson2JsonView jsonView()
	{
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setPrettyPrint(true);
		return jsonView;
	}

	public MarshallingView xmlView()
	{
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] {
			Product.class, 
			ProductList.class // jackson可以渲染json列表,xml要实现渲染列表需要再建立一个实体类与列表对应
		});
		MarshallingView xmlView = new MarshallingView(marshaller);
		return xmlView;
	}
}

