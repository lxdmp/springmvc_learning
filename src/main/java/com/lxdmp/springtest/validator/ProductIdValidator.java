// ProductId验证注解
package com.lxdmp.springtest.validator;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lxdmp.springtest.domain.Product;
import com.lxdmp.springtest.exception.ProductNotFoundException;
import com.lxdmp.springtest.service.ProductService;
import org.springframework.context.MessageSource;
import javax.servlet.http.HttpServletRequest;

public class ProductIdValidator implements ConstraintValidator<ProductId, String>
{
	@Autowired
	private ProductService productService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private  HttpServletRequest request;

	public void initialize(ProductId constraintAnnotation)
	{
		// intentionally left blank; 
		// this is the place to initialize the constraint annotation for any sensible default values.
	}
	
	public boolean isValid(String value, ConstraintValidatorContext context)
	{
		Product product = productService.getProductById(value);
		if(product==null){
			return true;
		}else{
			String message_template = context.getDefaultConstraintMessageTemplate();
			String s = messageSource.getMessage(message_template, new String[]{value}, request.getLocale());
			context.disableDefaultConstraintViolation(); 
			context.buildConstraintViolationWithTemplate(s).addConstraintViolation();
			return false;
		}
	}
}

