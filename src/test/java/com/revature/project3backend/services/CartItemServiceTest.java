package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.CartItemRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CartItemServiceTest {

    private final CartItemRepo cartItemRepo = Mockito.mock(CartItemRepo.class);
    private final CartItemService cartItemService;

    List<Product> products = new ArrayList<>();
    List<User> users = new ArrayList<>();

    public CartItemServiceTest() {
        cartItemService = new CartItemService(cartItemRepo);
    }

    @BeforeEach
    void setUp() {
        products.add (new Product(1, "Java I", "A beginner Java course", 10.00F, "", 8.00F, 5));
        products.add (new Product (2, "Java II", "An intermediate Java course", 20.00F, "", 18.00F, 5));
        products.add (new Product (3, "Python I", "A beginner Python course", 10.00F, "", 8.00F, 5));
        products.add (new Product (4, "Python II", "An intermediate Python course", 20.00F, "", 18.00F, 5));
        users.add (new User (1, "User", "1", "email1", "username13123", "password", new ArrayList <> (), new ArrayList <> ()));
        users.add (new User (2, "User", "2", "email2", "username22425", "password", new ArrayList <> (), new ArrayList <> ()));
        users.add (new User (3, "User", "3", "email3", "username32323", "password", new ArrayList <> (), new ArrayList <> ()));
    }

    @AfterEach
    void tearDown() {
        products.clear();
        users.clear();
    }

    @Test
    void createCartItem() {
        CartItem cartItem = new CartItem(users.get(1), products.get(2), 4);
        cartItemService.createCartItem(cartItem);
        Mockito.verify(this.cartItemRepo, Mockito.times(1)).save(cartItem);
    }

    @Test
    void getCartItems() {
//        Integer UserId = 1;
//        List<CartItem> expectedResult = new ArrayList<>();
//        expectedResult.add(new CartItem(users.get(0), products.get(1), 1));
//        expectedResult.add(new CartItem(users.get(0), products.get(3), 2));
//        Mockito.when(cartItemRepo.findAllByBuyerId(UserId)).thenReturn(expectedResult);
//        List<CartItem> actualResult = cartItemService.getCartItems(UserId);
//        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateCartItem() throws InvalidValueException {
        Integer cartItemId = 1;
        CartItem cartItem = new CartItem(1, users.get(0), products.get(1), 2);
        Mockito.when(this.cartItemRepo.findById(cartItemId)).thenReturn(Optional.of(cartItem));
        Integer quantity = 3;
        cartItemService.updateCartItem(cartItemId, quantity);
        assertEquals(quantity, cartItem.getQuantity());
        Mockito.verify(cartItemRepo, Mockito.times(1)).save(cartItem);
    }

    @Test
    void updateCartItem_InvalidCartItem() throws InvalidValueException {
        Integer cartItemId = 5;
        Mockito.when(this.cartItemRepo.findById(cartItemId)).thenReturn(Optional.empty());
        Integer quantity = 2;
        assertThrows(InvalidValueException.class, () -> cartItemService.updateCartItem(cartItemId, quantity));
    }

    @Test
    void deleteCartItem() {
        Integer cartItemId = 2;
        cartItemService.deleteCartItem(cartItemId);
        Mockito.verify(cartItemRepo, Mockito.times(1)).deleteById(cartItemId);
    }
}