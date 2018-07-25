// MultipartFileType验证注解
package com.lxdmp.springtest.validator;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.context.MessageSource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.util.StringUtils;

public class MultipartFileTypeValidator implements ConstraintValidator<MultipartFileType, MultipartFile>
{
	private List<String> fileTypesPermitted = new ArrayList<String>();

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private  HttpServletRequest request;

	public void initialize(MultipartFileType constraintAnnotation)
	{
		// intentionally left blank; 
		// this is the place to initialize the constraint annotation for any sensible default values.
		String[] fileTypesPermitted = constraintAnnotation.value();
		for(int i=0; i<fileTypesPermitted.length; ++i)
		{
			String suffix = fileTypesPermitted[i];
			int firstIndex = suffix.indexOf(".");
			int lastIndex = suffix.lastIndexOf(".");
			if(firstIndex>=0 && lastIndex>=0)
			{
				if(firstIndex!=lastIndex){
					continue;
				}else{
					if(firstIndex>0)
						continue;
					else
						suffix = suffix.substring(1, suffix.length());
				}
			}
			this.fileTypesPermitted.add(suffix);
		}
	}
	
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context)
	{
		String fileName = multipartFile.getOriginalFilename();
		for(int i=0; i<this.fileTypesPermitted.size(); ++i)
		{
			String fileTypePermitted = this.fileTypesPermitted.get(i);
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			if(fileTypePermitted.equalsIgnoreCase(suffix))
				return true;
		}

		String message_template = context.getDefaultConstraintMessageTemplate();
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String tips = StringUtils.arrayToCommaDelimitedString(this.fileTypesPermitted.toArray());
		String s = messageSource.getMessage(
			message_template.replace("{", "").replace("}", ""), 
			new String[]{tips}, locale
		);
		context.disableDefaultConstraintViolation(); 
		context.buildConstraintViolationWithTemplate(s).addConstraintViolation();
        return false;
	}
}

