package com.underfit.trpo.dto;

import com.underfit.trpo.entities.Product;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * A DTO for the {@link Product} entity
 */
@Data
public class ProductDto implements Serializable {
    private Long id;
    private String productname;

    public Product toEntity() {
        Product product = new Product();
        BeanUtils.copyProperties(this, product);
        return product;
    }

    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }
}