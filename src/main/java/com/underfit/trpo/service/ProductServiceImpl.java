package com.underfit.trpo.service;


import com.underfit.trpo.dao.ProductRepository;
import com.underfit.trpo.dto.ProductDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ShopService<ProductDto> {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAll() {
        return productRepository
                .findAll()
                .stream()
                .map(ProductDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getById(Long id) {
        return ProductDto.toDto(productRepository.findById(id).orElse(null));
    }

    @Override
    public ProductDto save(ProductDto dto) {
        return ProductDto.toDto(productRepository.save(dto.toEntity()));
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
