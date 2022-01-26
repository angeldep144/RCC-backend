package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.User;
import com.revature.project3backend.models.UserRole;
import com.revature.project3backend.services.RoleService;
import com.revature.project3backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The UserController handles requests concerning users
 */
@RestController
@RequestMapping ("user")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials = "true")
public class UserController {
	private final UserService userService;
	private final RoleService roleService;
	
	@Autowired
	public UserController (UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}
	
	/**
	 * Create user just calls the userService createUser method and then returns with a response entity
	 *
	 * @param body The body variable contains Strings (firstName, lastName, username, password, email)
	 * @return A response entity that holds a Json response with the message, boolean, object, redirect.
	 * @throws InvalidValueException is here because the userService can throw the exception to this method
	 */
	@PostMapping
	public ResponseEntity <JsonResponse> createUser (@RequestBody User body) throws InvalidValueException {
		//validate user
		
		if (body.getFirstName () == null || body.getLastName () == null || body.getEmail () == null || body.getUsername () == null || body.getPassword () == null) {
			throw new InvalidValueException ("Invalid user");
		}
		
		if (body.getFirstName ().trim ().equals ("") || body.getLastName ().trim ().equals ("") || body.getEmail ().trim ().equals ("") || body.getUsername ().trim ().equals ("") || body.getPassword ().trim ().equals ("")) {
			throw new InvalidValueException ("Invalid user");
		}
		
		if (!body.getUsername ().matches ("^[\\w-]+$")) {
			throw new InvalidValueException ("Invalid username");
		}
		
		if (!body.getEmail ().matches ("^[\\w-\\.]+@[\\w-]+\\.[a-zA-z]+$")) {
			throw new InvalidValueException ("Invalid email");
		}
		
		UserRole role = this.roleService.getRoleByName ("USER");
		
		body.setRole (role);
		
		//create user
		User user = this.userService.createUser (body);
		
		return ResponseEntity.ok (new JsonResponse ("Created user", true, user, "/login"));
	}
}
