package com.lxdmp.springtest.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.web.csrf.CsrfToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value = "/token")
public class TokenController
{ 
	private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

	@RequestMapping(value="/csrf", method=RequestMethod.GET)
	@ResponseBody
	public String getCsrfToken(HttpServletRequest request)
	{
		/*
	    CsrfToken token = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
	    return token.getToken();
		*/
		return "";
	}
}

