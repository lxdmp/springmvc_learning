package com.lxdmp.springtest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.Logger;

public class ProcessTimeLogInterceptor implements HandlerInterceptor
{
	private static final Logger logger = Logger.getLogger(ProcessTimeLogInterceptor.class);
	private static final String key_name = "startTime";
	
	public boolean preHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler)
	{
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
	}
}

