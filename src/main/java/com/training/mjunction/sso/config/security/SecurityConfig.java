package com.training.mjunction.sso.config.security;

import java.security.KeyPair;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.filter.ForwardedHeaderFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Bean
	public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
		return new OAuth2AccessDeniedHandler();
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(
			@Value("${keystore.file:certs/selfsigned.jks}") final String keyStoreFile,
			@Value("${keystore.password:foobar}") final String keyStorePassword,
			@Value("${keystore.alies:test}") final String keyStoreAlies) {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		final KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource(keyStoreFile),
				keyStorePassword.toCharArray()).getKeyPair(keyStoreAlies);
		converter.setKeyPair(keyPair);
		return converter;
	}

	@Bean
	public FilterRegistrationBean forwardedHeaderFilter() {
		final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
		filterRegBean.setFilter(new ForwardedHeaderFilter());
		filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filterRegBean;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(8);
	}

}
