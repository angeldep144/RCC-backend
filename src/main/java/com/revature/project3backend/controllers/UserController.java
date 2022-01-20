package com.revature.project3backend.controllers;

import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("user")
@CrossOrigin (origins = "http://localhost:4200", allowCredentials = "true")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}


	/**
	 * Creates a user with the given fields
	 * @param body Is converted to an object from the given fields
	 * @return
	 */
	@PostMapping
	public ResponseEntity <JsonResponse> createUser (@RequestBody User body) {

		User returnUser = this.userService.createUser(body);

		if (returnUser.getId() == -1) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new JsonResponse("Username is already taken please try another one", false, null));
		}else if (returnUser.getId() == -2) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new JsonResponse("Email is already registered", false, null));
		}

		return ResponseEntity.ok (new JsonResponse ("Created user", true, returnUser, "/login"));
	}

	@GetMapping
	public ResponseEntity <JsonResponse> getUsers() {//added get all users to test the user functionality
		List<User> userList = userService.getUsers();

		if(userList == null) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new JsonResponse("Could not fetch all users", false, null));
		}

		return ResponseEntity.ok (new JsonResponse ("Got all users", true, userList, "/login"));
	}

}
