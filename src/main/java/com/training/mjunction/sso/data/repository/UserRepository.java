package com.training.mjunction.sso.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.mjunction.sso.data.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsernameIgnoreCase(String username);

	Optional<User> findByEmailIgnoreCase(String email);

	Optional<User> findByPhoneIgnoreCase(String email);
}
