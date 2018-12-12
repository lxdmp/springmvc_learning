package com.lxdmp.springtest.auth;

import java.util.Iterator;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import com.lxdmp.springtest.service.UserService;
import org.apache.log4j.Logger;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	private static final Logger logger = Logger.getLogger(SecurityConfig.class);

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception
	{
		return super.authenticationManager();
	}

	/*
	 * Spring Security约定的权限是人员、角色两张表,
	 * roles/hasRole方法默认有前缀"ROLE_"前缀.
	 *
	 * 若要实现更灵活的人员、角色、功能三张表(可用功能代替默认约定的角色概念),
	 * 使用authorities/hasAuthority代替上述的roles方法,这样的字串没有任何前缀或后缀.
	 */
	@Autowired
	UserService userService;

	@Bean
	UserDetailsService customUserDetailsService()
	{
		return new UserDetailsService(){
			@Override
			public UserDetails loadUserByUsername(String username)
			{
				com.lxdmp.springtest.domain.User user = userService.queryUserByName(username);
				if(user==null)
					return null;
				return new User(
					user.getUserName(), 
					user.getUserPasswd(), 
					user.getUserPriviledges()
				);
			}
		};
	}

	@Bean
	AuthenticationProvider customAuthenticationProvider()
	{
		return new AuthenticationProvider(){
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException
			{
				String username = authentication.getName();
				String password = (String)authentication.getCredentials();

				UserDetails userDetails = customUserDetailsService().loadUserByUsername(username);
				if(userDetails==null)
					return null;

				if( !username.equals(userDetails.getUsername()) || 
					!password.equals(userDetails.getPassword()) )
					return null;

				return new UsernamePasswordAuthenticationToken(
					userDetails, password, userDetails.getAuthorities()
				);
			}
		
			@Override
			public boolean supports(Class<?> arg0)
			{
				return true;
			}
		};
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception
	{
		/*
		auth.inMemoryAuthentication().withUser("test").password("123").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("root123").roles("USER", "ADMIN");
		*/

		/*
		auth.inMemoryAuthentication().withUser("test").password("123").authorities("CUSTOM_FORMAT");
		auth.inMemoryAuthentication().withUser("admin").password("root123").authorities(
			"CUSTOM_FORMAT", "ADD_PRODUCT"
		);
		*/

		auth.userDetailsService(customUserDetailsService()); // 订制认证的用户信息来源
		auth.authenticationProvider(customAuthenticationProvider()); // 订制认证的方式
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity
			.authorizeRequests()
				.antMatchers("/", "/products", "/products/list").permitAll() // 不需要权限的页面
				//.antMatchers("/**/add").access("hasRole('ADMIN')") // url的权限匹配按照声明顺序进行,
				//.antMatchers("/**/format").access("hasRole('USER')") // 故可按照由精确到模糊的顺序声明.
				//.antMatchers("/**/add").access("hasAuthority('ADD_PRODUCT')") // url的权限匹配按照声明顺序进行,
				//.antMatchers("/**/format").access("hasAuthority('CUSTOM_FORMAT')") // 故可按照由精确到模糊的顺序声明.
				.anyRequest().authenticated()
				.and()
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
				.permitAll()
				.and()
			.exceptionHandling()
				.accessDeniedPage("/login?accessDenied");
			/*
				.and()
			.csrf() // 跨域请求,使能该功能
				.disable();
			*/
	}
}

