package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.repositories.ProductRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    ProductRepo productRepo = Mockito.mock(ProductRepo.class);
    ProductService productService;

    public ProductServiceTest() {
        this.productService = new ProductService(this.productRepo);
    }

    @BeforeEach
    void setUp() {
        System.out.println("before test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("after test");
    }

    @Test
    void getProducts() {

    }

    @Test
    void getProductPositive() throws InvalidValueException {
        Product expectedResult = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);

        Mockito.when(productRepo.findById(1)).thenReturn(java.util.Optional.of(expectedResult));

        Product actualResult = productService.getProduct(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getProductNegative() {
        String expectedResult = "Error! Invalid product id";
        String actualResult = null;

        Mockito.when(productRepo.findById(99)).thenReturn(Optional.ofNullable(null));

        try {
            productService.getProduct(99);
        }
        catch (InvalidValueException e){
            actualResult = e.getMessage();
        }

        assertEquals(expectedResult, actualResult);
    }
}