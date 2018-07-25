// MultipartFileNotEmpty验证注解
package com.lxdmp.springtest.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.context.MessageSource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.RequestContextUtils;

public class MultipartFileNotEmptyValidator implements ConstraintValidator<MultipartFileNotEmpty, MultipartFile>
{
	public void initialize(MultipartFileNotEmpty constraintAnnotation)
	{
	}
	
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context)
	{
		return !value.isEmpty();
	}
}

