package com.training.mjunction.sso.actuator;

import java.util.Date;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Default Service health indicator to be used. This indicator will be used in
 * addition to the spring actuator '/health' endpoint.
 *
 * Note: It's completely optional for applications if it doesn't need to do
 * anything extra for default health other than spring health endpoint.
 */
@Component
public class SvcsHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		final StringBuilder msg = new StringBuilder("Service healthcheck for instance:")
				.append(System.identityHashCode(this)).append(" PASSED @ :").append(new Date());
		return Health.up().withDetail("description", msg.toString()).build();
	}

}
