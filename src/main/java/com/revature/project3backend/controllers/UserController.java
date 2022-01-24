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

import javax.management.relation.Role;

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
		UserRole role = this.roleService.getRoleByName("USER");
		body.setRole(role);
		User returnUser = this.userService.createUser (body);
		
		return ResponseEntity.ok (new JsonResponse ("Created user", true, returnUser, "/login"));
	}
}
