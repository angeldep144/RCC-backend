package com.revature.project3backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project3backend.jsonmodels.CreateSessionBody;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(SessionController.class)
class SessionControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private MockHttpSession session;

    private final ObjectMapper json = new ObjectMapper ();

  /*  @Test
    void createSession() throws Exception{
        CreateSessionBody credentials = new CreateSessionBody();
        credentials.setIdentifier("TestUser");
        credentials.setPassword("pass123");

        List<CartItem> items = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        User user = new User(1,"Test", "User", "testuser@email.com", "TestUser", "pass123", items, transactions);

        JsonResponse expectedResult = new JsonResponse ("Logged in", true, user, "/");

        Mockito.when(this.userService.loginUser(user.getUsername(), user.getPassword())).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credentials))
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(new ObjectMapper().writeValueAsString(expectedResult)));


        Mockito.verify(this.session, Mockito.times(1))
                .setAttribute("user",user);
    }

    @Test
    void deleteSession() throws Exception{

        mvc.perform((MockMvcRequestBuilders.delete("/session").session(session)))
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper()
                        .writeValueAsString(new JsonResponse("Logged out", true, null, "/login"))));

    }*/

    @Test
    void createSession () throws Exception {
        User user = new User ();

        CreateSessionBody createSessionBody = new CreateSessionBody ();

        createSessionBody.setIdentifier ("identifier");
        createSessionBody.setPassword ("password");

        Mockito.when (userService.loginUser (createSessionBody.getIdentifier (), createSessionBody.getPassword ())).thenReturn (user);

        mvc.perform (MockMvcRequestBuilders.post ("/session")
                        .contentType (MediaType.APPLICATION_JSON)
                        .session (new MockHttpSession ())
                        .content (json.writeValueAsString (createSessionBody)))

                .andExpect (MockMvcResultMatchers.status ().isOk ())
                .andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse ("Logged in", true, user, "/"))));
    }

    @Test
    void deleteSession () throws Exception {
        CreateSessionBody createSessionBody = new CreateSessionBody ();

        createSessionBody.setIdentifier ("identifier");
        createSessionBody.setPassword ("password");

        mvc.perform (MockMvcRequestBuilders.delete ("/session")
                        .contentType (MediaType.APPLICATION_JSON)
                        .session (new MockHttpSession ())
                        .content (json.writeValueAsString (createSessionBody)))

                .andExpect (MockMvcResultMatchers.status ().isOk ())
                .andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse ("Logged out", true, null, "/login"))));
    }
}