package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidCredentialsException;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.CreateSessionBody;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping ("session")
@CrossOrigin (origins = "http://localhost:4200", allowCredentials = "true")
public class SessionController {
	private final UserService userService;
	
	@Autowired
	public SessionController (UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public ResponseEntity <JsonResponse> createSession (@RequestBody CreateSessionBody body, HttpSession httpSession) throws InvalidCredentialsException, InvalidValueException {
		if (body.getIdentifier () == null || body.getPassword () == null) {
			throw new InvalidValueException ("Invalid credentials");
		}
		
		User user = this.userService.loginUser (body.getIdentifier (), body.getPassword ());
		
		httpSession.setAttribute ("user", user);
		
		return ResponseEntity.ok (new JsonResponse ("Logged in", true, user, "/"));
	}
	
	@DeleteMapping
	public ResponseEntity <JsonResponse> deleteSession (HttpSession httpSession) {
		httpSession.invalidate ();
		
		return ResponseEntity.ok (new JsonResponse ("Logged out", true, null, "/login"));
	}
}
