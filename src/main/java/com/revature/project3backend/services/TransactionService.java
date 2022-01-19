package com.revature.project3backend.services;

import com.revature.project3backend.repositories.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TransactionService {
	private final TransactionRepo transactionRepo;
	
	@Autowired
	public TransactionService (TransactionRepo transactionRepo) {
		this.transactionRepo = transactionRepo;
	}
}
