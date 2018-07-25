// MultipartFileMax验证注解
package com.lxdmp.springtest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.context.MessageSource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.RequestContextUtils;

public class MultipartFileMaxValidator implements ConstraintValidator<MultipartFileMax, MultipartFile>
{
	private long max_limit;

	public void initialize(MultipartFileMax constraintAnnotation)
	{
		// intentionally left blank; 
		// this is the place to initialize the constraint annotation for any sensible default values.
		this.max_limit = constraintAnnotation.value();
	}
	
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context)
	{
		return (value.getSize()<=this.max_limit);
	}
}

