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
public class CartItem {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne (optional = false)
	private User buyer;
	
	@OneToOne (optional = false)
	private Product product;
	
	@Column (nullable = false)
	private Integer quantity;
	
	public CartItem (User buyer, Product product, Integer quantity) {
		this.buyer = buyer;
		this.product = product;
		this.quantity = quantity;
	}
}
