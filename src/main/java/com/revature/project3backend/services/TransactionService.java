package com.revature.project3backend.services;

import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionService {
	private final TransactionRepo transactionRepo;
	
	@Autowired
	public TransactionService (TransactionRepo transactionRepo) {
		this.transactionRepo = transactionRepo;
	}

	public User validateUserInfo(String email)
	{
		User checkUser = transactionRepo.findEmailByBuyer(email);

		if(checkUser == null)
			return null;

		else
			return checkUser;
	}

	public Float total(Transaction transaction)
	{
		Transaction tempTrans = transactionRepo.getById(transaction.getId());

		List<CartItem> items = tempTrans.getItems();
		Product product = new Product();

		Float totalCost = 0f;

		if(transaction.getTotal() == null) {
			for (int i = 0; i < items.size(); i++) {
				product = items.get(i).getProduct();
				if(product.getSalePrice() == null) {
					totalCost += product.getPrice() * items.get(i).getQuantity();
				}
				else
					totalCost += product.getSalePrice() * items.get(i).getQuantity();
			}
		}

		return totalCost;
	}

	public void createTransaction(Transaction transaction)
	{
		this.transactionRepo.save(transaction);
	}
}
