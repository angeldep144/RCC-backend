package com.revature.project3backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.CreateCartItemBody;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.User;
import com.revature.project3backend.models.UserRole;
import com.revature.project3backend.services.CartItemService;
import com.revature.project3backend.services.ProductService;
import com.revature.project3backend.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest (CartItemController.class)
class CartItemControllerIT {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CartItemService cartItemService;
	
	@MockBean
	private ProductService productService;
	
	@MockBean
	private UserService userService;
	
	private final ObjectMapper json = new ObjectMapper ();
	
	private final List <Product> products = new ArrayList <> ();
	private final List <User> users = new ArrayList <> ();
	
	@BeforeEach
	void setUp () {
		products.add (new Product (1, "Java I", "A beginner Java course", 10.00F, "", 8.00F, 5));
		products.add (new Product (2, "Java II", "An intermediate Java course", 20.00F, "", 18.00F, 5));
		products.add (new Product (3, "Python I", "A beginner Python course", 10.00F, "", 8.00F, 5));
		products.add (new Product (4, "Python II", "An intermediate Python course", 20.00F, "", 18.00F, 5));
		users.add (new User (1, "User", "1", "email1", "username13123", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "USER")));
		users.add (new User (2, "User", "2", "email2", "username22425", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "USER")));
		users.add (new User (3, "User", "3", "email3", "username32323", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "USER")));
	}
	
	@AfterEach
	void tearDown () {
		users.clear ();
		products.clear ();
	}
	
	@Test
	void createCartItem () throws Exception {
		CreateCartItemBody body = new CreateCartItemBody ();
		body.setProductId (2);    // Java II
		body.setQuantity (2);
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", users.get (1)); // User 2
		Mockito.when (productService.getProduct (body.getProductId ())).thenReturn (products.get (1));  // Java II
		List <CartItem> newCart = new ArrayList <> ();
		CartItem cartItem = new CartItem (users.get (1), products.get (1), 2);
		newCart.add (cartItem);
		mvc.perform (MockMvcRequestBuilders.post ("/cartitem").contentType (MediaType.APPLICATION_JSON).session (httpSession).content (json.writeValueAsString (body))).andExpect (MockMvcResultMatchers.status ().isOk ()).andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse ("Added to cart", true))));
		Mockito.verify (cartItemService).createCartItem (cartItem);
		Mockito.verify (userService).addToCart (users.get (1), cartItem);
	}
	
	@Test
	void createCartItemWhenNotLoggedIn () throws Exception {
		CreateCartItemBody body = new CreateCartItemBody ();
		body.setProductId (2);    // Java II
		body.setQuantity (2);
		MockHttpSession httpSession = new MockHttpSession ();
		//httpSession.setAttribute("user", users.get(1)); // User 2, on purpose DON'T set it so its null
		mvc.perform (MockMvcRequestBuilders.post ("/cartitem").contentType (MediaType.APPLICATION_JSON).session (httpSession).content (json.writeValueAsString (body))).andExpect (MockMvcResultMatchers.status ().is4xxClientError ()) //isBadRequest ())
			.andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse (new UnauthorizedException (), "/login"))));
		Mockito.verify (productService, Mockito.never ()).getProduct (Mockito.any ());
		Mockito.verify (cartItemService, Mockito.never ()).createCartItem (Mockito.any ());
		Mockito.verify (userService, Mockito.never ()).addToCart (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void createCartItemWhenProductIdIsNull () throws Exception {
		CreateCartItemBody body = new CreateCartItemBody ();
		//body.setProductId(2); // If we don't set it product id will be null
		body.setQuantity (2);
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", users.get (2));
		mvc.perform (MockMvcRequestBuilders.post ("/cartitem").contentType (MediaType.APPLICATION_JSON).session (httpSession).content (json.writeValueAsString (body))).andExpect (MockMvcResultMatchers.status ().isBadRequest ()).andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse (new InvalidValueException ("Invalid product id")))));
		Mockito.verify (productService, Mockito.never ()).getProduct (Mockito.any ());
		Mockito.verify (cartItemService, Mockito.never ()).createCartItem (Mockito.any ());
		Mockito.verify (userService, Mockito.never ()).addToCart (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void createCartItemWhenQuantityIsNull () throws Exception {
		CreateCartItemBody body = new CreateCartItemBody ();
		body.setProductId (2); // Java II
		//body.setQuantity(2); // If we don't set it quantity will be null
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", users.get (0));
		mvc.perform (MockMvcRequestBuilders.post ("/cartitem").contentType (MediaType.APPLICATION_JSON).session (httpSession).content (json.writeValueAsString (body))).andExpect (MockMvcResultMatchers.status ().isBadRequest ()).andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse (new InvalidValueException ("Invalid quantity")))));
		Mockito.verify (productService, Mockito.never ()).getProduct (Mockito.any ());
		Mockito.verify (cartItemService, Mockito.never ()).createCartItem (Mockito.any ());
		Mockito.verify (userService, Mockito.never ()).addToCart (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void createCartItemWhenQuantityIsLessThanOne () throws Exception {
		CreateCartItemBody body = new CreateCartItemBody ();
		body.setProductId (2); // Java II
		body.setQuantity (0);  // Set quantity to invalid value e.g. < 1
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", users.get (0));
		mvc.perform (MockMvcRequestBuilders.post ("/cartitem").contentType (MediaType.APPLICATION_JSON).session (httpSession).content (json.writeValueAsString (body))).andExpect (MockMvcResultMatchers.status ().isBadRequest ()).andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse (new InvalidValueException ("Invalid quantity")))));
		Mockito.verify (productService, Mockito.never ()).getProduct (Mockito.any ());
		Mockito.verify (cartItemService, Mockito.never ()).createCartItem (Mockito.any ());
		Mockito.verify (userService, Mockito.never ()).addToCart (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void createCartItemWhenItemIsAlreadyInCart () throws Exception {
		CreateCartItemBody body = new CreateCartItemBody ();
		body.setProductId (2);    // Java II
		body.setQuantity (5);
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", users.get (1)); // User 2
		
		// Pre-populate user 2's cart with existing cart items which already contains Java II course
		List <CartItem> cartItems = new ArrayList <> ();
		// User 2 clone, leave blanked out the cart property to avoid infinite recursive loop it's probably an idiosyncrasy with
		// Jackson + mockMvc
		User user2Clone = new User (2, "User", "2", "email2", "username22425", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "USER"));
		cartItems.add (new CartItem (user2Clone, products.get (1), 2)); // pre-populate with existing item Java II
		users.get (1).setCart (cartItems);    // Set user 2's cart to contain existing items (just the Java II course)
		
		mvc.perform (MockMvcRequestBuilders.post ("/cartitem").contentType (MediaType.APPLICATION_JSON).session (httpSession).content (json.writeValueAsString (body))).andExpect (MockMvcResultMatchers.status ().isBadRequest ()).andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse (new InvalidValueException ("Invalid product id")))));
		Mockito.verify (productService, Mockito.never ()).getProduct (Mockito.any ());
		Mockito.verify (cartItemService, Mockito.never ()).createCartItem (Mockito.any ());
		Mockito.verify (userService, Mockito.never ()).addToCart (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void createCartItemWhenQuantityIsHigherThanStock () throws Exception {
		CreateCartItemBody body = new CreateCartItemBody ();
		body.setProductId (4);        // Python II
		body.setQuantity (6);        // Existing stock of Python II course is 5 on purpose exceed it for test
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", users.get (1)); // User 2
		
		// Pre-populate user 2's cart with existing cart items but NOT the Java II course
		List <CartItem> cartItems = new ArrayList <> ();
		// User 2 clone, leave blanked out the cart property to avoid infinite recursive loop it's probably an idiosyncrasy with
		// Jackson + mockMvc
		User user2Clone = new User (2, "User", "2", "email2", "username22425", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "USER"));
		cartItems.add (new CartItem (user2Clone, products.get (1), 2)); // pre-populate with existing item Java II
		users.get (1).setCart (cartItems);
		
		Mockito.when (productService.getProduct (body.getProductId ())).thenReturn (products.get (3));  // Python II
		
		mvc.perform (MockMvcRequestBuilders.post ("/cartitem").contentType (MediaType.APPLICATION_JSON).session (httpSession).content (json.writeValueAsString (body))).andExpect (MockMvcResultMatchers.status ().isBadRequest ()).andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse (new InvalidValueException ("Invalid quantity")))));
		Mockito.verify (cartItemService, Mockito.never ()).createCartItem (Mockito.any ());
		Mockito.verify (userService, Mockito.never ()).addToCart (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void getCartItems () throws Exception {
		List <CartItem> cart = new ArrayList <> ();
		// User 3 clone leave blanked out the cart property to avoid infinite recursive loop it's probably an idiosyncrasy with
		// Jackson + mockMvc
		User user3 = new User (3, "User", "3", "email3", "username32323", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "USER"));
		
		cart.add (new CartItem (user3, products.get (1), 2)); // pre-populate with existing item Java II
		cart.add (new CartItem (user3, products.get (2), 3)); // pre-populate with existing item Python I
		
		users.get (2).setCart (cart);    // User 3
		
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", users.get (2)); // User 3
		
		mvc.perform (MockMvcRequestBuilders.get ("/cartitem")
			.contentType (MediaType.APPLICATION_JSON)
			.session (httpSession))
			
			.andExpect (MockMvcResultMatchers.status ().isOk ())
			.andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse ("Got " + cart.size () + " cart items", true, cart)))
		);
	}
	
	@Test
	void getCartItemsWhenNotLoggedIn () {
	}
	
	@Test
	void updateCartItemWhenQuantityIsNull () {
	}
	
	@Test
	void updateCartItemWhenQuantityIsHigherThanStock () {
	}
	
	@Test
	void updateCartItemWhenItemIsNotInCart () {
	}
	
	@Test
	void deleteCartItem () throws InvalidValueException, UnauthorizedException {
	}
	
	@Test
	void deleteCartItemWhenNotLoggedIn () {
	}
	
	@Test
	void deleteCartItemWhenItemIsNotInCart () {
	}
}