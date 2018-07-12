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
	/*
	 * Spring Security约定的权限是人员、角色两张表,
	 * roles/hasRole方法默认有前缀"ROLE_"前缀.
	 *
	 * 若要实现更灵活的人员、角色、功能三张表(可用功能代替默认约定的角色概念),
	 * 使用authorities/hasAuthority代替上述的roles方法,这样的字串没有任何前缀或后缀.
	 */

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

