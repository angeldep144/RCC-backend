package com.revature.project3backend.repositories;

import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.CartItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CartItemRepoIT {

    @Autowired
    private ProductRepo productRepo;
    private final List <Product> products = new ArrayList <> ();
    private final int postsPerPage = 20;

    @Autowired
    private UserRepo userRepo;
    private final List<User> users = new ArrayList<>();

    @Autowired
    private CartItemRepo cartItemRepo;
    private final List<CartItem> cartItemsUser2 = new ArrayList<>();

    @BeforeEach
    void setUp() {
        products.add (new Product(null, "Java I", "A beginner Java course", 10.00F, "", 8.00F, 5));
        products.add (new Product (null, "Java II", "An intermediate Java course", 20.00F, "", 18.00F, 5));
        products.add (new Product (null, "Python I", "A beginner Python course", 10.00F, "", 8.00F, 5));
        products.add (new Product (null, "Python II", "An intermediate Python course", 20.00F, "", 18.00F, 5));

        for (int i=0; i< products.size(); i++) {
            products.set(i, productRepo.save (products.get(i)));    // we pass in null id get back the sequential it was set to here
        }

        users.add (new User (null, "User", "1", "email1", "username13123", "password", new ArrayList <> (), new ArrayList <> ()));
        users.add (new User (null, "User", "2", "email2", "username22425", "password", new ArrayList <> (), new ArrayList <> ()));
        users.add (new User (null, "User", "3", "email3", "username32323", "password", new ArrayList <> (), new ArrayList <> ()));

        for (int i=0; i< users.size(); i++) {
            users.set(i, userRepo.save (users.get(i))); // we pass in null id, get back the sequential it was set to here
        }

        // Set up User 2's cart items
        User user2 = userRepo.findByUsername("username22425");
        cartItemsUser2.clear();
        cartItemsUser2.add(new CartItem(user2, products.get(1), 3));
        cartItemsUser2.add(new CartItem(user2, products.get(3), 2));
        for (CartItem cartItem:cartItemsUser2) {
            cartItemRepo.save(cartItem);
        }
        for (int i=0; i<cartItemsUser2.size(); i++) {
            cartItemsUser2.set(i, cartItemRepo.save(cartItemsUser2.get(i)));  // Fetch back the unique id from the saved cart item
        }
        user2.setCart(cartItemsUser2);
        userRepo.save(user2);
    }

    @AfterEach
    void tearDown() {
        cartItemsUser2.clear();
        cartItemRepo.deleteAll();
        users.clear ();
        userRepo.deleteAll ();
        products.clear ();
        productRepo.deleteAll ();
    }

    @Test
    void findAllByBuyerId() {
        User user = userRepo.findByUsername("username22425");   // User 2 (their cart was set up in the @BeforeEach
        List<CartItem> actualResult = cartItemRepo.findAllByBuyerId(user.getId());
        /*for (CartItem ci:actualResult) {
            System.out.println("actualResult "+ci.getProduct().getId()+" "+ci.getProduct().getDescription()+" "+ci.getQuantity());
        }
        for (CartItem ci:user.getCart()) {
            System.out.println("user.cartItem..."+ci.getProduct().getId()+ " "+ci.getProduct().getDescription()+" "+ci.getQuantity());
        }*/
        assertEquals(cartItemsUser2, actualResult);
    }

    @Test
    void findAllByBuyerId_InvalidNoSuchId() {
        List<CartItem> expectedResult = new ArrayList<>();
        List<CartItem> actualResult = cartItemRepo.findAllByBuyerId(99999);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void findAllByBuyerId_WrongBuyerIdOfOtherUserWithCartItems() {
        User user = userRepo.findByUsername("username13123");   // User 1
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(user, products.get(0), 1));
        cartItems.add(new CartItem(user, products.get(2), 2));
        for (CartItem cartItem:cartItems) {
            cartItemRepo.save(cartItem);
        }
        for (int i=0; i<cartItems.size(); i++) {
            cartItems.set(i, cartItemRepo.save(cartItems.get(i)));  // Fetch back the unique id from the saved cart item
        }
        user.setCart(cartItems);
        userRepo.save(user);    // User 1 cart items now primed

        // User 1 "username13123" has completely different cart items than "username22425"
        User user2 = userRepo.findByUsername("username22425");
        List<CartItem> actualResult = cartItemRepo.findAllByBuyerId(user2.getId());
        /*for (CartItem ci:actualResult) {
            System.out.println("actualResult "+ci.getProduct().getId()+" "+ci.getProduct().getDescription()+" "+ci.getQuantity());
        }
        for (CartItem ci:user.getCart()) {
            System.out.println("user.cartItem..."+ci.getProduct().getId()+ " "+ci.getProduct().getDescription()+" "+ci.getQuantity());
        }*/
        assertNotEquals(cartItems, actualResult);
    }

    @Test
    void findAllByBuyerId_BuyerIdOfBuyerWithNoCartItems() {
        User user = userRepo.findByUsername("username32323");   // User 3 (has no cart items)
        List<CartItem> expectedResult = new ArrayList<>();
        List<CartItem> actualResult = cartItemRepo.findAllByBuyerId(user.getId());
        assertEquals(expectedResult, actualResult);
    }
}