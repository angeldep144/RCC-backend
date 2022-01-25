package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
	
	private final ProductRepo productRepo;
	
	private final int postsPerPage = 20;
	
	@Autowired
	public ProductService (ProductRepo productRepo) {
		this.productRepo = productRepo;
	}
	
	public List <Product> getProducts (String searchQuery, Integer page) {
		return this.productRepo.findByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining (searchQuery, searchQuery, PageRequest.of (page, postsPerPage, Sort.by ("name"))).getContent ();
	}
	
	public Product getProduct (Integer id) throws InvalidValueException {
		Product product = this.productRepo.findById (id).orElse (null);
		
		if (product == null) {
			throw new InvalidValueException ("Invalid product id");
		}
		
		return product;
	}

	/**
	 * Updates the product information in the database
	 * @param product The new product information.
	 * @param file The new image for the product if desired.
	 * @return The updated product.
	 */
    public Product updateProduct(Product product, MultipartFile file) {
		Product updatedProduct = this.productRepo.save(product);

		return updatedProduct;
    }

	public Product createProduct (Product product) {
		Product product2 = this.productRepo.save(product);

		return product2;
	}
}
