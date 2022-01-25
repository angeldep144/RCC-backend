package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidCredentialsException;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
	private final UserRepo userRepo;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();
	
	@Autowired
	public UserService (UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	/**
	 * Validates a user's credentials against what is in the database.
	 *
	 * @param username The user's username.
	 * @param password The user's plaintext password.
	 * @return The user's full object from the database if successful, null if not.
	 * @throws InvalidCredentialsException Throws an exception if the provided credentials do not match the info in the database.
	 */
	public User loginUser (String username, String password) throws InvalidCredentialsException {
		User user = userRepo.findByUsername (username);
		
		//if no user was found with username
		if (user == null) {
			throw new InvalidCredentialsException ();
		}
		
		if (passwordEncoder.matches (password, user.getPassword ())) {
			return user;
		}
		else {
			throw new InvalidCredentialsException ();
		}
	}
	
	/**
	 * Returns a user given a username.
	 *
	 * @param username The username to be searched.
	 * @return The User object from the database if it exists, null if not found.
	 */
	public User getUserByUserName (String username) {
		return userRepo.findByUsername (username);
	}
	
	public User createUser (User userInput) throws InvalidValueException {
		User checkUser = userRepo.findByUsername (userInput.getUsername ());
		
		if (checkUser != null) {
			throw new InvalidValueException ("Username already in use");
		}
		
		checkUser = userRepo.findByEmail (userInput.getEmail ());
		
		if (checkUser != null) {
			throw new InvalidValueException ("Email already in use");
		}
		
		userInput.setPassword (passwordEncoder.encode (userInput.getPassword ()));
		
		return userRepo.save (userInput);
	}
	
	public void addToCart (User user, CartItem cartItem) {
		user.getCart ().add (cartItem);
		
		userRepo.save (user);
	}
	
	public void removeFromCart (User user, int index) {
		user.getCart ().remove (index);
		
		//todo delete cartitems that don't have a transaction?
		
		userRepo.save (user);
	}
	
	public void clearCart (User user) {
		user.setCart (new ArrayList <> ());
		
		//todo delete cartitems that don't have a transaction?
		
		userRepo.save (user);
	}
}
