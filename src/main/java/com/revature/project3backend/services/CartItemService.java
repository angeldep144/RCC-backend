package com.revature.project3backend.services;

import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.repositories.CartItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CartItemService {
	private final CartItemRepo cartItemRepo;
	
	@Autowired
	public CartItemService (CartItemRepo cartItemRepo) {
		this.cartItemRepo = cartItemRepo;
	}
	
	public void createCartItem (CartItem cartItem) {
		this.cartItemRepo.save(cartItem);
	}
}
