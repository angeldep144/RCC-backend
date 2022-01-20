package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping ("transaction")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials = "true")
public class TransactionController {
	@PostMapping
	public ResponseEntity <JsonResponse> createTransaction (HttpSession httpSession) throws UnauthorizedException {
		if (httpSession.getAttribute ("user") == null) {
			throw new UnauthorizedException ();
		}
		
		//todo
		return null;
	}
}
