// ProductId验证注解
package com.lxdmp.springtest.validator;

import java.text.MessageFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lxdmp.springtest.domain.Product;
import com.lxdmp.springtest.exception.ProductNotFoundException;
import com.lxdmp.springtest.service.ProductService;

public class ProductIdValidator implements ConstraintValidator<ProductId, String>
{
	@Autowired
	private ProductService productService;

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
			String s = MessageFormat.format(context.getDefaultConstraintMessageTemplate(), value);
			context.disableDefaultConstraintViolation(); 
			context.buildConstraintViolationWithTemplate(s).addConstraintViolation();
			return false;
		}
	}
}

