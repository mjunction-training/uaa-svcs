package com.training.mjunction.sso.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.training.mjunction.sso.data.domain.OAuth2Client;

@Repository
public interface OAuth2ClientRepository extends JpaRepository<OAuth2Client, String> {

	Optional<OAuth2Client> findByClientIdIgnoreCase(String clientId);

}
