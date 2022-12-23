package com.underfit.trpo.integrationtest;

import com.underfit.trpo.controller.ProductController;
import com.underfit.trpo.dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private ProductController productController;

    @WithMockUser(roles = "ADMIN")
    @Transactional
    @Test
    public void testOrdersTest() {
        List<ProductDto> productDtoList = productController.getProducts();
        Assertions.assertEquals(0, productDtoList.size());
        ProductDto productDto = new ProductDto();
        productDto.setProductname("Java");
        productController.create(productDto);
        productDto = new ProductDto();
        productDto.setProductname("Sql");
        productController.create(productDto);
        productDtoList = productController.getProducts();
        Assertions.assertEquals(2, productDtoList.size());
        ProductDto product = productController.getProduct(1L);
        Assertions.assertEquals("Java", product.getProductname());
        product.setProductname("Молоко");
        productController.update(1L, product);
        product = productController.getProduct(1L);
        Assertions.assertEquals("Молоко", product.getProductname());
    }
}
