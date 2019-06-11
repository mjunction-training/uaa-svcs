package com.training.mjunction.sso.data.domain;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.userdetails.UserDetails;

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
@EqualsAndHashCode(callSuper = false, of = "username")
@Table(name = "user", schema = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }), @UniqueConstraint(columnNames = { "phone" }) })
public class User extends Auditable<String> implements UserDetails {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "username")
	private String username;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "phone", nullable = false, unique = true)
	private String phone;
	@Builder.Default
	@Column(name = "account_non_expired")
	private boolean accountNonExpired = true;
	@Builder.Default
	@Column(name = "account_non_locked")
	private boolean accountNonLocked = true;
	@Builder.Default
	@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired = true;
	@Builder.Default
	@Column(name = "enabled")
	private boolean enabled = true;
	@OrderBy
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Role.class)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "username"), inverseJoinColumns = @JoinColumn(name = "authority"))
	private SortedSet<Role> authorities = new TreeSet<>();
}