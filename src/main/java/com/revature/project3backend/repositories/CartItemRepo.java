package com.revature.project3backend.repositories;

import com.revature.project3backend.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepo extends JpaRepository <CartItem, Integer> {
	List <CartItem> findAllByBuyerId (Integer BuyerId);
}
