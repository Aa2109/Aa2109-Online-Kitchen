package com.aashif.kitchen.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aashif.kitchen.config.JwtProvider;
import com.aashif.kitchen.entity.Cart;
import com.aashif.kitchen.entity.User;
import com.aashif.kitchen.enumUsed.USER_ROLE;
import com.aashif.kitchen.exception.ResourceNotFoundException;
import com.aashif.kitchen.repository.CartRepo;
import com.aashif.kitchen.repository.UserRepo;
import com.aashif.kitchen.requset.LoginRequest;
import com.aashif.kitchen.response.AuthResponse;
import com.aashif.kitchen.service.CustomerUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	@Autowired
	private CartRepo cartRepo;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user){
		if (user.getPassword() == null) {
			System.out.println("user password: "+user.getPassword());
	        throw new IllegalArgumentException("Password cannot be null");
	        
	    }
		User isEmailExist = this.userRepo.findByEmail(user.getEmail());
		if(isEmailExist != null) {
			throw new ResourceNotFoundException("User","userId" , user.getId());
		}
		User createdUser = new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setName(user.getName());
		createdUser.setRole(user.getRole());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User saveUser = this.userRepo.save(createdUser);
		
		Cart cart = new Cart();
		cart.setCustomer(saveUser);
		cartRepo.save(cart);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generatetoken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register Succes");
		authResponse.setRole(saveUser.getRole());
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
				
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req){
		String userName = req.getEmail();
		String password = req.getPassword();
		
		Authentication authentication = authenticate(userName, password);
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
		
		String jwt = jwtProvider.generatetoken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login Succes");
		authResponse.setRole(USER_ROLE.valueOf(role));
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
	}

	private Authentication authenticate(String userName, String password) {
		UserDetails userDetails = customerUserDetailsService.loadUserByUsername(userName);
		if(userDetails ==null) {
			throw new BadCredentialsException("Inavlid username!!");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Inavlid password!!");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}

}
