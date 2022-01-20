package com.revature.project3backend.controllers;

import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@PostMapping
	public ResponseEntity <JsonResponse> createUser (@RequestBody User body) {
		
		User returnUser = this.userService.createUser (body);
		
		if (returnUser.getFirstName ().equals ("bad user")) {
			return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (new JsonResponse ("Username is already taken please try another one", false, null));
		} else if (returnUser.getFirstName ().equals ("bad email")) {
			return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (new JsonResponse ("Email is already registered", false, null));
		}
		
		return ResponseEntity.ok (new JsonResponse ("Created user", true, null, "/login"));
		
	}
}
