package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.CreateCartItemBody;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.User;
import com.revature.project3backend.models.UserRole;
import com.revature.project3backend.repositories.CartItemRepo;
import com.revature.project3backend.repositories.ProductRepo;
import com.revature.project3backend.repositories.UserRepo;
import com.revature.project3backend.services.CartItemService;
import com.revature.project3backend.services.ProductService;
import com.revature.project3backend.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartItemControllerTest {
    private final CartItemRepo cartItemRepo = Mockito.mock(CartItemRepo.class);
    private final ProductRepo productRepo = Mockito.mock(ProductRepo.class);
    private final UserRepo userRepo = Mockito.mock(UserRepo.class);

    private final CartItemService cartItemService;
    private final ProductService productService;
    private final UserService userService;

    private CartItemController cartItemController;

    private final List<Product> products = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    public CartItemControllerTest() {
        cartItemService = new CartItemService(cartItemRepo);
        productService = new ProductService(productRepo);
        userService = new UserService(userRepo, cartItemRepo);
        cartItemController = new CartItemController(cartItemService, productService, userService);
    }

    @BeforeEach
    void setUp() {
        products.add (new Product(1, "Java I", "A beginner Java course", 10.00F, "", 8.00F, 5));
        products.add (new Product (2, "Java II", "An intermediate Java course", 20.00F, "", 18.00F, 5));
        products.add (new Product (3, "Python I", "A beginner Python course", 10.00F, "", 8.00F, 5));
        products.add (new Product (4, "Python II", "An intermediate Python course", 20.00F, "", 18.00F, 5));
        users.add (new User (1, "User", "1", "email1", "username13123", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "USER")));
        users.add (new User (2, "User", "2", "email2", "username22425", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "USER")));
        users.add (new User (3, "User", "3", "email3", "username32323", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "USER")));
    }

    @AfterEach
    void tearDown() {
        users.clear();
        products.clear();
    }
	
	@Test
	void createCartItem () {
		
	}
	
	@Test
	void createCartItemWhenNotLoggedIn () {
		
	}
	
	@Test
	void createCartItemWhenProductIdIsNull () {
		
	}
	
	@Test
	void createCartItemWhenQuantityIsNull () {
		
	}
	
	@Test
	void createCartItemWhenQuantityIsLessThanOne () {
		
	}
	
	@Test
	void createCartItemWhenItemIsAlreadyInCart () {
		
	}
	
	@Test
	void createCartItemWhenQuantityIsHigherThanStock () {
		
	}
	
	@Test
	void getCartItems () {
		
	}
	
	@Test
	void getCartItemsWhenNotLoggedIn () {
		
	}
	
	@Test
	void updateCartItem () {
		
	}
	
	@Test
	void updateCartItemWhenNotLoggedIn () {
		
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
	void deleteCartItem () {
		
	}
	
	@Test
	void deleteCartItemWhenNotLoggedIn () {
		
	}
	
	@Test
	void deleteCartItemWhenItemIsNotInCart () {
		
	}
	
	/*
    @Test
    void createCartItem() throws InvalidValueException, UnauthorizedException {
        CreateCartItemBody body = new CreateCartItemBody();
        body.setProductId(2);    // Java II
        body.setQuantity(2);
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", users.get(1)); // User 2
        Mockito.when(productRepo.findById(2)).thenReturn(java.util.Optional.of(products.get(1)));   // Java II
        List<CartItem> newCart = new ArrayList<>();
        CartItem cartItem = new CartItem(users.get(1), products.get(1), 2);
        newCart.add(cartItem);
        assertEquals(ResponseEntity.ok (new JsonResponse("Added to cart", true)),
                cartItemController.createCartItem(body, httpSession));
        assertEquals(newCart, users.get(1).getCart());
        Mockito.verify(cartItemRepo, Mockito.times(1)).save(cartItem);
        Mockito.verify(userRepo, Mockito.times(1)).save (users.get(1));
    }

    @Test
    void createCartItem_HasExistingItemsAlready() throws InvalidValueException, UnauthorizedException {
        CreateCartItemBody body = new CreateCartItemBody();
        body.setProductId(4);    // Java II
        body.setQuantity(4);
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("user", users.get(1)); // User 2
        Mockito.when(productRepo.findById(4)).thenReturn(java.util.Optional.of(products.get(3)));   // Python II

        List<CartItem> newCart = new ArrayList<>();
        CartItem oldCartItem = new CartItem(users.get(1), products.get(1), 2); // pre-populate with existing item
        newCart.add(oldCartItem);

        CartItem cartItem = new CartItem(users.get(1), products.get(4), 4);
        assertEquals(ResponseEntity.ok (new JsonResponse("Added to cart", true)),
                cartItemController.createCartItem(body, httpSession));
        assertEquals(newCart, users.get(1).getCart());
        Mockito.verify(cartItemRepo, Mockito.times(1)).save(cartItem);
        Mockito.verify(userRepo, Mockito.times(1)).save (users.get(1));
    }

    @Test
    void getCartItems() {
    }

    @Test
    void updateCartItem() {
    }

    @Test
    void deleteCartItem() {
    }
    */
}