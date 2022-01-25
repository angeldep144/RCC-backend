package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidCredentialsException;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.CartItemRepo;
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
	private final CartItemRepo cartItemRepo;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder ();
	
	@Autowired
	public UserService (UserRepo userRepo, CartItemRepo cartItemRepo) {
		this.userRepo = userRepo;
		this.cartItemRepo = cartItemRepo;
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
	
	/**The createUser function takes in a user object then it does a username and email check where it will throw an
	 * InvalidValueException with the custom message to let the front end know if either the username or email is not unique
	 * then it encodes the password given and replaces it in the userInput object and then saves that object
	 * with the updated password in the database then returns the new object from the database entry
	 *
	 * @param user A user object with the fields all as Strings (firstname, lastname, username, email, password)
	 * @return The user that is registered in the database entry
	 * @throws InvalidValueException with a custom message to differentiate between unique email or username
	 */
	public User createUser (User user) throws InvalidValueException {
		User userWithUsername = userRepo.findByUsername (user.getUsername ());
		
		//if user with username exists
		if (userWithUsername != null) {
			throw new InvalidValueException ("Username already in use");
		}
		
		User userWithEmail = userRepo.findByEmail (user.getEmail ());
		
		//if user with email exists
		if (userWithEmail != null) {
			throw new InvalidValueException ("Email already in use");
		}
		
		//encrypt password
		user.setPassword (passwordEncoder.encode (user.getPassword ()));
		
		return userRepo.save (user);
	}
	
	public void addToCart (User user, CartItem cartItem) {
		user.getCart ().add (cartItem);
		
		userRepo.save (user);
	}
	
	public void removeFromCart (User user, CartItem cartItem) {
		user.getCart ().remove (cartItem);
		
		cartItemRepo.delete (cartItem);
		
		userRepo.save (user);
	}
	
	public void clearCart (User user) {
		List <CartItem> cart = user.getCart ();

		user.setCart (new ArrayList <> ());
		
		cartItemRepo.deleteAll (cart);
		
		userRepo.save (user);
	}
}
