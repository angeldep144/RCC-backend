package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("product")
@CrossOrigin (origins = "http://localhost:4200", allowCredentials = "true")
public class ProductController {
	@PostMapping
	public ResponseEntity <JsonResponse> getProducts (@RequestParam String searchQuery, @RequestParam Integer page) throws InvalidValueException {
		//todo
		
		return null;
	}
}
