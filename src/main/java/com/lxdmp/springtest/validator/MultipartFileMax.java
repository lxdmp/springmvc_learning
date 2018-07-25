// multipartfile验证注解
package com.lxdmp.springtest.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy=MultipartFileMaxValidator.class)
@Documented
public @interface MultipartFileMax
{
	long value() default 1024*1024*1024;
	String message() default "{com.lxdmp.springtest.validator.MultipartFile.Max.message}";
	Class<?>[] groups() default {};
	public abstract Class<? extends Payload>[] payload() default {};
}

