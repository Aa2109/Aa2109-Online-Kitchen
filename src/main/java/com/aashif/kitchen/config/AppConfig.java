package com.aashif.kitchen.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;


@Configuration
@EnableWebSecurity
public class AppConfig {

//public static final String[] PUBLIC_URLS= {"/api/auth/**", "/api/admin/**","/api/**"};
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .cors(cors->cors.configurationSource(corsConfigurationSource()))
             .authorizeHttpRequests(auth-> auth.requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER","ADMIN")
            		 .requestMatchers("/api/**", "/error").permitAll()//.requestMatchers("/test").permitAll()
//            		 .requestMatchers(HttpMethod.GET).permitAll()  // to allow all the get method 
                	.anyRequest()
                	.authenticated()
                	)
        .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
        .csrf(csrf -> csrf.disable());
        
        return http.build();
    }

private CorsConfigurationSource corsConfigurationSource() {

	return new CorsConfigurationSource() {
		
		@Override
		public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
			CorsConfiguration cfg = new CorsConfiguration();
			cfg.setAllowedOrigins(Arrays.asList(
					"https://Aa2109/aashif'skitchen",
					"http://localhost:3000"
					));
			cfg.setAllowedMethods(Collections.singletonList("*"));
			cfg.setAllowCredentials(true);
			cfg.setAllowedHeaders(Collections.singletonList("*"));
			cfg.setExposedHeaders(Arrays.asList("Authorization"));
			cfg.setMaxAge(3600L);
			return cfg;
			}
		};
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
