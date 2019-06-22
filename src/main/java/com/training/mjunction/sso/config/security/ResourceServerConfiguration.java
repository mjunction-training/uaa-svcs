package com.training.mjunction.sso.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends GlobalMethodSecurityConfiguration implements ResourceServerConfigurer {

	@Autowired
	private ResourceServerTokenServices tokenServices;

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.requestMatchers().antMatchers("/userinfo", "/me", "/user").and().authorizeRequests().anyRequest()
				.authenticated();
	}

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}

	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) {
		resources.resourceId("openid").accessDeniedHandler(new OAuth2AccessDeniedHandler())
				.tokenServices(tokenServices);
	}
}