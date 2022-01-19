package com.revature.project3backend.services;

import com.revature.project3backend.models.User;
import com.revature.project3backend.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;



class UserServiceTest {

    UserService userService;
    UserRepo userRepo = Mockito.mock(UserRepo.class);



    @Test
    void loginUser() {
    }

    @Test
    void getUserByValidUserName() {
        String username = "rmace";
        User user = new User();
        Mockito.when(userService.getUserByUserName(username)).thenReturn(user);

    }

    @Test
    void getUserByInvalidUserName() {
    }
}