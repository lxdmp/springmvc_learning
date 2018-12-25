package com.lxdmp.springtest.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import javax.servlet.Filter;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class[] {
			RootApplicationContextConfig.class
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class[] {
			WebApplicationContextConfig.class
		};
	}

	@Override
	protected String[] getServletMappings()
	{
		return new String[] {"/"};
	}

	@Override
	protected Filter[] getServletFilters()
	{
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] {
			characterEncodingFilter
		};
	}
}

