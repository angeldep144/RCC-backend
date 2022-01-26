package com.revature.project3backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.ProductService;
import com.revature.project3backend.services.TransactionService;
import com.revature.project3backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping ("transaction")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials = "true")
public class TransactionController {
	private final TransactionService transactionService;
	private final UserService userService;
	private final ProductService productService;
	
	@Autowired
	public TransactionController (TransactionService transactionService, UserService userService, ProductService productService) {
		this.transactionService = transactionService;
		this.userService = userService;
		this.productService = productService;
	}
	
	@PostMapping
	public ResponseEntity <JsonResponse> createTransaction (HttpSession httpSession) throws UnauthorizedException, InvalidValueException, JsonProcessingException {
		User user = (User) httpSession.getAttribute ("user");
		
		if (user == null) {
			throw new UnauthorizedException ();
		}
		
		List <CartItem> cart = user.getCart ();
		
		if (cart.size () < 1) {
			throw new InvalidValueException ("Invalid cart");
		}
		
		for (CartItem cartItem : cart) {
			productService.reduceStock (cartItem.getProduct (), cartItem.getQuantity ());
		}
		
		ResponseEntity <JsonResponse> responseEntity = ResponseEntity.ok (new JsonResponse ("Created transaction", true, transactionService.createTransaction (new Transaction (user), cart)));
		
		userService.clearCart (user);
		
		return responseEntity;
	}
	
	@GetMapping ("{transactionId}")
	public ResponseEntity <JsonResponse> getTransaction (@PathVariable Integer transactionId, HttpSession httpSession) throws UnauthorizedException, InvalidValueException {
		User user = (User) httpSession.getAttribute ("user");
		
		if (user == null) {
			throw new UnauthorizedException ();
		}
		
		Transaction transaction = transactionService.getTransaction (transactionId);
		
		if (!transaction.getBuyer ().getId ().equals (user.getId ())) {
			throw new InvalidValueException ("Invalid transaction id");
		}
		
		return ResponseEntity.ok (new JsonResponse ("Got transaction", true, transaction));
	}
}
