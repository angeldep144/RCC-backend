package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidCredentialsException;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.CartItemRepo;
import com.revature.project3backend.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
	UserService userService;
	UserRepo userRepo = Mockito.mock (UserRepo.class);
	CartItemRepo cartItemRepo = Mockito.mock (CartItemRepo.class);
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();

	public UserServiceTest(){ userService = new UserService (userRepo, cartItemRepo);}

	@Test
	void loginUser () throws InvalidCredentialsException {
		List<CartItem> items = new ArrayList<>();
		List <Transaction> transactions = new ArrayList <> ();
		String pass = "pass123";
		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", pass, items, transactions);
		String encryptedPass = passwordEncoder.encode (user.getPassword ());
		user.setPassword (encryptedPass);

		Mockito.when (userRepo.findByUsername (user.getUsername ())).thenReturn (user);

		User actualResult = userService.loginUser (user.getUsername (), pass);

		assertEquals (user, actualResult);
	}
	
	@Test
	void loginUserWhenUsernameIsInvalid () {
		List <CartItem> items = new ArrayList <> ();
		List <Transaction> transactions = new ArrayList <> ();
		String pass = "pass123";
		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", pass, items, transactions);
		String encryptedPass = passwordEncoder.encode (user.getPassword ());
		user.setPassword (encryptedPass);

		Mockito.when (userRepo.findByUsername (user.getUsername ())).thenReturn (user);

		InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
				() -> this.userService.loginUser(user.getUsername(), user.getPassword()));

		assertEquals("Error! Invalid credentials", exception.getMessage());

		Mockito.verify(userRepo, Mockito.times(1)).findByUsername(Mockito.any());
	}
	
	@Test
	void loginUserWhenPasswordIsInvalid () {
		List <CartItem> items = new ArrayList <> ();
		List <Transaction> transactions = new ArrayList <> ();
		String pass = "pass123";
		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", pass, items, transactions);
		String encryptedPass = passwordEncoder.encode (user.getPassword ());
		user.setPassword (encryptedPass);

		Mockito.when (userRepo.findByUsername (user.getUsername ())).thenReturn (user);

		InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
				() -> this.userService.loginUser(user.getUsername(), "pass"));

		assertEquals("Error! Invalid credentials", exception.getMessage());

		Mockito.verify(userRepo, Mockito.times(1)).findByUsername(Mockito.any());

	}
	
	@Test
	void getUserByUserName () {
		User expectedResult = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", "pass123", new ArrayList <> (), new ArrayList <> ());
		Mockito.when (userRepo.findByUsername (expectedResult.getUsername())).thenReturn (expectedResult);

		User actualResult = userService.getUserByUserName (expectedResult.getUsername());

		assertEquals (expectedResult, actualResult);
	}

	@Test
	void getUserByInvalidUserName () {
		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", "pass123", new ArrayList <> (), new ArrayList <> ());
		Mockito.when (userRepo.findByUsername (user.getUsername())).thenReturn (null);

		User actual = userService.getUserByUserName (user.getUsername());

		assertNull (actual);
	}
	
	@Test
	void createUser () throws InvalidValueException {
		User expectedResult = new User (1, "first", "last", "email", "username", "password", null, null);

		Mockito.when (this.userRepo.findByUsername (expectedResult.getUsername())).thenReturn (null);
		Mockito.when (this.userRepo.findByEmail (expectedResult.getEmail())).thenReturn (null);
		Mockito.when (this.userRepo.save (expectedResult)).thenReturn (expectedResult);

		User actualResult = this.userService.createUser (expectedResult);

		assertEquals (expectedResult, actualResult);
	}
	
	@Test
	void createUserWhenUsernameIsTaken () throws InvalidValueException {
		User user = new User (1, "first", "last", "email", "username", "password", null, null);

		Mockito.when (this.userRepo.findByUsername (user.getUsername())).thenReturn (user);
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userService.createUser (user));

		assertEquals ("Error! Username already in use", exception.getMessage ());

		Mockito.verify (userRepo, Mockito.never ()).findByEmail (Mockito.any ());
		Mockito.verify (userRepo, Mockito.never ()).save (Mockito.any ());
	}
	
	@Test
	void createUserWhenEmailIsTaken () {
		User user = new User (1, "first", "last", "email", "username", "password", null, null);

		Mockito.when (this.userRepo.findByUsername (user.getUsername())).thenReturn (null);
		Mockito.when (this.userRepo.findByEmail (user.getEmail())).thenReturn (user);

		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userService.createUser (user));

		assertEquals ("Error! Email already in use", exception.getMessage ());

		Mockito.verify (userRepo, Mockito.never ()).save (Mockito.any ());
	}
	
	@Test
	void addToCart () {
		List<CartItem> cartItems = new ArrayList<>();
		User user = new User (1, "first", "last", "email", "username", "password", cartItems, null);
		CartItem cartItem = new CartItem(1, user, new Product(), 1);

		this.userService.addToCart(user, cartItem);

		Mockito.verify(userRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	void removeFromCart () {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(1, null, new Product(), 1));
		User user = new User (1, "first", "last", "email", "username", "password", cartItems, null);
		CartItem cartItem = new CartItem(1, user, new Product(), 1);

		this.userService.removeFromCart(user, cartItem);

		Mockito.verify(cartItemRepo, Mockito.times(1)).delete(Mockito.any());
		Mockito.verify(userRepo, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test
	void clearCart () {
		List<CartItem> cartItems = new ArrayList<>();
		cartItems.add(new CartItem(1, null, new Product(), 1));
		cartItems.add(new CartItem(2, null, new Product(), 2));
		User user = new User (1, "first", "last", "email", "username", "password", cartItems, null);

		this.userService.clearCart(user);

		Mockito.verify(cartItemRepo, Mockito.times(1)).deleteAll(Mockito.any());
		Mockito.verify(userRepo, Mockito.times(1)).save(Mockito.any());
	}
}