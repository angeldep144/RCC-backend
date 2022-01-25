package com.revature.project3backend.controllers;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TransactionControllerTest {

    private TransactionController transactionController;
    private final TransactionService transactionService = Mockito.mock(TransactionService.class);

    @BeforeEach
    void setUp () {
    }

    @AfterEach
    void tearDown () {
    }


    @Test
    public void createTransactionPositive()
    {
        User user = new User ("first", "last", "email", "username", "password");
        Product product = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);

        Product product0 = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        List<CartItem> items = new ArrayList<>();

        items.add(new CartItem(user,product,1));
        items.add(new CartItem(user,product0,1));

        Transaction transaction = new Transaction(user,items);
        Transaction expected = new Transaction(1,user,items,25.76f);

        Mockito.when(transactionService.createTransaction(transaction)).thenReturn(expected);
        Transaction actual = transactionService.createTransaction(transaction);

        assertEquals(expected.toString(),actual.toString());

    }

    @Test
    public void createTransactionNegative()
    {
        User user = new User ("first", "last", "email", "username", "password");

        Transaction transaction = new Transaction(user,null);

        Mockito.when(transactionService.createTransaction(transaction)).thenReturn(null);
        Transaction actual = transactionService.createTransaction(transaction);

        assertNull(actual);

    }

    @Test
    public void getTransactionPositive() throws InvalidValueException {
        User user = new User ("first", "last", "email", "username", "password");
        Product product = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);

        Product product0 = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        List<CartItem> items = new ArrayList<>();

        items.add(new CartItem(user,product,1));
        items.add(new CartItem(user,product0,1));

        Transaction expected = new Transaction(1,user,items,25.76f);

        Mockito.when(transactionService.getTransaction(1)).thenReturn(expected);
        Transaction actual = transactionService.getTransaction(1);

        assertEquals(expected.toString(),actual.toString());

    }
    @Test
    public void getTransactionNegative() throws InvalidValueException {
        User user = new User ("first", "last", "email", "username", "password");
        Product product = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);

        Product product0 = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        List<CartItem> items = new ArrayList<>();

        items.add(new CartItem(user,product,1));
        items.add(new CartItem(user,product0,1));

        Transaction expected = new Transaction(1,user,items,25.76f);

        Mockito.when(transactionService.getTransaction(1)).thenReturn(null);
        Transaction actual = transactionService.getTransaction(1);

        assertNull(actual);

    }
}