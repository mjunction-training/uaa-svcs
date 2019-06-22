package com.training.mjunction.sso.api;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.mjunction.sso.data.domain.User;

@RestController
public class UserController {

	@GetMapping({ "/user", "/me", "/userinfo" })
	@PreAuthorize("#oauth2.hasScope('read')")
	public Map<String, String> user(final Principal principal) {
		final User user = (User) principal;
		final Map<String, String> map = new LinkedHashMap<>();
		map.put("name", user.getFirstName() + " " + user.getLastName());
		map.put("email", user.getEmail());
		return map;
	}

}