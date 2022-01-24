package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.CreateCartItemBody;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.CartItemService;
import com.revature.project3backend.services.ProductService;
import com.revature.project3backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping ("cartitem")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials = "true")
public class CartItemController {
	private final CartItemService cartItemService;
	private final ProductService productService;
	private final UserService userService;
	
	@Autowired
	public CartItemController (CartItemService cartItemService, ProductService productService, UserService userService) {
		this.cartItemService = cartItemService;
		this.productService = productService;
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity <JsonResponse> createCartItem (@RequestBody CreateCartItemBody createCartItemBody, HttpSession httpSession) throws InvalidValueException, UnauthorizedException {
		User user = (User) httpSession.getAttribute ("user"); 
		
		if (user == null) {
			throw new UnauthorizedException ();
		}
		
		if (createCartItemBody.getProductId () == null) {
			throw new InvalidValueException ("Invalid product id");
		}
		
		if (createCartItemBody.getQuantity () == null) {
			throw new InvalidValueException ("Invalid quantity");
		}
		
		if (createCartItemBody.getQuantity () < 1) {
			throw new InvalidValueException ("Invalid quantity");
		}
		
		for (CartItem cartItem : user.getCart ()) {
			//if product is already in cart
			if (createCartItemBody.getProductId ().equals (cartItem.getProduct ().getId ())) {
				throw new InvalidValueException ("Invalid product id");
			}
		}
		
		CartItem cartItem = new CartItem (user, productService.getProduct (createCartItemBody.getProductId ()), createCartItemBody.getQuantity ());
		
		cartItemService.createCartItem (cartItem);
		
		userService.addToCart (user, cartItem);
		
		return ResponseEntity.ok (new JsonResponse ("Added to cart", true));
	}
	
	@GetMapping
	public ResponseEntity <JsonResponse> getCartItems (HttpSession httpSession) throws UnauthorizedException, InvalidValueException {
		User user = (User) httpSession.getAttribute ("user");
		if (user == null) {
			throw new UnauthorizedException ();
		}

		List<CartItem> cartItems = cartItemService.getCartItems(user.getId());
		if (cartItems == null) {
			throw new InvalidValueException ("No cart items found for user.");
		}
		
		return ResponseEntity.ok (new JsonResponse ("Successfully retrieved cart items for "+user.getUsername()+".", true, cartItems));
	}
	
	@DeleteMapping ("{cartItemId}")
	public ResponseEntity <JsonResponse> deleteCartItem (@PathVariable Integer cartItemId, HttpSession httpSession) throws InvalidValueException, UnauthorizedException {
		User user = (User) httpSession.getAttribute ("user");
		if (user == null) {
			throw new UnauthorizedException ();
		}

		cartItemService.deleteCartItem(cartItemId);

		return ResponseEntity.ok (new JsonResponse ("Deleted item from cart", true));
	}
}
