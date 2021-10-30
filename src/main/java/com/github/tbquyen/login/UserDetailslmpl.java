package com.github.tbquyen.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailslmpl implements UserDetails {
	private static final long serialVersionUID = 4171811518800558345L;
	private String username;
	private String password;
	private String role;
	private String status;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(role);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !"-1".equals(status);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !"0".equals(status);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserDetailslmpl) {
			UserDetailslmpl otherUser = (UserDetailslmpl) obj;
			return this.username.equals(otherUser.getUsername());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.username != null ? this.username.hashCode() : 0;
	}
}
