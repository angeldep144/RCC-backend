package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping ("product")
@CrossOrigin (origins = "http://localhost:4200", allowCredentials = "true")
public class ProductController {

	private final ProductService productService;

	@Autowired
	private ProductController(ProductService productService){
		this.productService = productService;
	}

	/**
	 * Gets a List of all products or gets a specific List of items based on the searchQuery argument.
	 * @param searchQuery A String specifying a product or products
	 * @param page The Page Number that the user is on
	 * @return Returns a JsonResponse containing a message, status of success, and list of products.
	 * @throws InvalidValueException Thrown when an invalid entry is made into the searchQuery or page value is not accepted
	 */
	@GetMapping
	public ResponseEntity <JsonResponse> getProducts (@RequestParam String searchQuery, @RequestParam Integer page) throws InvalidValueException {

		if (searchQuery == null){
			throw new InvalidValueException("searchQuery is bad");
		} else if (page == null){
			throw new InvalidValueException("Page value not accepted");
		}

		List<Product> products = this.productService.getProducts(searchQuery, page);

		return ResponseEntity.ok(new JsonResponse("Got " + products.size(), true, products));
	}

	/**
	 * Gets a Product based on the argument id.
	 * @param id An Integer associated with a specific Product
	 * @return Returns a Product associated with the argument id.
	 * @throws InvalidValueException Thrown when the product does not exist or the id value is not accepted.
	 */
	@GetMapping("{id}")
	public ResponseEntity <JsonResponse> getProduct (@PathVariable Integer id) throws InvalidValueException {

		Product product = this.productService.getProduct(id);

		return ResponseEntity.ok(new JsonResponse("Got " + product.getName(), true, product));


	}
}
