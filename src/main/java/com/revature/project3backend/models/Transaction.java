package com.revature.project3backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table (name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne (optional = false)
	private User buyer;

	@OneToMany
	private List <CartItem> items;
	
	@Column (nullable = false)
	private Float total;
}
