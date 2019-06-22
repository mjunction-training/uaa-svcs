package com.training.mjunction.sso.config.security;

import java.security.KeyPair;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityConfig {

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(
			@Value("${keystore.file:certs/selfsigned.jks}") final String keyStoreFile,
			@Value("${keystore.password:password}") final String keyStorePassword,
			@Value("${keystore.alies:selfsigned}") final String keyStoreAlies) {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		final KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource(keyStoreFile),
				keyStorePassword.toCharArray()).getKeyPair(keyStoreAlies);
		converter.setKeyPair(keyPair);
		return converter;
	}

	@Bean
	public TokenStore tokenStore(final JwtAccessTokenConverter jwtAccessTokenConverter) {
		return new JwtTokenStore(jwtAccessTokenConverter);
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices(final TokenStore tokenStore) {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setAccessTokenValiditySeconds(60 * 30);
		return defaultTokenServices;
	}

	@Bean
	public FilterRegistrationBean<OncePerRequestFilter> forwardedHeaderFilter() {
		final FilterRegistrationBean<OncePerRequestFilter> filterRegBean = new FilterRegistrationBean<>();
		filterRegBean.setFilter(new ForwardedHeaderFilter());
		filterRegBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return filterRegBean;
	}

	@Bean
	public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
		return new OAuth2AccessDeniedHandler();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(8);
	}

}
