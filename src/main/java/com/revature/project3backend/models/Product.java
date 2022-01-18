package com.revature.project3backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class Product {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (nullable = false)
	private String name;
	
	@Column (nullable = false)
	private String description;
	
	@Column (nullable = false)
	private Float price;
	
	private String imageUrl;
	
	/**
	 * null means not on sale
	 */
	private Float salePrice;
	
	@Column// (nullable = false)
	private Integer stock;
}
