package com.revature.project3backend.repositories;

import com.revature.project3backend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository <Product, Integer> {
	Page <Product> findByNameOrDescriptionIgnoreCaseContaining (String name, String description, Pageable pageable);
}
