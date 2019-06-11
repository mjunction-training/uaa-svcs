package com.training.mjunction.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.mjunction.sso.data.domain.OAuth2Client;
import com.training.mjunction.sso.data.repository.OAuth2ClientRepository;

@Service
@CacheConfig(cacheNames = "client_details_cache")
public class OAuth2ClientDetailsServiceImpl implements OAuth2ClientDetailsService {

	@Autowired
	private OAuth2ClientRepository oAuth2ClientRepository;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(key = "#clientId", unless = "#result != null")
	public OAuth2Client loadClientByClientId(final String clientId) throws ClientRegistrationException {

		final OAuth2Client client = oAuth2ClientRepository.findByClientIdIgnoreCase(clientId).orElseGet(() -> null);

		if (client != null) {
			return client;
		}

		throw new ClientRegistrationException(clientId);
	}

	@Override
	@CachePut(key = "#result.clientId", unless = "#result != null")
	public OAuth2Client save(final OAuth2Client client) {
		return oAuth2ClientRepository.save(client);
	}

	@Override
	@CacheEvict(key = "#client.clientId", allEntries = false)
	public void delate(final String clientId) {

		final OAuth2Client client = oAuth2ClientRepository.findByClientIdIgnoreCase(clientId).orElseGet(() -> null);

		if (client == null) {
			throw new IllegalArgumentException("Client id not found");
		}

		oAuth2ClientRepository.delete(client);
	}

}
