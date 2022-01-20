package com.revature.project3backend.services;

import com.revature.project3backend.exceptions.InvalidValueException;
import com.revature.project3backend.models.Product;
import com.revature.project3backend.repositories.ProductRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

    @Test
    void getAllProductsPositive(){
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        Product product2 = new Product(2, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        Product product3 = new Product(3, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        Product product4 = new Product(4, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);

        Page<Product> expectedResult = new Page<Product>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Product, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Product> getContent() {
                return productList;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Product> iterator() {
                return null;
            }
        };
        Mockito.when(productRepo.findByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining("Jake", "Jake", PageRequest.of (1, 20, Sort.by ("name")))).thenReturn(expectedResult);

        List<Product> actualResult = productService.getProducts("Jake",1);

        assertEquals(expectedResult.getContent(), actualResult);
    }

    @Test
    void getAllProductsNegative(){
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        Product product2 = new Product(2, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        Product product3 = new Product(3, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
        Product product4 = new Product(4, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);

        Page<Product> expectedResult = new Page<Product>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super Product, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Product> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Product> iterator() {
                return null;
            }
        };
        Mockito.when(productRepo.findByNameIgnoreCaseContainingOrDescriptionIgnoreCaseContaining("Jake", "Jake", PageRequest.of (1, 20, Sort.by ("name")))).thenReturn(expectedResult);

        List<Product> actualResult = productService.getProducts("Jake",1);

        assertEquals(expectedResult.getContent(), actualResult);
    }
}