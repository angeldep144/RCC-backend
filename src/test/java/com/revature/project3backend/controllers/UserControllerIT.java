package com.revature.project3backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void createUserPositive() throws Exception {
        User user = new User(1, "first", "last", "email", "username", "password", null, null);
        User input = new User("first", "last", "email", "username", "password");

        JsonResponse expectedResult = new JsonResponse ("Created user", true, null, "/login");

        Mockito.when(this.userService.createUser(input)).thenReturn(user);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

}
