package com.training.mjunction.sso.config.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@FrameworkEndpoint
public class RevokeTokenEndpoint {

	@Resource(name = "consumerTokenServices")
	private ConsumerTokenServices tokenServices;

	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
	public void revokeToken(final HttpServletRequest request) {
		final String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.contains("Bearer")) {
			final String tokenId = authorization.substring("Bearer".length() + 1);
			tokenServices.revokeToken(tokenId);
		}
	}

}