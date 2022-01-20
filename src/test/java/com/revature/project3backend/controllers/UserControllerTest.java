package com.revature.project3backend.controllers;

import com.revature.project3backend.models.User;
import com.revature.project3backend.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    private UserController userController;
    private UserService userService = Mockito.mock(UserService.class);

    public UserControllerTest()  {
        this.userController = new UserController(userService);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createUser() {
        User expectedResult = new User(1, "first", "last", "email", "username", "password", null, null);


        Mockito.when(userService.createUser(expectedResult));

        //User

    }

    @Test
    void getUsers() {

        List<User> expectedResult = new ArrayList<>();
        expectedResult.add( new User(1, "first", "last", "email", "username", "password", null, null));
        expectedResult.add( new User(2, "first2", "last2", "email2", "username2", "password2", null, null));

        //Mockito.when();

    }
}