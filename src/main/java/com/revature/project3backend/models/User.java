package com.revature.project3backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * User model for carrying carts for products, and storing transaction history
 * Contains ID, First name, Last name, Email, Username, Password, List of cart items, List of transactions
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table (name = "users")
public class User {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (nullable = false)
	private String firstName;
	
	@Column (nullable = false)
	private String lastName;
	
	@Column (unique = true, nullable = false)
	private String email;
	
	@Column (unique = true, nullable = false)
	private String username;
	
	@Column (nullable = false)
	@JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@OneToMany (mappedBy = "buyer")
	private List <CartItem> cart;
	
	@OneToMany (mappedBy = "buyer")
	private List <Transaction> transactions;
	
	public User (String firstName, String lastName, String email, String username, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	@ManyToOne
//TODO: set default role to USER
	private UserRole role;
}
