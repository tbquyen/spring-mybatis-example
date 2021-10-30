package com.github.tbquyen.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private LoginDAO loginDAO;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return loginDAO.loadUserByUsername(username);
	}
}
