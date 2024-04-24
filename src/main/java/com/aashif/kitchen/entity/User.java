package com.aashif.kitchen.entity;
import java.util.*;

import com.aashif.kitchen.dto.RestaurantDto;
import com.aashif.kitchen.enumUsed.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 
	private String name;
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	private USER_ROLE role =USER_ROLE.ROLE_CUSTOMER;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private List<Order> orders=new ArrayList<>();
	
	@ElementCollection
	private List<RestaurantDto> favorites= new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> address = new ArrayList<>();

}
