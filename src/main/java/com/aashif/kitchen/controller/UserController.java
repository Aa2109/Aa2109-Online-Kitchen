package com.aashif.kitchen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aashif.kitchen.entity.User;
import com.aashif.kitchen.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception{
		User user =this.userService.findUserByJwtToken(jwt);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<User> findAllUser() throws Exception{
		return new ResponseEntity(this.userService.findAllUser(),HttpStatus.OK);
	}
}
