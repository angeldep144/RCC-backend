package com.revature.project3backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project3backend.jsonmodels.CreateSessionBody;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.CartItem;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.models.Transaction;
import com.revature.project3backend.models.User;
import com.revature.project3backend.services.ProductService;
import com.revature.project3backend.services.TransactionService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerIT {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private UserService userService;

    @MockBean
    private ProductService productService;

    @MockBean
    private SessionController sessionController;

    private final ObjectMapper json = new ObjectMapper ();

    @Test
    public void createTransactionPositive() throws Exception {
        User user = new User ("first", "last", "email", "username", "password");

        User expectedUser = new User (1, "first", "last", "email", "username", "password", null, null);

        Mockito.when(userService.createUser(user)).thenReturn(expectedUser);

        CreateSessionBody createSessionBody = new CreateSessionBody ();
        createSessionBody.setIdentifier ("username");
        createSessionBody.setPassword ("password");

        Mockito.when (userService.loginUser (createSessionBody.getIdentifier (), createSessionBody.getPassword ())).thenReturn (user);

        Product product = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);

        Product product0 = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        List<CartItem> items = new ArrayList<>();

        items.add(new CartItem(user,product,1));
        items.add(new CartItem(user,product0,1));

        Transaction transaction = new Transaction(user,items);
        Transaction expected = new Transaction(1,user,items,25.76f);

        Mockito.when(transactionService.createTransaction(transaction)).thenReturn(expected);

        Transaction actual = transactionService.createTransaction(transaction);
        System.out.println("service"+actual);


        RequestBuilder request = MockMvcRequestBuilders
                .post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(transaction));
        this.mvc.perform(request)
                .andExpect (MockMvcResultMatchers.status ().isOk ())
                .andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse("Created transaction", true, transactionService.createTransaction (new Transaction (user, items))))));


    }

}