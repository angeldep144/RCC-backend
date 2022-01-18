package com.revature.project3backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table
public class User {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
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
}
