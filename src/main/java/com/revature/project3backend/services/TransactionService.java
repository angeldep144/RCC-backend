package com.revature.project3backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.repositories.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransactionService {
	private final TransactionRepo transactionRepo;
	
	private final ObjectMapper json = new ObjectMapper ();
	
	@Autowired
	public TransactionService (TransactionRepo transactionRepo) {
		this.transactionRepo = transactionRepo;
	}

	/**
	 * @param transaction Grabs transaction filled with user , and cart items
	 * @param items gets products to sum up the total
	 * @return returns completed Transaction is returned
	 * @throws JsonProcessingException throws when error when parsing
	 */
	public Transaction createTransaction (Transaction transaction, List <CartItem> items) throws JsonProcessingException {
		float total = 0f;
		
		for (CartItem item : items) {
			Product product = item.getProduct ();
			
			total += (product.getSalePrice () == null ? product.getPrice () : product.getSalePrice ()) * item.getQuantity ();
		}
		
		transaction.setTotal (total);
		transaction.setItems (json.writeValueAsString (items));
		
		return transactionRepo.save (transaction);
	}

	/**
	 * @param transactionId Grabs transaction ID to find by
	 * @return returns the transaction that was found by ID
	 * @throws InvalidValueException throws when validations fail
	 */
	public Transaction getTransaction (Integer transactionId) throws InvalidValueException {
		Transaction transaction = transactionRepo.findById (transactionId).orElse (null);
		
		if (transaction == null) {
			throw new InvalidValueException ("Invalid transaction id");
		}
		
		return transaction;
	}
}
