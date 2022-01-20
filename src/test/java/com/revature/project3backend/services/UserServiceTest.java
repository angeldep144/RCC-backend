package com.revature.project3backend.services;

import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.UserRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private UserRepo userRepo = Mockito.mock(UserRepo.class);

    public UserServiceTest() {this.userService = new UserService(userRepo);}

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void loginUser() {
    }

    @Test
    void createUserPositive() {
        User user = new User(1, "first", "last", "email", "username", "password", null, null);

        Mockito.when(this.userRepo.save(user)).thenReturn(user);

        User actualResult = this.userService.createUser(user);
        User expectedResult = this.userRepo.save(user);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createUserNegative() {
        User user = new User(1, "first", "last", "email", "username", "password", null, null);

        this.userRepo.save(user);

        Mockito.when(this.userRepo.save(user)).thenReturn(null);

        User actualResult = this.userService.createUser(user);

        assertNull(actualResult);
    }
}