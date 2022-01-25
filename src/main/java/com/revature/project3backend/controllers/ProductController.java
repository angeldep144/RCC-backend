package com.revature.project3backend.controllers;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.services.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping ("product")
@CrossOrigin (origins = "http://localhost:4200/", allowCredentials = "true")
public class ProductController {
	private final ProductService productService;
	Logger log = Logger.getLogger(ProductController.class);
	
	@Autowired
	public ProductController (ProductService productService) {
		this.productService = productService;
	}
	
	/**
	 * Gets a List of all products or gets a specific List of items based on the searchQuery argument.
	 *
	 * @param searchQuery A String specifying a product or products
	 * @param page        The Page Number that the user is on
	 * @return Returns a JsonResponse containing a message, status of success, and list of products.
	 * @throws InvalidValueException Thrown when an invalid entry is made into the searchQuery or page value is not accepted
	 */
	@GetMapping
	public ResponseEntity <JsonResponse> getProducts (@RequestParam String searchQuery, @RequestParam Integer page) throws InvalidValueException {
		if (searchQuery == null) {
			throw new InvalidValueException ("searchQuery is bad");
		}
		
		if (page == null) {
			throw new InvalidValueException ("Page value not accepted");
		}
		
		List <Product> products = this.productService.getProducts (searchQuery, page);
		
		return ResponseEntity.ok (new JsonResponse ("Got " + products.size () + " products", true, products));
	}
	
	/**
	 * Gets a Product based on the argument id.
	 *
	 * @param id An Integer associated with a specific Product
	 * @return Returns a Product associated with the argument id.
	 * @throws InvalidValueException Thrown when the product does not exist or the id value is not accepted.
	 */
	@GetMapping ("{id}")
	public ResponseEntity <JsonResponse> getProduct (@PathVariable Integer id) throws InvalidValueException {
		Product product = this.productService.getProduct (id);
		
		return ResponseEntity.ok (new JsonResponse ("Got product", true, product));
	}

	/**
	 * Updates an existing product with new information, if no file is provided the imageUrl will stay the same.
	 * @param productName The product's name.
	 * @param productDescription The product's description.
	 * @param price The product's price.
	 * @param salePrice The product's sale price, null means it's not on sale.
	 * @param id The product's id.
	 * @param file The file for the product's image.
	 * @param stock The amount of stock for that product.
	 * @param imageUrl The existing string for the product's image.
	 * @return It returns a response containing the updated product.
	 */
	@PatchMapping
	public ResponseEntity<JsonResponse> updateProduct(@RequestParam("name") String productName, @RequestParam("description") String productDescription,
		  @RequestParam("price") Double price, @RequestParam(value = "salePrice", required = false) Double salePrice, @RequestParam(value = "id") Integer id,
		  @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "stock", required = false) Integer stock,
		  @RequestParam(value = "imageUrl", required = false) String imageUrl){
		Product product = null;
		try {
			product = new Product(id, productName, productDescription, price.floatValue(), imageUrl, stock);
			if(salePrice != null){
				product.setSalePrice(salePrice.floatValue());
				if(product.getSalePrice() < 0){
					product.setSalePrice(null);
				}
			}

			//Error thrown if the price is negative.
			if(product.getPrice() < 0){
				throw new InvalidValueException("Price cannot be negative.");
			}

		}catch(Exception e){
			log.error(e.getMessage());
		}

		product = this.productService.updateProduct(product, file);

		return ResponseEntity.ok (new JsonResponse ("Product updated ok.", true, product));
	}

	@PostMapping
	public ResponseEntity<JsonResponse> createProduct(@RequestParam("name") String productName, @RequestParam("description") String productDescription,
													  @RequestParam("price") Double price, @RequestParam(value = "salePrice", required = false) Double salePrice,
													  @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "stock", required = false) Integer stock,
													  @RequestParam(value = "imageUrl", required = false) String imageUrl){
		Product product = new Product(0, productName, productDescription, price.floatValue(), imageUrl, salePrice.floatValue(), stock);

		this.productService.createProduct(product);

		return ResponseEntity.ok (new JsonResponse ("Got product updated ok.", true, product));
	}
}
