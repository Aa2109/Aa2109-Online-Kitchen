package com.aashif.kitchen.config;

import java.io.IOException;
import java.util.*;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter{

		 private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				String requestHeader = request.getHeader(JwtConstant.JWT_HEADER);
				//Bearer 2352345235sdfrsfgsdfsdf
				logger.info("Header : {}", requestHeader);
				String token = null;
				
				if (requestHeader != null && requestHeader.startsWith("Bearer")) {
		            token = requestHeader.substring(7);
		            try {

		               SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
		               Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
		               String email = String.valueOf(claims.get("email"));
		               String authorities = String.valueOf(claims.get("authorities"));
		             //ROLE_CUSTOMER,ROLE_ADMIN
		               List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities); 
		               Authentication authentication = new UsernamePasswordAuthenticationToken(null, auth);
		               SecurityContextHolder.getContext().setAuthentication(authentication);
		               
		            } catch (IllegalArgumentException e) {
		                logger.info("Illegal Argument while fetching the username !!");
		                e.printStackTrace();
		            } catch (ExpiredJwtException e) {
		                logger.info("Given jwt token is expired !!");
		                e.printStackTrace();
		            } catch (MalformedJwtException e) {
		                logger.info("Some changed has done in token !! Invalid Token");
		                e.printStackTrace();
		            } catch (Exception e) {
		                e.printStackTrace();

		            }


		        } else {
		            logger.info("Invalid Header Value !! ");
		        }
		        filterChain.doFilter(request, response);
	}

}
