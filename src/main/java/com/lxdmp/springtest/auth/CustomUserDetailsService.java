package com.lxdmp.springtest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import com.lxdmp.springtest.service.UserService;

public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	UserService userService;

	public UserDetails loadUserByUsername(String username)
	{
		com.lxdmp.spring.domain.User user = userService.queryUserByName(username);
		return new User(user.getUserName(), user.getUserPasswd(), user.getUserPriviledges());
	}
}

