package com.revature.project3backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.jsonmodels.JsonResponse;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.services.ProductService;
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
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(ProductController.class)
public class ProductControllerIT {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    void getProductPositive() throws Exception {
        Product product1 = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);

        JsonResponse expectedResult = new JsonResponse("Got product", true, product1);

        Mockito.when(productService.getProduct (product1.getId())).thenReturn(product1);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product1));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));
    }

    @Test
    void getProductNegative() throws Exception {
        Integer invalidId = 999;
        InvalidValueException expectedException = new InvalidValueException("Invalid product id");
        JsonResponse expectedResult = new JsonResponse(expectedException);

        Mockito.when(this.productService.getProduct(invalidId)).thenThrow(expectedException);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/product/" + invalidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidId));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(expectedResult)));



    }

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
    }
}
