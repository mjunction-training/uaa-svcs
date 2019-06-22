package com.training.mjunction.sso.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.training.mjunction.sso.service.UserDetailsService;

@Configuration
public class UserLoginConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login", "/error").permitAll().anyRequest().authenticated().and()
				.formLogin().permitAll();
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/hystrix.stream", "/actuator/**", "/js/**", "/css/**", "/*.html", "/*.htm",
				"/*.jsp", "/swagger-ui.html", "/v2/api-docs", "/favicon.ico", "/webjars/**");
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.parentAuthenticationManager(authenticationManagerBean()).userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}
}