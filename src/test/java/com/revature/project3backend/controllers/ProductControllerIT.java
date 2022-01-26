package com.revature.project3backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.CreateSessionBody;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(ProductController.class)
public class ProductControllerIT {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;
	
	private final ObjectMapper json = new ObjectMapper ();
	
	@Test
	void getProducts () throws Exception {
		List <Product> products = new ArrayList <> ();
		
		products.add (new Product (1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10));
		products.add (new Product (2, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10));
		
		String searchQuery = "roomba";
		int page = 0;
		
		Mockito.when (productService.getProducts (searchQuery, page)).thenReturn (products);
		
		mvc.perform (MockMvcRequestBuilders.get ("/product?searchQuery=" + searchQuery + "&page=" + page))
			.andExpect (MockMvcResultMatchers.status ().isOk ())
			.andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse ("Got " + products.size () + " products", true, products))));
		
		Mockito.verify (productService).getProducts (searchQuery, page);
	}
	
	@Test
	void getProduct () throws Exception {
		Product product = new Product (1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
		
		Mockito.when (productService.getProduct (product.getId ())).thenReturn (product);
		
		mvc.perform (MockMvcRequestBuilders.get ("/product/" + product.getId ()))
			.andExpect (MockMvcResultMatchers.status ().isOk ())
			.andExpect (MockMvcResultMatchers.content ().json (json.writeValueAsString (new JsonResponse ("Got product", true, product))));
		
		Mockito.verify (productService).getProduct (product.getId ());
	}
	
	/*
	todo admin page tests
	
    @Test
    void updateProductSuccessful() throws Exception {
        MultipartFile file = null;
        Product product = new Product(1, "Dog Tricks", "Teach your dog new tricks.", (float) 1.15, null, 13);

        Mockito.when(this.productService.updateProduct(product, file)).thenReturn(product);

        RequestBuilder request = MockMvcRequestBuilders
                .patch("/product")
                .param("id", product.getId().toString())
                .param("name", product.getName())
                .param("description", product.getDescription())
                .param("price", product.getPrice().toString())
                .param("stock", product.getStock().toString())
                .contentType(MediaType.MULTIPART_FORM_DATA);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper()
                        .writeValueAsString(new JsonResponse ("Product updated ok.", true, product))));
    }

    @Test
    void updateProductNegativePrice() throws Exception {
        MultipartFile file = null;
        Product product = new Product(1, "Dog Tricks", "Teach your dog new tricks.", (float) -1.15, null, 13);
        InvalidValueException invalidValueException = new InvalidValueException("Price cannot be negative.");
        Mockito.when(this.productService.updateProduct(product, file)).thenReturn(product);

        RequestBuilder request = MockMvcRequestBuilders
                .patch("/product")
                .param("id", product.getId().toString())
                .param("name", product.getName())
                .param("description", product.getDescription())
                .param("price", product.getPrice().toString())
                .param("stock", product.getStock().toString())
                .contentType(MediaType.MULTIPART_FORM_DATA);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper()
                        .writeValueAsString(new JsonResponse (invalidValueException))));
    }

    @Test
    void updateProductSalePriceTooHigh() throws Exception {
        MultipartFile file = null;
        Product product = new Product(1, "Dog Tricks", "Teach your dog new tricks.", (float) 1.15, null, 13);
        InvalidValueException invalidValueException = new InvalidValueException("Sale price cannot be higher than normal price.");
        Mockito.when(this.productService.updateProduct(product, file)).thenReturn(product);

        RequestBuilder request = MockMvcRequestBuilders
                .patch("/product")
                .param("id", product.getId().toString())
                .param("name", product.getName())
                .param("description", product.getDescription())
                .param("price", product.getPrice().toString())
                .param("stock", product.getStock().toString())
                .param("salePrice", (product.getPrice() + 10) + "")
                .contentType(MediaType.MULTIPART_FORM_DATA);

        mvc.perform(request).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper()
                        .writeValueAsString(new JsonResponse (invalidValueException))));
    }*/
}
