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
	 * Creates a user with the given fields
	 *
	 * @param body Is converted to an object from the given fields
	 * @return A ResponseEntity
	 */
	@PostMapping
	public ResponseEntity <JsonResponse> createUser (@RequestBody User body) throws InvalidValueException {
		UserRole role = this.roleService.getRoleByName("USER");
		body.setRole(role);
		User returnUser = this.userService.createUser (body);
		
		return ResponseEntity.ok (new JsonResponse ("Created user", true, returnUser, "/login"));
	}
}
