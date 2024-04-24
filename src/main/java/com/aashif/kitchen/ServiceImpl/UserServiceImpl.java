package com.aashif.kitchen.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aashif.kitchen.config.JwtProvider;
import com.aashif.kitchen.entity.User;
import com.aashif.kitchen.repository.UserRepo;
import com.aashif.kitchen.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserByJwtToken(String jwt)throws Exception {
		String email = this.jwtProvider.getEmailFromJwtToken(jwt);
		User user = this.finduserByEmail(email);
		if(user == null) throw new Exception("User not found with email: "+email);
		return user;
		
	}

	@Override
	public User finduserByEmail(String email)throws Exception {
		User user = this.userRepo.findByEmail(email);
		if(user == null) throw new Exception("User not found with email: "+email);
		return user;
	}

	@Override
	public List<User> findAllUser() throws Exception {
		List<User> user =  this.userRepo.findAll();
		return user;
	}

}
