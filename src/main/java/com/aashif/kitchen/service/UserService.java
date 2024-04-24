package com.aashif.kitchen.service;

import java.util.List;

import com.aashif.kitchen.entity.User;

public interface UserService {
	
	public User findUserByJwtToken(String jwt) throws Exception;
	public User finduserByEmail(String email) throws Exception;
	public List<User> findAllUser() throws Exception;
}
