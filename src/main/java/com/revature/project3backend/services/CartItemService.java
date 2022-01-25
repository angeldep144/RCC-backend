package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidValueException;
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

	/**
	 * User (aka buyer) adds an item to their shopping cart via "cartitem" endpoint (see CartItemController)
	 * @param cartItem always associated with the logged-in user (e.g. buyer)
	 */
	public void createCartItem (CartItem cartItem) {
		this.cartItemRepo.save (cartItem);
	}
	
	/**
	 * Based on a user's (buyer and user are interchangeable) unique user id, return their cart items
	 *
	 * @param UserId unique serial identifier for a given user
	 * @return A list of all cart items for the given user
	 */
	public List <CartItem> getCartItems (Integer UserId) {
		return this.cartItemRepo.findAllByBuyerId (UserId);
	}

	/**
	 * Given the system-wide unique cart item identifier and quantity, update the buyer (user) cart item
	 *
	 * @param cartItemId	Unique throughout the system always associated with a buyer (User)
	 * @param quantity		The update quantity, this is the changed value
	 * @throws InvalidValueException	happens when specified cart item is not able to be found
	 */
	public void updateCartItem (Integer cartItemId, Integer quantity) throws InvalidValueException {
		CartItem cartItem = cartItemRepo.findById (cartItemId).orElse (null);
		
		if (cartItem == null) {
			throw new InvalidValueException ("Invalid cart item id");
		}
		
		cartItem.setQuantity (quantity);
		
		cartItemRepo.save (cartItem);
	}
	
	/**
	 * Cart items are tracked by user (buyer is interchangeable with user) but the id is unique throughout the whole system
	 *
	 * @param cartItemId The unique id assigned to the cart item
	 */
	public void deleteCartItem (Integer cartItemId) {
		this.cartItemRepo.deleteById (cartItemId);
	}
}
