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
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import com.lxdmp.springtest.interceptor.ProcessTimeLogInterceptor;
import com.lxdmp.springtest.interceptor.ProductsInXmlViewInterceptor;
//import com.lxdmp.springtest.interceptor.FileUploadInterceptor;

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

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.MessageSource;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableWebMvc
@ComponentScan({
	"com.lxdmp.springtest.controller", 
	"com.lxdmp.springtest.interceptor", 
	"com.lxdmp.springtest.formatter", 
	"com.lxdmp.springtest.exception"
})
public class WebApplicationContextConfig extends WebMvcConfigurerAdapter
{
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		super.configureDefaultServletHandling(configurer);
		configurer.enable();
	}

	// - 添加interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		super.addInterceptors(registry);
		registry.addInterceptor(new ProcessTimeLogInterceptor());
		registry.addInterceptor(new ProductsInXmlViewInterceptor());

		/*
		 * InterceptorRegistration addPathPatterns(String patterns) : 显式声明拦截器作用的路径;
		 * InterceptorRegistration excludePathPatterns(String patterns) : 显式声明拦截器不作用的路径.
		 */
		// 例如:
		// registry.addInterceptor(...).addPathPatterns("/**/market/products/specialOffer"),
		// 只作用于以该字串结尾的路径请求.

		/*
		 * 另外,可以在Interceptor接口的preHandle()中进行请求的重定向(HttpServletRequest.sendRedirect()).
		 */

		// 后续使用validator机制来对上传文件进行验证,不再使用interceptor的方法.
		/*
		registry.addInterceptor(new FileUploadInterceptor(
			new String[] {"jpg", "jpeg", "png"}, 
			10*1024
		)).addPathPatterns("/products/add");
		*/

		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);
	}

	// - 添加formatter
	@Override
	public void addFormatters(FormatterRegistry registry)
	{
		super.addFormatters(registry);
		registry.addFormatterForFieldType(CustomFormatTestObj.class, new CustomFormatTestFormatter());  // 将请求参数格式化为自定义的实体类(比如解码一个自定义编码的字符串)
	}

	// - 指定视图解析器
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver()
	{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	// - 指定模板风格的视图解析器(tiles,并指定了优先级,越小优先级越高)
	@Bean
	public UrlBasedViewResolver viewResolver()
	{
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

	// - 指定ContentNegotiatingViewResolver的json/xml实现
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

	// - 指定静态文件路径
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/img/**").addResourceLocations("/resources/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
	}

	// - 页面内容导入
	@Bean
	public MessageSource messageSource()
	{
		ResourceBundleMessageSource resource = new ResourceBundleMessageSource();
		resource.setBasename("messages");
		return resource;
	}

	// - 文件上传功能
	@Bean
	public CommonsMultipartResolver multipartResolver()
	{
		CommonsMultipartResolver resolver=new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}

	// - 国际化
	@Bean
	public LocaleResolver localeResolver()
	{
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(new Locale("zh"));
		return resolver;
	}

	// - Validator
	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator()
	{
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

	@Override
	public Validator getValidator()
	{
		return validator();
	}
}

