package com.aashif.kitchen.config;

import java.util.*;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	
	private SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generatetoken(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		String  roles = populateAuthrities(authorities);
		String jwt = Jwts.builder().issuedAt(new Date())
				.expiration(new Date(new Date().getTime()+84400000))
				.claim("email", auth.getName())
				.claim("authorities", roles)
				.signWith(key)
				.compact();
		return jwt;
	}
	
	public String getEmailFromJwtToken(String jwt) {
		jwt = jwt.substring(7);
		 Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
         String email = String.valueOf(claims.get("email"));
         return email;
	}
	
	private String populateAuthrities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> auths = new HashSet<>();
		for(GrantedAuthority authority: authorities) {
			auths.add(authority.getAuthority());
		}
		return String.join(",", auths);
	}
}
