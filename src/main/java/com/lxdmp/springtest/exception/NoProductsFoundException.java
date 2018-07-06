package com.lxdmp.springtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * @ResponseStatus注解方式的异常的异常信息固定(reason字段),
 * 若要动态设定异常信息,需要使用@ExceptionHandler机制.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No products found")
public class NoProductsFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1;
}

