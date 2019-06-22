package com.training.mjunction.sso.config;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import brave.sampler.Sampler;

@Configuration
public class AppConfig {

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}

	@Bean
	public ObjectMapper objetMapper() {
		return new ObjectMapper();
	}

	@Bean
	public Validator validator() {
		return Validation.buildDefaultValidatorFactory().getValidator();
	}

}
