package com.revature.project3backend.repositories;

import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepoTest {

    @Autowired
    UserRepo userRepo;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByUsernamePositive() {
        List<CartItem> items = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();
        //change the ids based on them in the database
        User expected = new User(3,"Test", "User", "testuser@email.com", "TestUser", "pass123", items, transactions);
        userRepo.save(expected);

        User actual = this.userRepo.findByUsername("TestUser");

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void findByUsernameNegative() {
        List<CartItem> items = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        User expected = new User(1,"Test", "User", "testuser@email.com", "TestUser", "pass123", items, transactions);
        userRepo.save(expected);

        User actual = this.userRepo.findByUsername("wrong username here");

        assertNull(actual);
    }

    @Test
    void findByEmailPositive() {
        List<CartItem> items = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();
        //change the ids based on them in the database
        User expected = new User(1,"Test", "User", "testuser@email.com", "TestUser", "pass123", items, transactions);
        userRepo.save(expected);

        User actual = this.userRepo.findByEmail("testuser@email.com");

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void findByEmailNegative() {
        List<CartItem> items = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        User expected = new User(1,"Test", "User", "testuser@email.com", "TestUser", "pass123", items, transactions);
        userRepo.save(expected);

        User actual = this.userRepo.findByEmail("fake email");

        assertNull(actual);
    }
}