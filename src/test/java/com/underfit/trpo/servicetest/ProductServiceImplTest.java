package com.underfit.trpo.servicetest;

import com.underfit.trpo.dao.ProductRepository;
import com.underfit.trpo.dto.ProductDto;
import com.underfit.trpo.entities.Product;
import com.underfit.trpo.service.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceImplTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product(1L, "noSql");
    }

    @Test
    public void getByIdTest() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductDto productDto = productService.getById(product.getId());
        Assertions.assertEquals(product.getId(), productDto.toEntity().getId());
    }

    @Test
    public void getAllWhenNullTest() {
        when(productRepository.findAll()).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, () -> productService.getAll());
    }

    @Test
    public void getAllTest() {
        Product product11 = new Product(1L, "Java");
        List<Product> list = List.of(product, product11);
        when(productRepository.findAll()).thenReturn(list);
        List<ProductDto> productDtos = productService.getAll();
        assertThat(productDtos).hasSize(2);
    }

    @Test
    public void saveTest() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductDto productDto = ProductDto.toDto(product);
        ProductDto saved = productService.save(productDto);
        Assertions.assertEquals(product.getId(), saved.getId());
    }

    @Test
    public void deleteTest() {
        doNothing().when(productRepository).deleteById(1L);
        productService.delete(product.getId());
        Assertions.assertFalse(productRepository.findById(product.getId()).isPresent());
    }
}
