package com.revature.project3backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.User;
import com.revature.project3backend.models.UserRole;
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
    private MockMvc mvc;

    @MockBean
    private UserService userService;
	
	private final ObjectMapper json = new ObjectMapper ();
	
	@Test
	void createUserWhenFirstNameIsNull () {
		
	}
	
	@Test
	void createUserWhenLastNameIsNull () {
		
	}
	
	@Test
	void createUserWhenEmailIsNull () {
		
	}
	
	@Test
	void createUserWhenUsernameIsNull () {
		
	}
	
	@Test
	void createUserWhenPasswordIsNull () {
		
	}
	
	@Test
	void createUserWhenFirstNameIsEmpty () {
		
	}
	
	@Test
	void createUserWhenLastNameIsEmpty () {
		
	}
	
	@Test
	void createUserWhenEmailIsEmpty () {
		
	}
	
	@Test
	void createUserWhenUsernameIsEmpty () {
		
	}
	
	@Test
	void createUserWhenPasswordIsEmpty () {
		
	}
	
	@Test
	void createUserWhenFirstNameIsWhitespace () {
		
	}
	
	@Test
	void createUserWhenLastNameIsWhitespace () {
		
	}
	
	@Test
	void createUserWhenEmailIsWhitespace () {
		
	}
	
	@Test
	void createUserWhenUsernameIsWhitespace () {
		
	}
	
	@Test
	void createUserWhenPasswordIsWhitespace () {
		
	}
	
	@Test
	void createUserWhenUsernameIsInvalid () {
		
	}
	
	@Test
	void createUserWhenEmailIsInvalid () {
		
	}
	
	@Test
	void createUser () {
		
	}

//    @Test
//    void createUserPositive() throws Exception {
//        UserRole role = new UserRole(1, "Admin");
//
//        User user = new User(1, "first", "last", "email"
//                , "username", "password", null, null, role);
//        User input = new User("first", "last", "email", "username", "password");
//
//        JsonResponse expectedResult = new JsonResponse ("Created user", true, user, "/login");
//
//        //todo: used wild card because there was an issue with the mocked service returning null
//        Mockito.when(this.userService.createUser(Mockito.any(User.class))).thenReturn(user);
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/user")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(input));
//
//        this.mvc.perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
//    }
}
