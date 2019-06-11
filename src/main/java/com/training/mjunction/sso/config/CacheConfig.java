package com.training.mjunction.sso.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(@Value("${redis.host:localhost}") final String host,
			@Value("${redis.port:6379}") final int port) {
		final JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(host);
		factory.setPort(port);
		return factory;
	}

	@Bean
	public RedisTemplate<Object, Object> redisTemplate(final JedisConnectionFactory jedisConnectionFactor) {
		final RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactor);
		redisTemplate.setExposeConnection(true);
		return redisTemplate;
	}

	@Bean
	public RedisCacheManager cacheManager(final RedisTemplate<Object, Object> redisTemplate) {
		final RedisCacheManager manager = new RedisCacheManager(redisTemplate);
		manager.setCacheNames(Arrays.asList("client_details_cache", "users_cache"));
		manager.setTransactionAware(true);
		manager.setLoadRemoteCachesOnStartup(true);
		return manager;
	}

}
