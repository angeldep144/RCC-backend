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

	@GetMapping("{id}")
	public ResponseEntity <JsonResponse> getProduct (@PathVariable Integer id) throws InvalidValueException {

		Product product = this.productService.getProduct(id);


		return ResponseEntity.ok(new JsonResponse("Got " + product.getName(), true, product));


	}
}
