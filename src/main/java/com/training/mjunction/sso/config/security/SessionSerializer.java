package com.training.mjunction.sso.config.security;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SessionSerializer extends JdkSerializationRedisSerializer {

	@Override
	public Object deserialize(final byte[] bytes) {
		try {
			final Object obj = super.deserialize(bytes);
			return obj;
		} catch (final SerializationException se) {
			// whenever inexistent classes from other service are deserialized they end up
			// here
			log.debug("Ignore: ", se.getRootCause());
		}

		return null;
	}

	@Override
	public byte[] serialize(final Object object) {
		return super.serialize(object);
	}

}