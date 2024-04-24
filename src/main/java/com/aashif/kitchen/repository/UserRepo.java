package com.aashif.kitchen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashif.kitchen.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	public User findByEmail(String userName);

}
