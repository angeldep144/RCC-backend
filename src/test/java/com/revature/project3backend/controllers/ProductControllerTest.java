package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.services.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductControllerTest {
	private ProductController productController;
	private ProductService productService = Mockito.mock (ProductService.class);
	
	public ProductControllerTest () {
		this.productController = new ProductController (productService);
	}
	
	@Test
	void getProducts () throws InvalidValueException {
		List<Product> products = new ArrayList<>();
		products.add(new Product (1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10));
		products.add(new Product (2, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10));
		ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok (new JsonResponse ("Got " + products.size () + " products", true, products));
		Mockito.when (productService.getProducts ("", 1)).thenReturn (products);

		ResponseEntity<JsonResponse> actualResult = productController.getProducts ("", 1);

		assertEquals (expectedResult, actualResult);
		Mockito.verify(this.productService, Mockito.times(1)).getProducts("", 1);
	}
	
	@Test
	void getProductsWhenSearchQueryIsNull () throws InvalidValueException {
		String searchQuery = null;
		Integer page = 1;

		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.getProducts (searchQuery, page));

		assertEquals ("Error! Invalid search query", exception.getMessage () );
		Mockito.verify(this.productService, Mockito.times(0)).getProducts (Mockito.any(), Mockito.any());
	}
	
	@Test
	void getProductsWhenPageIsNull () {
		String searchQuery = "";
		Integer page = null;

		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> this.productController.getProducts (searchQuery, page));

		assertEquals ("Error! Invalid page", exception.getMessage () );
		Mockito.verify(this.productService, Mockito.times(0)).getProducts (Mockito.any(), Mockito.any());
	}
	
	@Test
	void getProduct () throws InvalidValueException {
		Product product = new Product (1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
		ResponseEntity<JsonResponse> expectedResult = ResponseEntity.ok (new JsonResponse ("Got product", true, product));
		Mockito.when(this.productService.getProduct(product.getId())).thenReturn(product);

		ResponseEntity<JsonResponse> actaulResult = this.productController.getProduct(product.getId());

		assertEquals(expectedResult, actaulResult);
	}
}