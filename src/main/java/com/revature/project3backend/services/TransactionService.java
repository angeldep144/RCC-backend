package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.TransactionRepo;
import com.revature.project3backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TransactionService {
	private final TransactionRepo transactionRepo;
	
	@Autowired
	public TransactionService (TransactionRepo transactionRepo) {
		this.transactionRepo = transactionRepo;
	}
	
	public Transaction createTransaction (Transaction transaction) {
		float total = 0f;
		
		for (CartItem item : transaction.getItems ()) {
			Product product = item.getProduct ();
			
			total += (product.getSalePrice () == null ? product.getPrice () : product.getSalePrice ()) * item.getQuantity ();
		}
		
		transaction.setTotal (total);
		
		return transactionRepo.save (transaction);
	}
	
	public Transaction getTransaction (Integer transactionId) throws InvalidValueException {
		Transaction transaction = transactionRepo.findById (transactionId).orElse (null);
		
		if (transaction == null) {
			throw new InvalidValueException ("Invalid transaction id");
		}
		
		return transaction;
	}
}
