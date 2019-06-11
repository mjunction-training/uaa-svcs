package com.training.mjunction.sso.data.domain;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = "clientId")
@Table(name = "oauth_client")
public class OAuth2Client extends Auditable<String> implements ClientDetails {
	private static final long serialVersionUID = 1L;
	@Id
	@Getter
	@Setter
	@Column(name = "client_id")
	private String clientId;
	@Getter
	@Setter
	@Column
	private String clientSecret;
	@Getter
	@Setter
	@Column
	private String additionalInfo;
	@Getter
	@Setter
	@Column(nullable = false)
	private Integer accessTokenValiditySeconds;
	@Getter
	@Setter
	@Column(nullable = false)
	private Integer refreshTokenValiditySeconds;
	@Getter
	@Setter
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private Set<String> scope = new TreeSet<>();
	@Getter
	@Setter
	@Builder.Default
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> resourceIds = new TreeSet<>();
	@Getter
	@Setter
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private Set<String> autoApproveScope = new TreeSet<>();
	@Getter
	@Setter
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private Set<String> authorizedGrantTypes = new TreeSet<>();
	@Getter
	@Setter
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private Set<String> registeredRedirectUri = new TreeSet<>();
	@Setter
	@OrderBy
	@Builder.Default
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = OAuth2Role.class)
	@JoinTable(name = "oauth2_client_role", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "authority"))
	private SortedSet<OAuth2Role> authorities = new TreeSet<>();

	@Override
	public Collection<GrantedAuthority> getAuthorities() {

		final Set<GrantedAuthority> set = new TreeSet<>();
		for (final OAuth2Role oAuth2Role : authorities) {
			set.add(oAuth2Role);
		}

		return set;
	}

	@Override
	public boolean isSecretRequired() {
		return isNotBlank(clientSecret);
	}

	@Override
	public boolean isAutoApprove(final String scope) {
		return getAutoApproveScope().contains(scope);
	}

	@Override
	public boolean isScoped() {
		return getScope().size() > 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getAdditionalInformation() {
		try {
			return new ObjectMapper().readValue(additionalInfo, Map.class);
		} catch (final IOException e) {
			return new HashMap<>();
		}
	}

	public void setAdditionalInformation(final Map<String, Object> additionalInformation) {
		try {
			additionalInfo = new ObjectMapper().writeValueAsString(additionalInformation);
		} catch (final IOException e) {
			additionalInfo = "";
		}
	}

}