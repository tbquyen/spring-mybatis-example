package com.github.tbquyen.config;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public class MyPrincipal extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	protected MyPrincipal(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	protected MyPrincipal(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public static void authentication(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		Assert.notEmpty(authorities, "Authorities list cannot be empty or null");

		MyPrincipal myPrincipal = new MyPrincipal(principal, credentials, authorities);
		SecurityContextHolder.getContext().setAuthentication(myPrincipal);
	}

	/**
	 * GrantedAuthority objects from a comma-separated string representation (e.g. "ROLE_A, ROLE_B, ROLE_C").
	 *
	 * @param authorityString the comma-separated string
	 * @return the authorities created by tokenizing the string
	 */
	public static void grantedAuthority(String authorityString) {
		Assert.hasText(authorityString, "Authorities name cannot be empty or null");

		MyPrincipal myPrincipal = MyPrincipal.getInstance();

		if (!myPrincipal.isAuthenticated()) {
			return;
		}

		myPrincipal = new MyPrincipal(myPrincipal.getPrincipal(), myPrincipal.getCredentials(),
				AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString));

		SecurityContextHolder.getContext().setAuthentication(myPrincipal);
	}

	public static MyPrincipal getInstance() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof MyPrincipal) {
			return (MyPrincipal) authentication;
		}

		// anonymousUser
		return new MyPrincipal(authentication.getPrincipal(), authentication.getCredentials());
	}

	public static MyPrincipal getInstance(Object principal, Object credentials) {
		return new MyPrincipal(principal, credentials);
	}
	
	public static MyPrincipal getInstance(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		return new MyPrincipal(principal, credentials, authorities);
	}
}
