package com.lxdmp.springtest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessTimeLogInterceptor implements HandlerInterceptor
{
	private static final Logger logger = LoggerFactory.getLogger(ProcessTimeLogInterceptor.class);
	private static final String key_name = "startTime";
	
	public boolean preHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler)
	{
		//logger.info("Locale : "+RequestContextUtils.getLocaleResolver(request).resolveLocale(request).toString());
		long startTime = System.currentTimeMillis();
		request.setAttribute(key_name, startTime);
		return true; // 若返回false,请求不会到达控制器(controller)和其他拦截器(interceptor).
	}
	
	public void postHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
	{
		String queryString = request.getQueryString() == null ?
			"" : "?" + request.getQueryString();
		String path = request.getRequestURL() + queryString;

		long startTime = (Long)request.getAttribute(key_name);
		long endTime = System.currentTimeMillis();
		logger.info(String.format("%s millisecond taken to process the request %s", endTime-startTime, path));
	}

	public void afterCompletion(
		HttpServletRequest request, HttpServletResponse response, Object handler, Exception exceptionIfAny)
	{
		String queryString = request.getQueryString() == null ?
			"" : "?" + request.getQueryString();
		String path = request.getRequestURL() + queryString;

		long startTime = (Long)request.getAttribute(key_name);
		long endTime = System.currentTimeMillis();
		logger.info(String.format("%s millisecond taken in total (controller and view) to process the request %s", endTime-startTime, path));
	}
}

