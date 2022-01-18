package com.revature.project3backend.repositories;

import com.revature.project3backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository <Product, Integer> {}
