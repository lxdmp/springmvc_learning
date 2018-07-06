package com.lxdmp.springtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProductNotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1;
	private String productId;

	public ProductNotFoundException(String productId){this.productId = productId;}

	public String getProductId(){return this.productId;}
	public void settProductId(String productId){this.productId = productId;}
}

