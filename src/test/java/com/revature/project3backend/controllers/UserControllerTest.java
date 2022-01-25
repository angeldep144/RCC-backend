package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.User;
import com.revature.project3backend.models.UserRole;
import com.revature.project3backend.services.RoleService;
import com.revature.project3backend.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {
	private final UserController userController;
	private final UserService userService = Mockito.mock (UserService.class);
	private final RoleService roleService = Mockito.mock (RoleService.class);
	
	private final List <User> users = new ArrayList <> ();
	
	public UserControllerTest () {
		this.userController = new UserController (userService, roleService);
		
		users.add (new User ("John", "Smith", "johnsmith@example.com", "johnsmith", "password"));
		users.add (new User ("Sarah", "Smith", "sarahsmith@example.com", "sarahsmith", "password"));
	}
	
	@Test
	void createUserWhenFirstNameIsNull () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setLastName ("Smith");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("johnsmith");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenLastNameIsNull () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("johnsmith");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenEmailIsNull () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setUsername ("johnsmith");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenUsernameIsNull () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setEmail ("johnsmith@example.com");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenPasswordIsNull () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("johnsmith");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenFirstNameIsEmpty () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("");
		user.setLastName ("Smith");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("johnsmith");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenLastNameIsEmpty () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("johnsmith");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenEmailIsEmpty () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setEmail ("");
		user.setUsername ("johnsmith");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenUsernameIsEmpty () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenPasswordIsEmpty () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("johnsmith");
		user.setPassword ("");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenFirstNameIsWhitespace () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("       ");
		user.setLastName ("Smith");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("johnsmith");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenLastNameIsWhitespace () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("          ");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("johnsmith");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenEmailIsWhitespace () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setEmail ("            ");
		user.setUsername ("johnsmith");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenUsernameIsWhitespace () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("          ");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenPasswordIsWhitespace () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("johnsmith");
		user.setPassword ("            ");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid user", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenUsernameIsInvalid () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setEmail ("johnsmith@example.com");
		user.setUsername ("@.\\/");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid username", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUserWhenEmailIsInvalid () throws InvalidValueException {
		User user = new User ();
		
		user.setId (1);
		user.setFirstName ("John");
		user.setLastName ("Smith");
		user.setEmail ("not an email");
		user.setUsername ("johnsmith");
		user.setPassword ("password");
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.userController.createUser (user));
		
		assertEquals ("Error! Invalid email", exception.getMessage ());
		
		Mockito.verify (userService, Mockito.never ()).createUser (Mockito.any ());
	}
	
	@Test
	void createUser () throws InvalidValueException {
		assertEquals (ResponseEntity.ok (new JsonResponse ("Created user", true, null, "/login")), this.userController.createUser (this.users.get (0)));
		
		Mockito.verify (userService).createUser (users.get (0));
	}
}