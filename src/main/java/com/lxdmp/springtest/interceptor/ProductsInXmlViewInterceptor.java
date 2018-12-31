package com.lxdmp.springtest.interceptor;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.lxdmp.springtest.entity.Product;
import com.lxdmp.springtest.dto.ProductList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductsInXmlViewInterceptor implements HandlerInterceptor
{
	private static final Logger logger = LoggerFactory.getLogger(ProductsInXmlViewInterceptor.class);
	
	@SuppressWarnings("unchecked")
	private static <T> T reinterpret_cast(Object obj)
	{
		return (T) obj;
	}

	public boolean preHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler)
	{
		return true;
	}
	
	public void postHandle(
		HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
	{
		/*
		 * 满足以下条件,则对视图中绑定数据作处理:
		 * - 请求路径以.xml结尾;
		 * - 视图名为products;
		 * - 视图中有键名为products的数据.
		 */
		final String suffix = ".xml";
		final String viewName = "products";
		final String dataKeyName = "products";

		if(!request.getRequestURL().toString().endsWith(suffix))
			return;
		if(modelAndView.getViewName()!=viewName)
			return;
		if(!modelAndView.getModel().containsKey(dataKeyName))
			return;

		logger.info("try to do replacement in ProductsInXmlView");
		List<Product> products = reinterpret_cast(modelAndView.getModel().remove(dataKeyName));
		ProductList list = new ProductList(products);
		modelAndView.getModel().put(dataKeyName, list);
		logger.info("replacement done in ProductsInXmlView");
	}

	public void afterCompletion(
		HttpServletRequest request, HttpServletResponse response, Object handler, Exception exceptionIfAny)
	{
	}
}

