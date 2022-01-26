package com.revature.project3backend.repositories;

import com.revature.project3backend.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Crud actions of Cart items to Database
 */
public interface CartItemRepo extends JpaRepository <CartItem, Integer> {}
