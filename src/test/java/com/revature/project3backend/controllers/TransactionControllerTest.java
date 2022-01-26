package com.revature.project3backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.ProductService;
import com.revature.project3backend.services.TransactionService;
import com.revature.project3backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerTest {
	private TransactionController transactionController;
	private final TransactionService transactionService = Mockito.mock(TransactionService.class);
	private final UserService userService = Mockito.mock(UserService.class);
	private final ProductService productService = Mockito.mock(ProductService.class);

	public TransactionControllerTest()
	{
		this.transactionController = new TransactionController(this.transactionService,this.userService,this.productService);
	}

	@Test
	void createTransaction () throws JsonProcessingException {
		User user = new User ("first", "last", "email", "username", "password");
		Product product = new Product(1, "roomba", "description", 10f, "1.jpg", null, 10);
		Product product0 = new Product(12, "roomba", "description", 10f, "2.jpg", null, 10);
		List<CartItem> items = new ArrayList<>();

		items.add(new CartItem(user,product,1));
		items.add(new CartItem(user,product0,1));

		Transaction transaction = new Transaction(user);
		Transaction expected = new Transaction(1,user,items.toString(),20.00f);


		Mockito.when(transactionService.createTransaction(transaction,items)).thenReturn(expected);
		Transaction actual = transactionService.createTransaction(transaction,items);

		assertEquals(expected.toString(),actual.toString());


	}

	@Test
	void createTransactionWhenNotLoggedIn () throws JsonProcessingException, InvalidValueException, UnauthorizedException {
		User user = new User ("first", "last", "email", "username", "password");
		MockHttpSession mockHttpSession = new MockHttpSession ();
		mockHttpSession.setAttribute("user", null);
		Product product = new Product(1, "roomba", "description", 10f, "1.jpg", null, 10);
		List<CartItem> items = new ArrayList<>();

		items.add(new CartItem(user,product,1));
		user.setCart(items);

		UnauthorizedException exception = assertThrows(UnauthorizedException.class,()->this.transactionController.createTransaction(mockHttpSession));

		assertEquals("Error! Unauthorized",exception.getMessage());


	}

	@Test
	void createTransactionWhenCartIsEmpty () throws JsonProcessingException, InvalidValueException, UnauthorizedException {


		User user = new User ("first", "last", "email", "username", "password");
		MockHttpSession mockHttpSession = new MockHttpSession ();
		mockHttpSession.setAttribute("user", user);
		List<CartItem> items = new ArrayList<>();

		user.setCart(items);
		InvalidValueException exception = assertThrows(InvalidValueException.class,()->this.transactionController.createTransaction(mockHttpSession));

		assertEquals("Error! Invalid cart",exception.getMessage());
	}

	@Test
	void getTransaction () throws JsonProcessingException, InvalidValueException, UnauthorizedException {
		User user = new User ("first", "last", "email", "username", "password");
		Product product = new Product(1, "roomba", "description", 10f, "1.jpg", null, 10);
		Product product0 = new Product(12, "roomba", "description", 10f, "2.jpg", null, 10);
		List<CartItem> items = new ArrayList<>();

		items.add(new CartItem(user,product,1));
		items.add(new CartItem(user,product0,1));

		Transaction transaction = new Transaction(user);
		Transaction expectedCreate = new Transaction(1,user,items.toString(),20.0f);


		Mockito.when(transactionService.createTransaction(transaction,items)).thenReturn(expectedCreate);
		Transaction create = transactionService.createTransaction(transaction,items);

		Mockito.when(transactionService.getTransaction(create.getId())).thenReturn(expectedCreate);

		Transaction actual = transactionService.getTransaction(create.getId());
		assertEquals(expectedCreate,actual);



	}

	@Test
	void getTransactionWhenNotLoggedIn () throws InvalidValueException, UnauthorizedException, JsonProcessingException {

		User user = new User ("first", "last", "email", "username", "password");
		Product product = new Product(1, "roomba", "description", 10f, "1.jpg", null, 10);
		Product product0 = new Product(12, "roomba", "description", 10f, "2.jpg", null, 10);

		MockHttpSession mockHttpSession = new MockHttpSession ();
		mockHttpSession.setAttribute("buyer", null);
		List<CartItem> items = new ArrayList<>();
		items.add(new CartItem(user,product,1));
		items.add(new CartItem(user,product0,1));

		Transaction expected = new Transaction(1,user,items.toString(),20.0f);

		user.setCart(items);
		UnauthorizedException exception = assertThrows(UnauthorizedException.class,()->this.transactionController.getTransaction(expected.getId(),mockHttpSession));

		assertEquals("Error! Unauthorized",exception.getMessage());


	}

	@Test
	void getTransactionWhenTransactionWasMadeByOtherUser () throws InvalidValueException, UnauthorizedException {


		User user0 = new User ("first0", "last0", "email0", "username0", "password0");
		User user = new User ("first", "last", "email", "username", "password");
		MockHttpSession mockHttpSession = new MockHttpSession ();
		mockHttpSession.setAttribute("buyer", user0);
		List<CartItem> items = new ArrayList<>();
		user.setCart(items);
		user.setId(1);
		user0.setId(2);

		UnauthorizedException exception = assertThrows(UnauthorizedException.class,()->this.transactionController.getTransaction(1,mockHttpSession));

		assertEquals("Error! Unauthorized",exception.getMessage());

	}

}