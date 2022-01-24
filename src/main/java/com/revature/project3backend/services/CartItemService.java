package com.revature.project3backend.services;

import com.revature.project3backend.models.CartItem;
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
		this.cartItemRepo.save(cartItem);
	}

	/**
	 * Based on a user's unique user id, return their cart items
	 * @param UserId unique serial identifier for a given user
	 * @return A list of all cart items for the given user
	 */
	public List<CartItem> getCartItems(Integer UserId) {
		return this.cartItemRepo.findAllByBuyerId(UserId);
	}

	/**
	 * Cart items are tracked by user but the id is unique throughout the whole system
	 * @param cartItemId The unique id assigned to the cart item
	 */
	public void deleteCartItem(Integer cartItemId) {
		this.cartItemRepo.deleteById(cartItemId);
	}
}
