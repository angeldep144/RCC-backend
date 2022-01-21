package com.revature.project3backend.repositories;

import com.revature.project3backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository <User, Integer> {
	/**
	 *
	 * @param username a string that will search the database in the Users table for a matching value in the username column
	 * @return The user found in the database and if none is found it will return null
	 */
	User findByUsername (String username);

	/**
	 *
	 * @param email a string that will search the database in the Users table for a matching value in the emails column
	 * @return The user found in the database and if none is found it will return null
	 */
	User findByEmail (String email);
}
