package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping ("transaction")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials = "true")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService)
	{
		this.transactionService = transactionService;
	}

	@PostMapping
	public ResponseEntity <JsonResponse> createTransaction (@RequestBody Transaction transaction) throws UnauthorizedException {

		User valUser = transactionService.validateUserInfo(transaction.getBuyer().getEmail());

		if(valUser == null)
		{
			throw new UnauthorizedException();
		}

		if(transaction.getTotal()!=null) {
			transaction.setTotal(transactionService.total(transaction));
		}

		return ResponseEntity.ok(new JsonResponse("transaction complete", true, transaction));
	}
}
