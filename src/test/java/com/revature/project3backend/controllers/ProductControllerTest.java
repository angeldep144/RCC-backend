package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.User;
import com.revature.project3backend.models.UserRole;
import com.revature.project3backend.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {
	private final ProductController productController;
	private final ProductService productService = Mockito.mock (ProductService.class);
	private final HttpSession httpSession = Mockito.mock(HttpSession.class);
	
	public ProductControllerTest () {
		this.productController = new ProductController (productService);
	}
	
	@Test
	void getProducts () throws InvalidValueException {
		List <Product> products = new ArrayList <> ();
		
		products.add (new Product (1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10));
		products.add (new Product (2, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10));
		
		String query = "";
		int page = 0;
		
		Mockito.when (productService.getProducts (query, page)).thenReturn (products);
		
		assertEquals (ResponseEntity.ok (new JsonResponse ("Got " + products.size () + " products", true, products)), productController.getProducts (query, page));
		
		Mockito.verify (productService).getProducts (query, page);
	}
	
	@Test
	void getProductsWhenSearchQueryIsNull () throws InvalidValueException {
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.getProducts (null, 1));
		
		assertEquals ("Error! Invalid search query", exception.getMessage ());
		
		Mockito.verify (productService, Mockito.never ()).getProducts (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void getProductsWhenPageIsNull () {
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> productController.getProducts ("", null));
		
		assertEquals ("Error! Invalid page", exception.getMessage ());
		
		Mockito.verify (productService, Mockito.never ()).getProducts (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void getProduct () throws InvalidValueException {
		Product product = new Product (1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
		
		Mockito.when (productService.getProduct (product.getId ())).thenReturn (product);
		
		assertEquals (ResponseEntity.ok (new JsonResponse ("Got product", true, product)), productController.getProduct (product.getId ()));
		
		Mockito.verify (productService).getProduct (product.getId ());
	}
	
	//todo this is a service test, it doesn't call the controller. It's also redundant because the service is already tested for this (ProductServiceTest.getProductWhenNotFound). Should probably just remove this test
	@Test
	void getProductNegative () throws InvalidValueException {
		String expectedResult = "Error! Invalid product id";
		String actualResult = null;
		
		Mockito.when (productService.getProduct (99)).thenThrow (new InvalidValueException ("Invalid product id"));
		
		try {
			productService.getProduct (99);
		} catch (InvalidValueException e) {
			actualResult = e.getMessage ();
		}
		
		assertEquals (expectedResult, actualResult);
	}
	
	@Test
	void updateProductWhenNotLoggedIn () {
		String expectedResult = "Error! Unauthorized";
		String actualResult = "";

		Mockito.when(httpSession.getAttribute("user")).thenReturn(null);

		try{
			productController.updateProduct("product", "description", 13.0f, 11.0f, 1, Mockito.any(), null, "url.com", httpSession);
		} catch (UnauthorizedException | InvalidValueException e) {
			actualResult = e.getMessage();
		}

		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	void updateProductWhenNotAnAdmin () {
		User user = new User("fname", "lname", "email@email.com", "username", "password");

		UserRole role = new UserRole();
		role.setRole("USER");
		user.setRole(role);

		String expectedResult = "Error! Unauthorized";
		String actualResult = "";

		Mockito.when(httpSession.getAttribute("user")).thenReturn(user);

		try{
			productController.updateProduct("product", "description", 13.0f, 11.0f, 1, Mockito.any(), null, "url.com", httpSession);
		} catch (UnauthorizedException | InvalidValueException e) {
			actualResult = e.getMessage();
		}

		assertEquals(expectedResult, actualResult);
	}


	@Test
	void updateProductWhenSalePriceIsNegative () {
		fail ();
	}

	@Test
	void updateProductWhenSalePriceIsHigherThanPrice () throws InvalidValueException {
		//todo call productController.updateProduct with sale price negative
		
		//todo assert that the sale price is null
		
		fail ();
		
		//todo should we do mockito verifys and check return value here since we already test it in updateProduct test?
		
		Mockito.verify (this.productService, Mockito.never ()).updateProduct (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void updateProductWhenPriceIsNegative () throws InvalidValueException {
		MultipartFile file = null;
		Product product = new Product (1, "Dog Tricks", "Teach your dog new tricks.", (float) -1.15, null, 13);
		InvalidValueException invalidValueException = new InvalidValueException ("Sale price cannot be higher than normal price.");
		
		//todo assertThrows and test message
		//todo this doesn't ever call the controller method
		
		fail ();
		
		Mockito.verify (this.productService, Mockito.never ()).updateProduct (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void updateProduct () throws InvalidValueException {
		MultipartFile file = null;
		Product product = new Product (1, "Dog Tricks", "Teach your dog new tricks.", (float) 1.15, null, 13);
		
		Mockito.when (productService.updateProduct (product, file)).thenReturn (product);
		
		Product actual = this.productService.updateProduct (product, file);
		
		assertEquals (product, actual);
		
		//todo this doesn't ever call the controller method
		
		fail ();
		
		//todo verify that methods were run
		//todo test ResponseEntity
	}
	
	@Test
	void createProductWhenNotLoggedIn () {
		fail ();
		
		//todo assertThrows and test message
		//todo verify that methods were not run
	}
	
	@Test
	void createProductWhenNotAnAdmin () {
		fail ();
		
		//todo assertThrows and test message
		//todo verify that methods were not run
	}
	
	@Test
	void createProductWhenSalePriceIsNegative () {
		fail ();
		
		//todo assertThrows and test message
		//todo verify that methods were not run
	}
	
	@Test
	void createProductWhenSalePriceIsHigherThanPrice () {
		fail ();
		
		//todo assertThrows and test message
		//todo verify that methods were not run
	}
	
	@Test
	void createProductWhenPriceIsNegative () {
		fail ();
		
		//todo assertThrows and test message
		//todo verify that methods were not run
	}
	
	@Test
	void createProduct () {
		fail ();
		
		//todo verify that methods were run
		//todo test ResponseEntity
	}
}