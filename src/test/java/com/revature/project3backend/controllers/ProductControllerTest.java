package com.revature.project3backend.controllers;
import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.services.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {
    private ProductController productController;
    private ProductService productService= Mockito.mock(ProductService.class);

    public ProductControllerTest(){
        this.productController = new ProductController(productService);

    }

//    this.ProductController=();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getProducts() {

    }

    @Test
    void getProductPositive() throws InvalidValueException {
        Product expectedResult = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        Mockito.when(productService.getProduct(expectedResult.getId())).thenReturn(expectedResult);

        Product actualResult = productService.getProduct(expectedResult.getId());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getProductNegative() throws InvalidValueException {
        String expectedResult = "Error! Invalid product id";
        String actualResult = null;

        Mockito.when(productService.getProduct(99)).thenThrow(new InvalidValueException("Invalid product id"));

        try {
            productService.getProduct(99);
        } catch (InvalidValueException e){
            actualResult = e.getMessage();
        }

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateProductSuccessful() {
        MultipartFile file = null;
        Product product = new Product(1, "Dog Tricks", "Teach your dog new tricks.", (float) 1.15, null, 13);

        Mockito.when(this.productService.updateProduct(product, file)).thenReturn(product);

        Product actual = this.productService.updateProduct(product, file);

        assertEquals(product, actual);
    }

    @Test
    void updateProductPriceNegative() {
        MultipartFile file = null;
        Product product = new Product(1, "Dog Tricks", "Teach your dog new tricks.", (float) -1.15, null, 13);
        InvalidValueException invalidValueException = new InvalidValueException("Sale price cannot be higher than normal price.");

        Mockito.verify(this.productService, Mockito.times(0));

    }

    @Test
    void updateProductSalePriceTooHigh() {
        MultipartFile file = null;
        Product product = new Product(1, "Dog Tricks", "Teach your dog new tricks.", (float) 1.15, null, 13);
        product.setSalePrice(product.getPrice() + 1);
        InvalidValueException invalidValueException = new InvalidValueException("Sale price cannot be higher than normal price.");

        Mockito.when(this.productService.updateProduct(product, file)).thenReturn(product);

        Mockito.verify(this.productService, Mockito.times(0));

    }
}