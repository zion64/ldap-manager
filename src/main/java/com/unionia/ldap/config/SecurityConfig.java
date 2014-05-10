package com.unionia.ldap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ApplicationContext context;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password("password").roles("USER").and()
			.withUser("admin").password("admin").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
// @formatter:off
		http.authorizeRequests()
			.antMatchers("/login/**", "/login").permitAll()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/**").authenticated()
		.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/")
				.failureUrl("/loginfailed").permitAll()
		.and()
			.logout()
				.deleteCookies("JSESSIONID")
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
		.and()
        	.setSharedObject(ApplicationContext.class, context);
// @formatter:on
	}
	
}
