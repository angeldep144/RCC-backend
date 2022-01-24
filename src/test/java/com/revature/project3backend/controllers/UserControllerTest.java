package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.User;
import com.revature.project3backend.models.UserRole;
import com.revature.project3backend.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserControllerTest {
	private UserController userController;
	private final UserService userService = Mockito.mock (UserService.class);
	
	public UserControllerTest () {
		this.userController = new UserController (userService);
	}
	
	@BeforeEach
	void setUp () {
	}
	
	@AfterEach
	void tearDown () {
	}
	
	@Test
	void createUserPostiive () throws InvalidValueException {
		UserRole role = new UserRole(1, "Admin");

		User expectedResult = new User (1, "first", "last", "email", "username", "password", null, null, role);
		User input = new User ("first", "last", "email", "username", "password");
		
		Mockito.when (userService.createUser (input)).thenReturn (expectedResult);
		
		User actualResult = userService.createUser (input);
		
		assertEquals (expectedResult, actualResult);
	}
	
	@Test
	void createUserNegative () throws InvalidValueException {
		User input = new User ("first", "last", "email", "username", "password");
		
		Mockito.when (userService.createUser (input)).thenReturn (null);
		
		User actualResult = userService.createUser (input);
		
		assertNull (actualResult);
	}
}