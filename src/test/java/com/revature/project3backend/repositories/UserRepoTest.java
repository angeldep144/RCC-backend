package com.revature.project3backend.repositories;

import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
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

    @Test
    void findByUsername() {
        List<CartItem> items = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        User expected = new User(1,"Test", "User", "testuser@email.com", "TestUser", "pass123", items, transactions);

        User actual = userRepo.findByUsername("TestUser");

        assertEquals(expected.toString(), actual.toString());
    }
}