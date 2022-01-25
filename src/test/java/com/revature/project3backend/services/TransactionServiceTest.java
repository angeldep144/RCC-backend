package com.revature.project3backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.TransactionRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionServiceTest {

	TransactionRepo transactionRepo = Mockito.mock(TransactionRepo.class);
	TransactionService transactionService;

	public TransactionServiceTest(){ this.transactionService = new TransactionService(transactionRepo);}

	@Test
	void createTransaction () throws JsonProcessingException {
		List<CartItem> items = new ArrayList<>();
		List <Transaction> transactions = new ArrayList <> ();

		Product product = new Product(1,"something","something",3000.00f,"image",null,1);

		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", "pass123", items, null );
		CartItem cartItem = new CartItem(1,user,product,1);
		items.add(cartItem);
		Transaction expectedResult = new Transaction(1 , user,"items",300.00f);
		transactions.add(expectedResult);

		Mockito.when(this.transactionRepo.save(expectedResult)).thenReturn(expectedResult);
		Mockito.verify(this.transactionRepo, Mockito.times(1)).save(expectedResult);

		Transaction actualResult = this.transactionService.createTransaction(expectedResult,items);

		assertEquals(expectedResult, actualResult);

		
	}
	
	@Test
	void getTransaction () throws InvalidValueException {
		List<CartItem> items = new ArrayList<>();
		List <Transaction> transactions = new ArrayList <> ();

		Product product = new Product(1,"something","something",3000.00f,"image",null,1);

		User user = new User (1, "john", "doe", "jdoe@mail.com", "jdoe1", "pass123", items, null );
		CartItem cartItem = new CartItem(1,user,product,1);
		items.add(cartItem);
		Transaction expectedResult = new Transaction(1 , user,"items",300.00f);
		transactions.add(expectedResult);

		Mockito.when(this.transactionRepo.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));
		Mockito.verify(this.transactionRepo, Mockito.times(1)).findById(expectedResult.getId());

		Transaction actualResult = this.transactionService.getTransaction(expectedResult.getId());

		assertEquals(expectedResult, actualResult);

		
	}
	
	@Test
	void getTransactionWhenNotFound () throws InvalidValueException {

		Integer transacionId = 4;

		Mockito.when(this.transactionRepo.findById(transacionId)).thenReturn(Optional.ofNullable(null));
		Mockito.verify(this.transactionRepo, Mockito.times(0)).findById(transacionId);


		assertThrows (InvalidValueException.class, ()-> transactionService.getTransaction(2));
		
	}
}