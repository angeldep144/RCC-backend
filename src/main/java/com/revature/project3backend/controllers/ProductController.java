package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The ProductController handles requests concerning products
 */
@RestController
@RequestMapping ("product")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials = "true")
public class ProductController {
	/**
	 * The instance of ProductService to use
	 */
	private final ProductService productService;
	
	/**
	 * This constructor is automatically called by Spring
	 * 
	 * @param productService The instance of ProductService to use
	 */
	@Autowired
	public ProductController (ProductService productService) {
		this.productService = productService;
	}
	
	/**
	 * Gets products that match the given searchQuery
	 *
	 * @param searchQuery The query to use to search the products
	 * @param page The page of products to get
	 * @return A ResponseEntity used to create the HTTP response, contains the products found
	 * @throws InvalidValueException Thrown when validation fails
	 */
	@GetMapping
	public ResponseEntity <JsonResponse> getProducts (@RequestParam String searchQuery, @RequestParam Integer page) throws InvalidValueException {
		if (searchQuery == null) {
			throw new InvalidValueException ("Invalid search query");
		}
		
		if (page == null) {
			throw new InvalidValueException ("Invalid page");
		}
		
		List <Product> products = this.productService.getProducts (searchQuery, page);
		
		return ResponseEntity.ok (new JsonResponse ("Got " + products.size () + " products", true, products));
	}
	
	/**
	 * Gets a product with a given id
	 *
	 * @param id The id of the product to get
	 * @return A ResponseEntity used to create the HTTP response, contains the products found
	 * @throws InvalidValueException Thrown when validation fails
	 */
	@GetMapping ("{id}")
	public ResponseEntity <JsonResponse> getProduct (@PathVariable Integer id) throws InvalidValueException {
		Product product = this.productService.getProduct (id);
		
		return ResponseEntity.ok (new JsonResponse ("Got product", true, product));
	}
}
