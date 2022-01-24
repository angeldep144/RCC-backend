package com.revature.project3backend.repositories;

import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository <Transaction, Integer> {

    User findEmailByBuyer(String email);



}
