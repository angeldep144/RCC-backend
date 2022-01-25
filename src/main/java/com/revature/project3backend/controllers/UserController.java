package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("user")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials = "true")
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController (UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Creates a user with the given fields
	 *
	 * @param body Is converted to an object from the given fields
	 * @return A ResponseEntity
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
		
		//create user
		User user = this.userService.createUser (body);
		
		return ResponseEntity.ok (new JsonResponse ("Created user", true, user, "/login"));
	}
}
