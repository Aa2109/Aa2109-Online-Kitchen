package com.aashif.kitchen.entity;

import java.util.*;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User customer;
	
	@JsonIgnore
	@ManyToOne
	private Restaurant restaurant;
	
	private long totalAmount;
	private String orderStatus;
	private Date createdAt;
	
	@ManyToOne
	private Address deliveryAdress;
	
	@OneToMany
	private List<OrderItem> items;
//	private Payment paymentMethod;
	private int totalItem;
	private int price;

}
