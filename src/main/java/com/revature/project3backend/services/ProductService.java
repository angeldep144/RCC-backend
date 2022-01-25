package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

	public Product createProduct (Product product) throws InvalidValueException {
		Product product2 = this.productRepo.save(product);

		if((product2.getSalePrice()<0) || (product2.getPrice()<0)){
			throw new InvalidValueException("Price cannot be less than 0");
		}
		if(product2.getSalePrice()>product2.getPrice()){
			throw new InvalidValueException("Sales price cannot be greater than original price");
		}
		return product2;
	}
}
