package com.revature.project3backend.services;

import com.revature.project3backend.models.Product;
import com.revature.project3backend.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

	public List<Product> getProducts (String searchQuery, Integer page){

		Pageable pageable = PageRequest.of (page, postsPerPage, Sort.by ("created").descending ());

		return this.productRepo.findAll(pageable).getContent();
	}

	public Product getProduct (Integer id){
		return this.productRepo.findById(id).orElse(null);
	}
}
