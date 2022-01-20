package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidCredentialsException;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserServiceTest {
	
	UserService userService;
	UserRepo userRepo = Mockito.mock (UserRepo.class);
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();
	
	@BeforeEach
	void before () {
		userService = new UserService (userRepo);
	}
	
	@Test
	void loginValidUser () {
		List <CartItem> items = new ArrayList <> ();
		List <Transaction> transactions = new ArrayList <> ();
		String pass = "pass123";
		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", pass, items, transactions);
		String encryptedPass = passwordEncoder.encode (user.getPassword ());
		user.setPassword (encryptedPass);
		
		Mockito.when (userRepo.findByUsername (user.getUsername ())).thenReturn (user);
		
		User actual = null;
		try {
			actual = userService.loginUser (user.getUsername (), pass);
		} catch (InvalidCredentialsException e) {
			e.printStackTrace ();
		}
		
		assertEquals (user, actual);
	}
	
	@Test
	void loginInvalidUsername () {
		List <CartItem> items = new ArrayList <> ();
		List <Transaction> transactions = new ArrayList <> ();
		String pass = "pass123";
		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", pass, items, transactions);
		String encryptedPass = passwordEncoder.encode (user.getPassword ());
		user.setPassword (encryptedPass);
		
		Mockito.when (userRepo.findByUsername (user.getUsername ())).thenReturn (user);
		
		User actual = null;
		try {
			actual = userService.loginUser ("rmace", pass);
		} catch (InvalidCredentialsException e) {
			assertEquals (e.getMessage (), "Error! Invalid credentials");
		}
		
		assertNull (actual);
	}
	
	@Test
	void loginInvalidUserPassword () {
		List <CartItem> items = new ArrayList <> ();
		List <Transaction> transactions = new ArrayList <> ();
		String pass = "pass123";
		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", pass, items, transactions);
		String encryptedPass = passwordEncoder.encode (user.getPassword ());
		user.setPassword (encryptedPass);
		
		Mockito.when (userRepo.findByUsername (user.getUsername ())).thenReturn (user);
		
		User actual = null;
		try {
			actual = userService.loginUser (user.getUsername (), "pass");
		} catch (InvalidCredentialsException e) {
			assertEquals (e.getMessage (), "Error! Invalid credentials");
		}
		
		assertNull (actual);
	}
	
	@Test
	void getUserByValidUserName () {
		String username = "rmace";
		List <CartItem> items = new ArrayList <> ();
		List <Transaction> transactions = new ArrayList <> ();
		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", "pass123", items, transactions);
		Mockito.when (userService.getUserByUserName (username)).thenReturn (user);
		
		User actual = userService.getUserByUserName (username);
		
		assertEquals (actual, user);
		
	}
	
	@Test
	void getUserByInvalidUserName () {
		String username = "rmace";
		List <CartItem> items = new ArrayList <> ();
		List <Transaction> transactions = new ArrayList <> ();
		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", "pass123", items, transactions);
		Mockito.when (userService.getUserByUserName (username)).thenReturn (null);
		
		User actual = userService.getUserByUserName (username);
		
		assertNull (actual);
	}
}