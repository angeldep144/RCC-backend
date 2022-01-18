package com.revature.project3backend.repositories;

import com.revature.project3backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository <User, Integer> {
	User findByUsername (String username);
}
