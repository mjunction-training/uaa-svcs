package com.training.mjunction.sso.data.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "authority")
@Table(name = "client_role", schema = "users")
public class OAuth2Role extends Auditable<String> implements GrantedAuthority, Comparable<OAuth2Role> {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "authority")
	private String authority;
	@Builder.Default
	@ManyToMany(mappedBy = "authorities")
	private Set<OAuth2Client> clients = new HashSet<>();

	@Override
	public int compareTo(final OAuth2Role o) {
		return authority.compareToIgnoreCase(o.authority);
	}
}
