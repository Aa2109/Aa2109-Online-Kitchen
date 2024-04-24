package com.aashif.kitchen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aashif.kitchen.entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Long>{

}
