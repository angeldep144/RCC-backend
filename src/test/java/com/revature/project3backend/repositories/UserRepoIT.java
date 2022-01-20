package com.revature.project3backend.repositories;

import com.revature.project3backend.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepoIT {

    @Autowired
    private UserRepo userRepo;

    private final List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() { //delete this and change the ids in the actual tests when we swap away from H2
        users.add(new User(7, "first", "last", "email", "username", "password", null, null));
        users.add(new User(2, "first", "last", "email1", "username1", "password", null, null));
        users.add(new User(3, "first", "last", "email2", "username2", "password", null, null));

        for (int i = 0; i < users.size(); i++) {
            userRepo.save(users.get(i));
        }
    }

    @Test
    void findByUsernamePositive() {
        User expectedResult = users.get(0);

        User actualResult = userRepo.findByUsername("username");

        assertEquals (expectedResult, actualResult);
    }

    @Test
    void findByUsernameNegative() {

        User actualResult = userRepo.findByUsername("wrong username");

        assertNull (actualResult);
    }

    @Test
    void findByEmailPositive() {
        User expectedResult = new User(1, "first", "last", "email", "username", "password", null, null);

        User actualResult = userRepo.findByEmail("email");

        assertEquals (expectedResult, actualResult);
    }

    @Test
    void findByEmailNegative() {
        User actualResult = userRepo.findByEmail("wrong email");

        assertNull(actualResult);
    }

}
