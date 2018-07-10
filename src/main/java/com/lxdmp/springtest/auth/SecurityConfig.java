package com.lxdmp.springtest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.inMemoryAuthentication().withUser("test").password("123").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("root123").roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity
			.formLogin()
				.loginPage("/login") // default is /login with an HTTP get
				.usernameParameter("userId") // default is username
				.passwordParameter("password") // default is password
				.defaultSuccessUrl("/")
				.failureUrl("/login?error") // default is /login?error
				//.loginProcessingUrl("/authentication/login/process"); // default is /login
				.permitAll()
				.and()
			.logout()
				//.deleteCookies("remove")
				//.invalidateHttpSession(false)
				//.logoutUrl("/custom-logout") // default is /logout
				.logoutSuccessUrl("/login?logout")
				.and()
			.exceptionHandling()
				.accessDeniedPage("/login?accessDenied")
				.and()
			.authorizeRequests()
				.antMatchers("/**/add").access("hasRole('ADMIN')") // url的权限匹配按照声明顺序进行,
				.antMatchers("/**/format").access("hasRole('USER')") // 故可按照由精确到模糊的顺序声明.
				.antMatchers("/**").permitAll()
				.and();
			/*
			.csrf()
				.disable();
			*/
	}
}

