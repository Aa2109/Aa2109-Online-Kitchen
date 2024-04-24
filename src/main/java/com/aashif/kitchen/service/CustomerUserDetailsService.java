package com.aashif.kitchen.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aashif.kitchen.entity.User;
import com.aashif.kitchen.enumUsed.*;
import com.aashif.kitchen.exception.ResourceNotFoundException;
import com.aashif.kitchen.repository.UserRepo;

// this class prevent to generate auto password of spring security
@Service
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(username);//.orElseThrow(new ResourceNotFoundException("username", "user", username));
		if(user == null) {
			throw new UsernameNotFoundException("user not found with email: "+username);
			//throw new ResourceNotFoundException("User", "Username", username);
		}
		USER_ROLE role = user.getRole();
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),authorities);
	}

}
