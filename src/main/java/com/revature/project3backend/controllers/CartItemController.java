package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.CreateCartItemBody;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.jsonmodels.UpdateCartItemBody;
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
	public ResponseEntity <JsonResponse> createCartItem (@RequestBody CreateCartItemBody body, HttpSession httpSession) throws InvalidValueException, UnauthorizedException {
		User user = (User) httpSession.getAttribute ("user");
		
		if (user == null) {
			throw new UnauthorizedException ();
		}
		
		if (body.getProductId () == null) {
			throw new InvalidValueException ("Invalid product id");
		}
		
		if (body.getQuantity () == null) {
			throw new InvalidValueException ("Invalid quantity");
		}
		
		if (body.getQuantity () < 1) {
			throw new InvalidValueException ("Invalid quantity");
		}
		
		for (CartItem cartItem : user.getCart ()) {
			//if product is already in cart
			if (body.getProductId ().equals (cartItem.getProduct ().getId ())) {
				throw new InvalidValueException ("Invalid product id");
			}
		}
		
		Product product = productService.getProduct (body.getProductId ());
		
		if (product.getStock () - body.getQuantity () < 0) {
			throw new InvalidValueException ("Invalid quantity");
		}
		
		CartItem cartItem = new CartItem (user, product, body.getQuantity ());
		
		cartItemService.createCartItem (cartItem);
		
		userService.addToCart (user, cartItem);
		
		return ResponseEntity.ok (new JsonResponse ("Added to cart", true));
	}
	
	@GetMapping
	public ResponseEntity <JsonResponse> getCartItems (HttpSession httpSession) throws UnauthorizedException {
		User user = (User) httpSession.getAttribute ("user");
		
		if (user == null) {
			throw new UnauthorizedException ();
		}
		
		List <CartItem> cart = user.getCart ();
		
		return ResponseEntity.ok (new JsonResponse ("Got " + cart.size () + " cart items", true, cart));
	}
	
	@PutMapping ("{cartItemId}")
	public ResponseEntity <JsonResponse> updateCartItem (@PathVariable Integer cartItemId, @RequestBody UpdateCartItemBody body, HttpSession httpSession) throws InvalidValueException, UnauthorizedException {
		User user = (User) httpSession.getAttribute ("user");
		
		if (user == null) {
			throw new UnauthorizedException ();
		}
		
		if (body.getQuantity () == null) {
			throw new InvalidValueException ("Invalid quantity");
		}
		
		for (int i = 0; i < user.getCart ().size (); i++) {
			if (user.getCart ().get (i).getId ().equals (cartItemId)) {
				if (user.getCart ().get (i).getProduct ().getStock () - body.getQuantity () < 0) {
					throw new InvalidValueException ("Invalid quantity");
				}
				
				cartItemService.updateCartItem (cartItemId, body.getQuantity ());
				
				return ResponseEntity.ok (new JsonResponse ("Updated cart item quantity", true));
			}
		}
		
		throw new InvalidValueException ("Invalid cart item id");
	}
	
	@DeleteMapping ("{cartItemId}")
	public ResponseEntity <JsonResponse> deleteCartItem (@PathVariable Integer cartItemId, HttpSession httpSession) throws InvalidValueException, UnauthorizedException {
		User user = (User) httpSession.getAttribute ("user");
		
		if (user == null) {
			throw new UnauthorizedException ();
		}
		
		for (int i = 0; i < user.getCart ().size (); i++) {
			if (user.getCart ().get (i).getId ().equals (cartItemId)) {
				userService.removeFromCart (user, i);
				
				return ResponseEntity.ok (new JsonResponse ("Deleted cart item", true));
			}
		}
		
		throw new InvalidValueException ("Invalid cart item id");
	}
}
