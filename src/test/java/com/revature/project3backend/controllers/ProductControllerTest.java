package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductControllerTest {
	private final ProductController productController;
	private final ProductService productService = Mockito.mock (ProductService.class);
	
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
		
		Mockito.verify (this.productService).getProducts (query, page);
	}
	
	@Test
	void getProductsWhenSearchQueryIsNull () throws InvalidValueException {
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.getProducts (null, 1));
		
		assertEquals ("Error! Invalid search query", exception.getMessage ());
		
		Mockito.verify (this.productService, Mockito.never ()).getProducts (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void getProductsWhenPageIsNull () {
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.getProducts ("", null));
		
		assertEquals ("Error! Invalid page", exception.getMessage ());
		
		Mockito.verify (this.productService, Mockito.never ()).getProducts (Mockito.any (), Mockito.any ());
	}
	
	@Test
	void getProduct () throws InvalidValueException {
		Product product = new Product (1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
		
		Mockito.when (this.productService.getProduct (product.getId ())).thenReturn (product);
		
		assertEquals (ResponseEntity.ok (new JsonResponse ("Got product", true, product)), this.productController.getProduct (product.getId ()));
		
		Mockito.verify (this.productService).getProduct (product.getId ());
	}
}