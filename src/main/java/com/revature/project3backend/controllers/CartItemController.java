package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping ("cartitem")
@CrossOrigin (origins = "http://localhost:4200", allowCredentials = "true")
public class CartItemController {
	@PostMapping
	public ResponseEntity <JsonResponse> createCartItem (HttpSession httpSession) throws InvalidValueException, UnauthorizedException {
		if (httpSession.getAttribute ("user") == null) {
			throw new UnauthorizedException ();
		}
		
		//todo
		
		return null;
	}
	
	@GetMapping
	public ResponseEntity <JsonResponse> getCartItems (HttpSession httpSession) throws UnauthorizedException {
		if (httpSession.getAttribute ("user") == null) {
			throw new UnauthorizedException ();
		}
		
		//todo
		
		return null;
	}
	
	@DeleteMapping
	public ResponseEntity <JsonResponse> deleteCartItem (@RequestParam Integer cartItemId, HttpSession httpSession) throws InvalidValueException, UnauthorizedException {
		if (httpSession.getAttribute ("user") == null) {
			throw new UnauthorizedException ();
		}
		
		//todo
		
		return null;
	}
}
