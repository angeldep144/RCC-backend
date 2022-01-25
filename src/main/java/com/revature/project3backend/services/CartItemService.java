package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.CartItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CartItemService {
	private final CartItemRepo cartItemRepo;
	
	@Autowired
	public CartItemService (CartItemRepo cartItemRepo) {
		this.cartItemRepo = cartItemRepo;
	}
	
	public void createCartItem (CartItem cartItem) {
		this.cartItemRepo.save (cartItem);
	}
	
	public void updateCartItem (Integer cartItemId, Integer quantity) throws InvalidValueException {
		CartItem cartItem = cartItemRepo.findById (cartItemId).orElse (null);
		
		if (cartItem == null) {
			throw new InvalidValueException ("Invalid cart item id");
		}
		
		cartItem.setQuantity (quantity);
		
		cartItemRepo.save (cartItem);
	}
}
