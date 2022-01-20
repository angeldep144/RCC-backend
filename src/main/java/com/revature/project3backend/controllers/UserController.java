package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("user")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials = "true")
public class UserController {
	@PostMapping
	public ResponseEntity <JsonResponse> createUser (@RequestBody User body) throws InvalidValueException {
		//todo
		
		return ResponseEntity.ok (new JsonResponse ("Created user", true, null, "/login"));
	}
}
