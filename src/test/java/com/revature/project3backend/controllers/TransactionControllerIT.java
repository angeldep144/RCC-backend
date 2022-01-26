package com.revature.project3backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.exceptions.UnauthorizedException;
import com.revature.project3backend.jsonmodels.CreateSessionBody;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.*;
import com.revature.project3backend.services.ProductService;
import com.revature.project3backend.services.TransactionService;
import com.revature.project3backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerIT {
    @Autowired
    private MockMvc mvc;

    @MockBean
    TransactionService transactionService;

    @MockBean
    UserService userService;

    @MockBean
    ProductService productService;

    @MockBean
    private MockHttpSession session;
	
	private final ObjectMapper json = new ObjectMapper ();
	
	@Test
	void createTransaction () {
		
	}
	
	@Test
	void createTransactionWhenNotLoggedIn () {
		
	}
	
	@Test
	void createTransactionWhenCartIsEmpty () {
		
	}
	
	@Test
	void getTransaction () throws Exception {
        User user = new User (1,"first", "last", "email", "username", "password", new ArrayList<> (), new ArrayList<> (), new UserRole(2, "USER"));
        Transaction transaction = new Transaction (1, user, new String(), 20.00f);
        user.getTransactions ().add(transaction);

        Mockito.when (this.session.getAttribute ("user")).thenReturn (user);
        Mockito.when (this.transactionService.getTransaction (transaction.getId ())).thenReturn (transaction);

        mvc.perform (MockMvcRequestBuilders.get ("/transaction/" + user.getId ())
                .contentType (MediaType.APPLICATION_JSON)
                .session (this.session))

                .andExpect (MockMvcResultMatchers.status ().isOk ())
                .andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString(new JsonResponse ("Got transaction", true, transaction))));
	}
	
	@Test
	void getTransactionWhenNotLoggedIn () throws Exception {
        User user = new User (1,"first", "last", "email", "username", "password", new ArrayList<>(), new ArrayList<> (), new UserRole (2, "USER"));
        Transaction transaction = new Transaction (1, user, "", 20.00f);
        user.getTransactions ().add (transaction);

        Mockito.when (this.session.getAttribute("user")).thenReturn (null);

        mvc.perform (MockMvcRequestBuilders.get ("/transaction/" + user.getId())
                        .contentType (MediaType.APPLICATION_JSON)
                        .session (this.session))

                .andExpect (MockMvcResultMatchers.status ().isUnauthorized ())
                .andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse (new UnauthorizedException (), "/login"))));
    }
	
	@Test
	void getTransactionWhenTransactionWasMadeByOtherUser () throws Exception {
        User user1 = new User (1,"first", "last", "email", "username", "password", new ArrayList<>(), new ArrayList<>(), new UserRole (2, "USER"));
        User user2 = new User (2,"second", "last", "email2", "username2", "password", new ArrayList<>(), new ArrayList<>(), new UserRole (2, "USER"));

        Transaction transaction = new Transaction (1, user1, "", 20.00f);
        user1.getTransactions ().add(transaction);

        Mockito.when (this.session.getAttribute("user")).thenReturn (user2);
        Mockito.when (this.transactionService.getTransaction (transaction.getId ())).thenReturn(transaction);

        mvc.perform (MockMvcRequestBuilders.get("/transaction/" + user1.getId ())
                        .contentType (MediaType.APPLICATION_JSON)
                        .session (this.session))

                .andExpect (MockMvcResultMatchers.status ().isBadRequest ())
                .andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse (new InvalidValueException ("Invalid transaction id")))));
    }
}