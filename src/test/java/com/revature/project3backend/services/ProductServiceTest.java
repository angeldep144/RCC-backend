package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.repositories.ProductRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductServiceTest {
	ProductRepo productRepo = Mockito.mock (ProductRepo.class);
	ProductService productService;
	
	List <Product> products = new ArrayList <> ();
	
	public ProductServiceTest () {
		this.productService = new ProductService (this.productRepo);
		
		products.add (new Product (1, "name", "description", 10F, "", null, 10));
		products.add (new Product (2, "name", "description", 10F, "", null, 10));
		products.add (new Product (3, "name", "description", 10F, "", null, 10));
	}
	
	@Test
	void getProducts () {
		String searchQuery = "query";
		
		Page <Product> page = new PageImpl <> (products);
		
		Mockito.when (productRepo.findByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining (Mockito.eq (searchQuery), Mockito.eq (searchQuery), Mockito.any (PageRequest.class))).thenReturn (page);
		
		assertEquals (products, productService.getProducts (searchQuery, 0));
	}
	
	@Test
	void getProduct () throws InvalidValueException {
		int id = 1;
		
		Mockito.when (productRepo.findById (id)).thenReturn (Optional.of (products.get (0)));
		
		assertEquals (products.get (0), productService.getProduct (id));
	}
	
	@Test
	void getProductWhenNotFound () throws InvalidValueException {
		int id = 1;
		
		Mockito.when (productRepo.findById (id)).thenReturn (Optional.empty ());
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> productService.getProduct (id));
		
		assertEquals ("Error! Invalid product id", exception.getMessage ());
	}
	
	@Test
	void reduceStock () throws InvalidValueException {
		int stock = 10;
		
		Product product = new Product (3, "name", "description", 10F, "", null, stock);
		
		int quantity = 3;
		
		productService.reduceStock (product, quantity);
		
		assertEquals (stock - quantity, product.getStock ());
		
		Mockito.verify (productRepo).save (product);
	}
	
	@Test
	void reduceStockWhenStockWouldBeBelowZero () throws InvalidValueException {
		int stock = 2;
		
		Product product = new Product (3, "name", "description", 10F, "", null, stock);
		
		int quantity = 3;
		
		InvalidValueException exception = assertThrows (InvalidValueException.class, () -> productService.reduceStock (product, quantity));
		
		assertEquals ("Error! Invalid quantity", exception.getMessage ());
		
		assertEquals (stock, product.getStock ());
		
		Mockito.verify (productRepo, Mockito.never ()).save (Mockito.any ());
	}
}