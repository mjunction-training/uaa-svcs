package com.training.mjunction.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.training.mjunction.sso.data.domain.User;
import com.training.mjunction.sso.data.repository.UserRepository;

@Service
@CacheConfig(cacheNames = "users_cache")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	@Cacheable(key = "#username", unless = "#result != null")
	public User loadUserByUsername(final String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsernameIgnoreCase(username).orElseGet(() -> null);

		if (user != null) {
			return user;
		}

		user = userRepository.findByEmailIgnoreCase(username).orElseGet(() -> null);

		if (user != null) {
			return user;
		}

		user = userRepository.findByPhoneIgnoreCase(username).orElseGet(() -> null);

		if (user != null) {
			return user;
		}

		throw new UsernameNotFoundException(username);
	}

	@Override
	@CachePut(key = "#result.username", unless = "#result != null")
	public User save(final User user) {
		return userRepository.save(user);
	}

	@Override
	@CacheEvict(key = "#username", allEntries = false)
	public void delate(final String username) {

		final User user = userRepository.findByUsernameIgnoreCase(username).orElseGet(() -> null);

		if (user == null) {
			throw new IllegalArgumentException("User id not found");
		}

		userRepository.delete(user);
	}
}