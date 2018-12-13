package com.lxdmp.springtest.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping(value = "/priviledges")
@PreAuthorize("hasAuthority('权限管理')")
public class PriviledgeController
{
	@RequestMapping
	public String get(HttpServletRequest request)
	{
		return "";
	}
}

