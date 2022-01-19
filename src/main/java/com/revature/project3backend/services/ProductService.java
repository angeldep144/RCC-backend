package com.revature.project3backend.services;

import com.revature.project3backend.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductService {
	private final ProductRepo productRepo;
	
	@Autowired
	public ProductService (ProductRepo productRepo) {
		this.productRepo = productRepo;
	}
}
