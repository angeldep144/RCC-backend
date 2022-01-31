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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductControllerTest {
	private final ProductController productController;
	private final ProductService productService = Mockito.mock (ProductService.class);
	private final HttpSession httpSession = Mockito.mock (HttpSession.class);
	
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
		
		Mockito.when (httpSession.getAttribute ("user")).thenReturn (null);
		
		try {
			productController.updateProduct ("product", "description", 13.0f, 11.0f, 1, Mockito.any (), null, "url.com", httpSession);
		} catch (UnauthorizedException | InvalidValueException e) {
			actualResult = e.getMessage ();
		}
		
		assertEquals (expectedResult, actualResult);
	}
	
	@Test
	void updateProductWhenNotAnAdmin () {
		User user = new User ("fname", "lname", "email@email.com", "username", "password");
		
		UserRole role = new UserRole ();
		role.setRole ("USER");
		user.setRole (role);
		
		String expectedResult = "Error! Unauthorized";
		String actualResult = "";
		
		Mockito.when (httpSession.getAttribute ("user")).thenReturn (user);
		
		try {
			productController.updateProduct ("product", "description", 13.0f, 11.0f, 1, Mockito.any (), null, "url.com", httpSession);
		} catch (UnauthorizedException | InvalidValueException e) {
			actualResult = e.getMessage ();
		}
		
		assertEquals (expectedResult, actualResult);
	}
	
	
	@Test
	void updateProductWhenSalePriceIsNegative () throws InvalidValueException {
		Product product = new Product (1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", -13.00f, 10);
		
		UserRole userRole = new UserRole (1, "ADMIN");
		
		User user = new User (1, "John", "Smith", "johnsmith@example.com", "johnsmith", "password", null, null, userRole);
		
		MockHttpSession session = new MockHttpSession ();
		
		session.setAttribute ("user", user);
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.updateProduct (product.getName (), product.getDescription (), product.getPrice (), product.getSalePrice (), product.getId (), null, product.getStock (), product.getImageUrl (), session));
		
		assertEquals ("Error! Sale price cannot negative.", exception.getMessage ());
		
		Mockito.verify (this.productService, Mockito.never ()).updateProduct (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void updateProductWhenSalePriceIsHigherThanPrice () throws InvalidValueException {
		Product product = new Product (1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 13.00f, 10);
		
		UserRole userRole = new UserRole (1, "ADMIN");
		
		User user = new User (1, "John", "Smith", "johnsmith@example.com", "johnsmith", "password", null, null, userRole);
		
		MockHttpSession session = new MockHttpSession ();
		
		session.setAttribute ("user", user);
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.updateProduct (product.getName (), product.getDescription (), product.getPrice (), product.getSalePrice (), product.getId (), null, product.getStock (), product.getImageUrl (), session));
		
		assertEquals ("Error! Sale price cannot be higher than normal price.", exception.getMessage ());
		
		Mockito.verify (this.productService, Mockito.never ()).updateProduct (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void updateProductWhenPriceIsNegative () throws InvalidValueException {
		Product product = new Product (1, "roomba", "description", -12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", -13.00f, 10);
		
		UserRole userRole = new UserRole (1, "ADMIN");
		
		User user = new User (1, "John", "Smith", "johnsmith@example.com", "johnsmith", "password", null, null, userRole);
		
		MockHttpSession session = new MockHttpSession ();
		
		session.setAttribute ("user", user);
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.updateProduct (product.getName (), product.getDescription (), product.getPrice (), product.getSalePrice (), product.getId (), null, product.getStock (), product.getImageUrl (), session));
		
		assertEquals ("Error! Sale price cannot negative.", exception.getMessage ());
		
		Mockito.verify (this.productService, Mockito.never ()).updateProduct (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void createProductWhenNotLoggedIn () {
		String expectedResult = "Error! Unauthorized";
		String actualResult = "";
		
		Mockito.when (httpSession.getAttribute ("user")).thenReturn (null);
		
		try {
			productController.createProduct ("product", "description", 13.0f, 11.0f, Mockito.any (), null, "url.com", httpSession);
		} catch (UnauthorizedException | InvalidValueException e) {
			actualResult = e.getMessage ();
		}
		
		assertEquals (expectedResult, actualResult);
	}
	
	@Test
	void createProductWhenNotAnAdmin () {
		User user = new User ("fname", "lname", "email@email.com", "username", "password");
		
		UserRole role = new UserRole ();
		role.setRole ("USER");
		user.setRole (role);
		
		String expectedResult = "Error! Unauthorized";
		String actualResult = "";
		
		Mockito.when (httpSession.getAttribute ("user")).thenReturn (user);
		
		try {
			productController.createProduct ("product", "description", 13.0f, 11.0f, Mockito.any (), null, "url.com", httpSession);
		} catch (UnauthorizedException | InvalidValueException e) {
			actualResult = e.getMessage ();
		}
		
		assertEquals (expectedResult, actualResult);
	}
	
	@Test
	void createProductWhenSalePriceIsNegative () throws InvalidValueException {
		Product product = new Product (null, "Dog Tricks", "Teach your dog new tricks.", (float) -1.15, "https://s3-alpha.figma.com/hub/file/948140848/1f4d8ea7-e9d9-48b7-b70c-819482fb10fb-cover.png", 13);
		MultipartFile file = null;
		User user = new User (1, "User", "1", "email1", "username13123", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "ADMIN"));
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", user);
		
		InvalidValueException invalidValueException = new InvalidValueException ("Sale price cannot be less than 0");
		
		Mockito.when (productService.createProduct (product, null)).thenThrow (invalidValueException);
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.createProduct (product.getName (), product.getDescription (), product.getPrice (), product.getSalePrice (), file, product.getStock (), product.getImageUrl (), httpSession));
		
		assertEquals ("Error! Sale price cannot be less than 0", exception.getMessage ());
	}
	
	@Test
	void createProductWhenSalePriceIsHigherThanPrice () throws InvalidValueException {
		Product product = new Product (null, "Dog Tricks", "Teach your dog new tricks.", (float) 1.15, "https://s3-alpha.figma.com/hub/file/948140848/1f4d8ea7-e9d9-48b7-b70c-819482fb10fb-cover.png", 13);
		MultipartFile file = null;
		User user = new User (1, "User", "1", "email1", "username13123", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "ADMIN"));
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", user);
		
		InvalidValueException invalidValueException = new InvalidValueException ("Sale price cannot be higher than normal price.");
		
		Mockito.when (productService.createProduct (product, null)).thenThrow (invalidValueException);
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.createProduct (product.getName (), product.getDescription (), product.getPrice (), product.getSalePrice (), file, product.getStock (), product.getImageUrl (), httpSession));
		
		assertEquals ("Error! Sale price cannot be higher than normal price.", exception.getMessage ());
	}
	
	@Test
	void createProductWhenPriceIsNegative () throws InvalidValueException {
		Product product = new Product (null, "Dog Tricks", "Teach your dog new tricks.", (float) -1.15, "https://s3-alpha.figma.com/hub/file/948140848/1f4d8ea7-e9d9-48b7-b70c-819482fb10fb-cover.png", 13);
		MultipartFile file = null;
		User user = new User (1, "User", "1", "email1", "username13123", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "ADMIN"));
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", user);
		
		InvalidValueException invalidValueException = new InvalidValueException ("Price cannot be less than 0");
		
		Mockito.when (productService.createProduct (product, null)).thenThrow (invalidValueException);
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.createProduct (product.getName (), product.getDescription (), product.getPrice (), product.getSalePrice (), file, product.getStock (), product.getImageUrl (), httpSession));
		
		assertEquals ("Error! Price cannot be less than 0", exception.getMessage ());
	}
	
	@Test
	void createProduct () throws InvalidValueException, UnauthorizedException {
		Product product = new Product (null, "Dog Tricks", "Teach your dog new tricks.", (float) 1.15, "https://s3-alpha.figma.com/hub/file/948140848/1f4d8ea7-e9d9-48b7-b70c-819482fb10fb-cover.png", 13);
		Product actualResult = new Product (1, "Dog Tricks", "Teach your dog new tricks.", (float) 1.15, "https://s3-alpha.figma.com/hub/file/948140848/1f4d8ea7-e9d9-48b7-b70c-819482fb10fb-cover.png", 13);
		MultipartFile file = null;
		User user = new User (1, "User", "1", "email1", "username13123", "password", new ArrayList <> (), new ArrayList <> (), new UserRole (2, "ADMIN"));
		MockHttpSession httpSession = new MockHttpSession ();
		httpSession.setAttribute ("user", user);
		
		Mockito.when (productService.createProduct (product, file)).thenReturn (actualResult);
		
		ResponseEntity <JsonResponse> expectedResult = ResponseEntity.ok (new JsonResponse ("Got product updated ok.", true, actualResult, "/product/" + actualResult.getId ()));
		
		assertEquals (expectedResult, productController.createProduct (actualResult.getName (), actualResult.getDescription (), actualResult.getPrice (), actualResult.getSalePrice (), file, actualResult.getStock (), actualResult.getImageUrl (), httpSession));
	}
}