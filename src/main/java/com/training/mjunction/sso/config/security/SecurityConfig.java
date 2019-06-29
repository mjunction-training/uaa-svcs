package com.training.mjunction.sso.config.security;

import java.security.KeyPair;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class SecurityConfig {

	@Bean
	public KeyPair keyPair(@Value("${keystore.file:certs/selfsigned.jks}") final String keyStoreFile,
			@Value("${keystore.password:password}") final String keyStorePassword,
			@Value("${keystore.alies:selfsigned}") final String keyStoreAlies) {
		return new KeyStoreKeyFactory(new ClassPathResource(keyStoreFile), keyStorePassword.toCharArray())
				.getKeyPair(keyStoreAlies);
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(final KeyPair keyPair) {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyPair);
		final DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
		accessTokenConverter.setUserTokenConverter(new SubjectAttributeUserTokenConverter());
		converter.setAccessTokenConverter(accessTokenConverter);
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

	/**
	 * Legacy Authorization Server does not support a custom name for the user
	 * parameter, so we'll need to extend the default. By default, it uses the
	 * attribute {@code user_name}, though it would be better to adhere to the
	 * {@code sub} property defined in the
	 * <a target="_blank" href="https://tools.ietf.org/html/rfc7519">JWT
	 * Specification</a>.
	 */
	private static class SubjectAttributeUserTokenConverter extends DefaultUserAuthenticationConverter {
		@Override
		public Map<String, ?> convertUserAuthentication(final Authentication authentication) {
			final Map<String, Object> response = new LinkedHashMap<>();
			response.put("sub", authentication.getName());
			if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
				response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
			}
			return response;
		}
	}

}
