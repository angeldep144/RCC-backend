package com.revature.project3backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class Transaction {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne (optional = false)
	@JsonProperty (access = JsonProperty.Access.WRITE_ONLY)
	private User buyer;

	@Column (length = 1000)
	private String items;
	
	@Column (nullable = false)
	private Float total;
	
	public Transaction (User buyer) {
		this.buyer = buyer;
	}
}
