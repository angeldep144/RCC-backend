package com.revature.project3backend.utils;

import com.revature.project3backend.models.Product;
import com.revature.project3backend.utils.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.web.multipart.MultipartFile;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class FileUtilIT {
    private static final Logger log = Logger.getLogger(FileUtil.class);

    private MultipartFile multipartFile;
    private Product product;
    private FileUtil fileUtil;
    @BeforeEach
    void beforeEach() {
        fileUtil = new FileUtil();

        multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return "https://s3-alpha.figma.com/hub/file/948140848/1f4d8ea7-e9d9-48b7-b70c-819482fb10fb-cover.png";
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IllegalStateException {

            }
        };
        product = new Product(1, "roomba", "description", 12.88f, "https://i.pcmag.com/imagery/reviews/01hmxcWyN13h1LfMglNxHGC-1.fit_scale.size_1028x578.v1589573902.jpg", 12.00f, 10);
    }


//    todo test this, was failing on Jenkins
//    @Test
//    void uploadToS3() {
//
//        String expectedValue = "https://jwa-p2.s3.us-east-2.amazonaws.com/" + "RCC/" + "products/" + product.getId().toString() + "-" + multipartFile.getOriginalFilename();
//        String actualValue =  fileUtil.uploadToS3(product, multipartFile);
//        assertEquals(expectedValue, actualValue);
//    }
}

