package com.training.mjunction.sso.config.security;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

@FrameworkEndpoint
public class FrameworkEndpoints {

	@Autowired
	private KeyPair keyPair;

	@Autowired
	private TokenStore tokenStore;

	@Resource(name = "consumerTokenServices")
	private ConsumerTokenServices tokenServices;

	@PostMapping("/introspect")
	@ResponseBody
	public Map<String, Object> introspect(@RequestParam("token") final String token) {

		final OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);

		final Map<String, Object> attributes = new HashMap<>();

		if (accessToken == null || accessToken.isExpired()) {
			attributes.put("active", false);
			return attributes;
		}

		final OAuth2Authentication authentication = tokenStore.readAuthentication(token);

		attributes.put("active", true);
		attributes.put("exp", accessToken.getExpiration().getTime());
		attributes.put("scope", accessToken.getScope().stream().collect(Collectors.joining(" ")));
		attributes.put("sub", authentication.getName());

		return attributes;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
	public void revokeToken(final HttpServletRequest request) {

		final String authorization = request.getHeader("Authorization");

		if (authorization != null && authorization.contains("Bearer")) {
			final String tokenId = authorization.substring("Bearer".length() + 1);
			tokenServices.revokeToken(tokenId);
		}
	}

	@GetMapping("/.well-known/jwks.json")
	public @ResponseBody Map<String, Object> getKey(final Principal principal) {

		final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		final RSAKey key = new RSAKey.Builder(publicKey).build();

		return new JWKSet(key).toJSONObject();
	}

}